package com.vinspier.goods.service.impl;

import com.vinspier.goods.client.BrandClient;
import com.vinspier.goods.client.CategoryClient;
import com.vinspier.goods.client.GoodsClient;
import com.vinspier.goods.client.SpecClient;
import com.vinspier.goods.service.GoodsService;
import com.vinspier.item.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecClient specClient;


    @Override
    public Map<String, Object> loadData(Long spuId) {
        Map<String, Object> map = new HashMap<>();
        // 根据id查询spu对象
        Spu spu = this.goodsClient.querySpuBySpuId(spuId).getData();

        // 查询spudetail
        SpuDetail spuDetail = this.goodsClient.querySpuDetailBySpuId(spuId).getData();

        // 查询sku集合
        List<Sku> skus = this.goodsClient.querySkusBySpuId(spuId).getData();

        // 查询分类
        List<Long> cids = Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3());
        List<String> names = this.categoryClient.queryNamesByIds(cids).getData();
        List<Map<String, Object>> categories = new ArrayList<>();
        for (int i = 0; i < cids.size(); i++) {
            Map<String, Object> categoryMap = new HashMap<>();
            categoryMap.put("id", cids.get(i));
            categoryMap.put("name", names.get(i));
            categories.add(categoryMap);
        }
        // 查询品牌
        Brand brand = this.brandClient.findById(spu.getBrandId()).getData();

        // 查询规格参数组
        List<SpecGroup> groups = this.specClient.querySpecsByCid(spu.getCid3()).getData();

        // 查询特殊的规格参数
        List<SpecParam> params = this.specClient.queryParams(null, spu.getCid3(), false, null).getData();
        Map<Long, String> paramMap = new HashMap<>();
        params.forEach(param -> {
            paramMap.put(param.getId(), param.getName());
        });

        // 封装spu
        map.put("spu", spu);
        // 封装spuDetail
        map.put("spuDetail", spuDetail);
        // 封装sku集合
        map.put("skus", skus);
        // 分类
        map.put("categories", categories);
        // 品牌
        map.put("brand", brand);
        // 规格参数组
        map.put("groups", groups);
        // 查询特殊规格参数
        map.put("paramMap", paramMap);

        return map;
    }
}
