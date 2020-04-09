package com.vinspier.item.api;

import com.vinspier.common.template.ResponseTemplate;
import org.springframework.web.bind.annotation.*;


/**
 * 商品操作统一接口
 * */
@RequestMapping("/goods")
public interface GoodsApi {

    /**
     * 查询商品列表Spu，返回分页结果集
     */
    @GetMapping("spu/page")
    ResponseTemplate querySpuBoByPage(
            @RequestParam(value = "key", required = false)String key,
            @RequestParam(value = "saleable", required = false)Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "rows", defaultValue = "5")Integer rows);

    /**
     * 查询商品的详细信息
     * @param spuId
     * @return
     */
    @GetMapping("spu/detail/{spuId}")
    ResponseTemplate querySpuDetailBySpuId(@PathVariable("spuId")Long spuId);


    /**
     * 查询 商品的sku组合信息列表
     * @param spuId
     * @return
     */
    @GetMapping("sku/list")
    ResponseTemplate querySkusBySpuId(@RequestParam("id")Long spuId);
}
