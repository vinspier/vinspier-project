package com.vinspier.item.mapper;

import com.vinspier.item.pojo.SpuDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;


public interface SpuDetailMapper extends Mapper<SpuDetail> {
    /**
     * 删除商品的sku组合信息
     */
    @Delete("DELETE FROM tb_spu_detail where spu_id = #{spuId}")
    void deleteBySpuId(@Param("spuId") Long spuId);
}
