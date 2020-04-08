package com.vinspier.search.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: Goods
 * @Description: 整合商品需要建立的相关索引信息
 * @Author:
 * @Date: 2020/4/8 16:43
 * @Version V1.0
 **/
@Document(indexName = "goods", type = "docs", shards = 1, replicas = 0)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    @Id
    private Long id; // spuId
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    // 所有需要被搜索的信息，包含标题，分类，甚至品牌
    private String all;
    // 卖点 副标题
    @Field(type = FieldType.Keyword, index = false)
    private String subTitle;
    private Long brandId;// 品牌id
    private Long cid1;// 1级分类id
    private Long cid2;// 2级分类id
    private Long cid3;// 3级分类id
    private Date createTime;// 创建时间
    // 价格数组，是所有sku的价格集合。方便根据价格进行筛选过滤
    private List<Long> price;
    // 用于页面展示的sku信息，不索引，不搜索。包含skuId、image、price、title字段
    // List<sku>信息的json结构
    @Field(type = FieldType.Keyword, index = false)
    private String skus;
    // 可搜索的规格参数，key是参数名，值是参数值
    private Map<String, Object> specs;
}
