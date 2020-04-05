package com.vinspier.item.bo;


import com.vinspier.item.pojo.Sku;
import com.vinspier.item.pojo.Spu;
import com.vinspier.item.pojo.SpuDetail;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class SpuBo extends Spu {

    private String cname;// 商品分类名称

    private String bname;// 品牌名称

    private SpuDetail spuDetail;// 商品详情信息

    private List<Sku> skus;// sku集合
}
