package com.vinspier.item.mapper;

import com.vinspier.item.pojo.Stock;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;


public interface StockMapper extends Mapper<Stock> {

    /**
     * 删除商品的库存信息
     */
    @Delete("DELETE tb_stock FROM tb_stock LEFT JOIN tb_sku on tb_stock.sku_id = tb_sku.id left JOIN tb_spu on tb_sku.spu_id = tb_spu.id WHERE tb_spu.id = #{spuId}")
    void deleteBySpuId(@Param("spuId") Long spuId);
}
