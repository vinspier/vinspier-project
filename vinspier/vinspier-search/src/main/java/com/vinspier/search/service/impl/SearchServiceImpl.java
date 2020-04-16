package com.vinspier.search.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinspier.common.pojo.PageResult;
import com.vinspier.item.pojo.*;
import com.vinspier.search.client.BrandClient;
import com.vinspier.search.client.CategoryClient;
import com.vinspier.search.client.GoodsClient;
import com.vinspier.search.client.SpecClient;
import com.vinspier.search.pojo.Goods;
import com.vinspier.search.repository.GoodsRepository;
import com.vinspier.search.service.SearchService;
import com.vinspier.search.vo.SearchResultVo;
import com.vinspier.search.vo.SearchVo;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private GoodsRepository goodsRepository;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public Goods buildGoods(Spu spu) throws IOException{
        Goods goods = new Goods();
        // ToDo 查询品牌信息
        Brand brand = brandClient.findById(spu.getBrandId()).getData();
        // ToDo 查询分类信息
        List<String> categoryNames =  categoryClient.queryNamesByIds(Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3())).getData();
        // ToDo 查询sku信息
        List<Long> prices = new ArrayList<>();
        List<Map<String, Object>> skuMapList = new ArrayList<>();
        List<Sku> skuList = goodsClient.querySkusBySpuId(spu.getId()).getData();
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
        List<SpecParam> params = specClient.queryParams(null,spu.getCid3(),null,true).getData();
        // ToDo 查询spuDetail信息
        SpuDetail spuDetail = goodsClient.querySpuDetailBySpuId(spu.getId()).getData();
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

    @Override
    public PageResult<Goods> search(SearchVo searchVo) {
        String key = searchVo.getKey();
        if (StringUtils.isBlank(key)){
            return null;
        }
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 1、对key进行全文检索查询
       // queryBuilder.withQuery(QueryBuilders.matchQuery("all", key).operator(Operator.AND));
        BoolQueryBuilder basicQuery = buildBoolQueryBuilder(searchVo);
        queryBuilder.withQuery(basicQuery);

        // 2、通过sourceFilter设置返回的结果字段,我们只需要id、skus、subTitle
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","skus","subTitle"}, null));

        // 3、添加排序
//        String sortBy = searchVo.getSortBy();
//        Boolean desc = searchVo.getDescending();
//        if (StringUtils.isNotBlank(sortBy)){
//            queryBuilder.withSort(SortBuilders.fieldSort(sortBy).order(desc ? SortOrder.DESC : SortOrder.ASC));
//        }

        // 4、添加聚合查询
        String categoryAggName = "categories";
        String brandAggName = "brands";
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));

        // 5、分页
        // 准备分页参数
        int page = searchVo.getPage();
        int size = searchVo.getSize();
        queryBuilder.withPageable(PageRequest.of(page - 1, size));

        // 6执行搜索，获取搜索的结果集
        AggregatedPage<Goods> goodsPage = (AggregatedPage<Goods>)this.goodsRepository.search(queryBuilder.build());
        // 解析聚合结果集
        List<Map<String, Object>> categories = getCategoryAggResult(goodsPage.getAggregation(categoryAggName));
        List<Brand> brands = getBrandAggResult(goodsPage.getAggregation(brandAggName));

        // 判断是否是一个分类，只有是一个分类是才做参数聚合
        List<Map<String, Object>> specs = null;
        if(!CollectionUtils.isEmpty(categories) && categories.size() == 1){
            // 对规格参数进行聚合
            specs  = getParamAggResult((Long)categories.get(0).get("id"),basicQuery);
        }

//        // 4、查询，获取结果
//        Page<Goods> pageInfo = this.goodsRepository.search(queryBuilder.build());

        return new SearchResultVo(goodsPage.getTotalElements(),goodsPage.getTotalPages(),goodsPage.getContent(),categories,brands,specs);
    }

    /**
     * 新增和更新是相同的方法入口
     * */
    @Override
    public void createIndex(Long id) throws Exception{
        Spu spu = goodsClient.querySpuBySpuId(id).getData();
        // 处理包装成索引信息商品对象
        Goods goods = this.buildGoods(spu);
        // 保存到索引中去
        goodsRepository.save(goods);
    }

    @Override
    public void deleteIndex(Long id) throws Exception{
        goodsRepository.deleteById(id);
    }

    /**
     * 解析分类的聚合结果集
     * @param aggregation
     * @return
     */
    private List<Map<String, Object>> getCategoryAggResult(Aggregation aggregation) {
        LongTerms terms = (LongTerms) aggregation;
        // 获取桶的集合，转化成List<Map<String,  Object>>
        return terms.getBuckets().stream().map(bucket -> {
            // 初始化一个map
            HashMap<String, Object> map = new HashMap<>();
            // 获取桶中的分类id（key）
            long id = bucket.getKeyAsNumber().longValue();
            // 根据分类id查询分类名称
            List<String> names = this.categoryClient.queryNamesByIds(Arrays.asList(id)).getData();
            map.put("id",id);
            map.put("name",names.get(0));
            return map;
        }).collect(Collectors.toList());
    }

    /**
     * 解析品牌的聚合结果集
     * @param aggregation
     * @return
     */
    private List<Brand> getBrandAggResult(Aggregation aggregation) {
        LongTerms terms = (LongTerms) aggregation;
        return terms.getBuckets().stream().map(bucket ->
                this.brandClient.findById(bucket.getKeyAsNumber().longValue()).getData()).collect(Collectors.toList());
    }

    /**
     * 根据查询条件聚合规格参数
     * @param cid
     * @param basicQuery
     * @return
     */
    private List<Map<String, Object>> getParamAggResult(Long cid, QueryBuilder basicQuery) {
        //自定义查询对象构建
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加基本查询条件
        queryBuilder.withQuery(basicQuery);
        //查询要聚合的规格参数
        List<SpecParam> params = this.specClient.queryParams(null, cid, null, true).getData();
        //添加规格参数的聚合
        params.forEach(param -> {
            queryBuilder.addAggregation(AggregationBuilders.terms(param.getName()).field("specs." + param.getName() + ".keyword"));
        });
        //添加结果集过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{},null));
        //执行聚合
        AggregatedPage<Goods> goodsPage = (AggregatedPage<Goods>)this.goodsRepository.search(queryBuilder.build());
        List<Map<String, Object>> specs = new ArrayList<>();
        // 解析聚合结果集 key- 聚合名称（规格参数） value-聚合参数
        Map<String, Aggregation> aggregationMap = goodsPage.getAggregations().asMap();
        for (Map.Entry<String, Aggregation> entry : aggregationMap.entrySet()) {
            //  初始化一个map {k: 规格参数名 options: 聚合的规格参数}
            HashMap<String, Object> map = new HashMap<>();
            map.put("k",entry.getKey());
            // 初始化一个options集合，收集桶中的key
            ArrayList<String> options = new ArrayList<>();
            // 获取聚合
            StringTerms terms = (StringTerms) entry.getValue();
            // 获取桶集合
            terms.getBuckets().forEach(bucket -> {
                options.add(bucket.getKeyAsString());
            });
            map.put("options",options);
            specs.add(map);
        }

        return specs;
    }

    /**
     * 构建布尔查询
     * @param searchVo
     * @return
     */
    private BoolQueryBuilder buildBoolQueryBuilder(SearchVo searchVo) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 给布尔查询添加基本查询条件
        boolQueryBuilder.must(QueryBuilders.matchQuery("all", searchVo.getKey()).operator(Operator.AND));
        // 添加过滤条件
        // 获取用户选择的过滤信息
        Map<String, Object> filter = searchVo.getFilter();
        if (CollectionUtils.isEmpty(filter)){
            return boolQueryBuilder;
        }
        for (Map.Entry<String, Object> entry : filter.entrySet()) {
            String key = entry.getKey();
            if (StringUtils.equals("品牌", key)){
                key = "brandId";
            } else if (StringUtils.equals("分类", key)){
                key = "cid3";
            } else {
                key = "specs." + key + ".keyword";
            }
            boolQueryBuilder.filter(QueryBuilders.termQuery(key, entry.getValue()));
        }
        return boolQueryBuilder;
    }

    /**
     * 处理价格落在哪个具体的区间
     * */
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
