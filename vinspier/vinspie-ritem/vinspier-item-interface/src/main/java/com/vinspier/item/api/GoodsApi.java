package com.vinspier.item.api;

import com.vinspier.common.pojo.PageResult;
import com.vinspier.common.template.ResponseTemplate;
import com.vinspier.item.bo.SpuBo;
import com.vinspier.item.pojo.Sku;
import com.vinspier.item.pojo.Spu;
import com.vinspier.item.pojo.SpuDetail;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 商品操作统一接口
 * */
public interface GoodsApi {

    /**
     * 查询商品列表Spu，返回分页结果集
     */
    @GetMapping("/spu/page")
    ResponseTemplate<PageResult<SpuBo>> querySpuBoByPage(
            @RequestParam(value = "key", required = false)String key,
            @RequestParam(value = "saleable", required = false)Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "rows", defaultValue = "5")Integer rows);

    /**
     * 查询商品的基础信息
     * @param spuId
     * @return
     */
    @GetMapping("/spu/{spuId}")
    ResponseTemplate<Spu> querySpuBySpuId(@PathVariable("spuId")Long spuId);

    /**
     * 查询商品的详细信息
     * @param spuId
     * @return
     */
    @GetMapping("/spu/detail/{spuId}")
    ResponseTemplate<SpuDetail> querySpuDetailBySpuId(@PathVariable("spuId")Long spuId);


    /**
     * 查询 商品的sku组合信息列表
     * @param spuId
     * @return
     */
    @GetMapping("/sku/list")
    ResponseTemplate<List<Sku>> querySkusBySpuId(@RequestParam("id")Long spuId);

    /**
     * 根据skuId 查询Sku
     * @param skuId
     * @return
     */
    @GetMapping("sku/{skuId}")
    Sku querySkuBySkuId(@PathVariable("skuId")Long skuId);
}
