package com.vinspier.search.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinspier.item.pojo.*;
import com.vinspier.search.client.BrandClient;
import com.vinspier.search.client.CategoryClient;
import com.vinspier.search.client.GoodsClient;
import com.vinspier.search.client.SpecClient;
import com.vinspier.search.pojo.Goods;
import com.vinspier.search.service.SearchService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * 查询商品相关信息 将其转换成elasticSearch操作索引的对象信息
 * */
@Service
public class SearchServiceImpl implements SearchService{

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecClient specClient;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public Goods buildGoods(Spu spu) throws IOException{
        Goods goods = new Goods();
        // ToDo 查询品牌信息
        Brand brand = (Brand) brandClient.findById(spu.getBrandId()).getData();
        // ToDo 查询分类信息
        List<String> categoryNames = (List<String>) categoryClient.queryNamesByIds(Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3())).getData();
        // ToDo 查询sku信息
        List<Long> prices = new ArrayList<>();
        List<Map<String, Object>> skuMapList = new ArrayList<>();
        List<Sku> skuList = (List<Sku>) goodsClient.querySkusBySpuId(spu.getId()).getData();
        skuList.forEach(sku -> {
            prices.add(sku.getPrice());
            Map<String, Object> skuMap = new HashMap<>();
            skuMap.put("id", sku.getId());
            skuMap.put("title", sku.getTitle());
            skuMap.put("price", sku.getPrice());
            skuMap.put("image", StringUtils.isNotBlank(sku.getImages()) ? StringUtils.split(sku.getImages(), ",")[0] : "");
            skuMapList.add(skuMap);
        });
        // ToDo 查询specParams信息
        List<SpecParam> params = (List<SpecParam>)specClient.queryParams(null,spu.getCid3(),null,true).getData();
        // ToDo 查询spuDetail信息
        SpuDetail spuDetail = (SpuDetail) goodsClient.querySpuDetailBySpuId(spu.getId()).getData();
        // 获取通用的规格参数
        Map<Long, Object> genericSpecMap = MAPPER.readValue(spuDetail.getGenericSpec(), new TypeReference<Map<Long, Object>>() {});
        // 获取特殊的规格参数
        Map<Long, List<Object>> specialSpecMap = MAPPER.readValue(spuDetail.getSpecialSpec(), new TypeReference<Map<Long, List<Object>>>() {
        });
        // 定义map接收{规格参数名，规格参数值}
        Map<String, Object> paramMap = new HashMap<>();
        params.forEach(param -> {
            // 判断是否通用规格参数
            if (param.getGeneric()) {
                // 获取通用规格参数值
                String value = genericSpecMap.get(param.getId()).toString();
                // 判断是否是数值类型
                if (param.getNumeric()){
                    // 如果是数值的话，判断该数值落在那个区间
                    value = chooseSegment(value, param);
                }
                // 把参数名和值放入结果集中
                paramMap.put(param.getName(), value);
            } else {
                paramMap.put(param.getName(), specialSpecMap.get(param.getId()));
            }
        });
        // 设置参数
        goods.setId(spu.getId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setBrandId(spu.getBrandId());
        goods.setCreateTime(spu.getCreateTime());
        goods.setSubTitle(spu.getSubTitle());
        goods.setAll(spu.getTitle() + brand.getName() + StringUtils.join(categoryNames, " "));
        goods.setPrice(prices);
        goods.setSkus(MAPPER.writeValueAsString(skuMapList));
        goods.setSpecs(paramMap);
        return goods;
    }

    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }

}
