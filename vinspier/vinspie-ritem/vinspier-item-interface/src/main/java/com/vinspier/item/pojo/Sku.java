package com.vinspier.item.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 商品sku详情类
 */
@Table(name = "tb_sku")
@Data
public class Sku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long spuId;

    private String title;

    private String images;

    private Long price;

    private String ownSpec;// 商品特殊规格的键值对

    private String indexes;// 商品特殊规格的下标

    private Boolean enable;// 是否有效，逻辑删除用

    private Date createTime;// 创建时间

    private Date lastUpdateTime;// 最后修改时间
    // @Transient表示该属性并非一个到数据库表的字段的映射
    @Transient

    private Integer stock;// 忽略库存
}
