package com.vinspier.item.service;


import com.vinspier.common.pojo.PageResult;
import com.vinspier.item.bo.SpuBo;
import com.vinspier.item.pojo.Sku;
import com.vinspier.item.pojo.SpuDetail;

import java.util.List;

public interface GoodsService {
    /**
     * 根据条件的不同 分页查询商品
     * */
    PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer rows);

    /**
     * 保存 商品信息
     * */
    void saveGoods(SpuBo spuBo);

    /**
     * 更新商品信息
     * @param spuBo
     */
    void updateGoods(SpuBo spuBo);

    /**
     * 查询商品详细信息
     * @param spuId
     * @return
     */
    SpuDetail querySpuDetailBySpuId(Long spuId);


    /**
     * 查询 商品的sku集合
     * @param spuId
     * @return
     */
    List<Sku> querySkusBySpuId(Long spuId);

    /**
     * 删除商品相关信息
     * @param spuId
     */
    void deleteBySpuId(Long spuId);
}
