package com.vinspier.item.controller;

import com.vinspier.common.pojo.PageResult;
import com.vinspier.common.template.ResponseTemplate;
import com.vinspier.item.bo.SpuBo;
import com.vinspier.item.pojo.Sku;
import com.vinspier.item.pojo.Spu;
import com.vinspier.item.pojo.SpuDetail;
import com.vinspier.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品信息
 * */
@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 查询商品列表Spu，返回分页结果集
     */
    @GetMapping("/spu/page")
    @ResponseBody
    public ResponseTemplate<PageResult<SpuBo>> querySpuBoByPage(
            @RequestParam(value = "key", required = false)String key,
            @RequestParam(value = "saleable", required = false)Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "rows", defaultValue = "5")Integer rows){
        PageResult<SpuBo> pageResult = this.goodsService.querySpuBoByPage(key, saleable, page, rows);
       // return ResponseEntity.ok(pageResult);
        return ResponseTemplate.ok(pageResult);
    }

    /**
     * 查询商品的基础信息
     * @param spuId
     * @return
     */
    @GetMapping("/spu/{spuId}")
    @ResponseBody
    public ResponseTemplate<Spu> querySpuBySpuId(@PathVariable("spuId")Long spuId){
        Spu spu = this.goodsService.querySpuBySpuId(spuId);
        return ResponseTemplate.ok(spu);
    }

    /**
     * 查询商品的详细信息
     * @param spuId
     * @return
     */
    @GetMapping("/spu/detail/{spuId}")
    @ResponseBody
    public ResponseTemplate<SpuDetail> querySpuDetailBySpuId(@PathVariable("spuId")Long spuId){
        SpuDetail spuDetail = this.goodsService.querySpuDetailBySpuId(spuId);
        return ResponseTemplate.ok(spuDetail);
    }


    /**
     * 查询 商品的sku组合信息列表
     * @param spuId
     * @return
     */
    @GetMapping("/sku/list")
    @ResponseBody
    public ResponseTemplate<List<Sku>> querySkusBySpuId(@RequestParam("id")Long spuId){
        List<Sku> skus = this.goodsService.querySkusBySpuId(spuId);
        return ResponseTemplate.ok(skus);
    }

    /**
     * 保存 新增商品
     * @param spuBo
     * @return
     */
    @PostMapping("/goods")
    @ResponseBody
    public ResponseTemplate saveGoods(@RequestBody SpuBo spuBo){
        this.goodsService.saveGoods(spuBo);
        return ResponseTemplate.ok();
    }

    /**
     * 更新 商品信息
     * @param spuBo
     * @return
     */
    @PutMapping("/goods")
    @ResponseBody
    public ResponseTemplate  updateGoods(@RequestBody SpuBo spuBo){
        this.goodsService.updateGoods(spuBo);
        return ResponseTemplate.ok();
    }

    /**
     * 更新 商品信息 上下架
     * @param spuBo
     * @return
     */
    @PostMapping("/goods/updateSaleable")
    @ResponseBody
    public ResponseTemplate  updateSaleable(@RequestBody SpuBo spuBo){
        this.goodsService.updateSaleable(spuBo);
        return ResponseTemplate.ok();
    }


    /**
     * 删除商品
     * */
    @GetMapping("/goods/{spuId}")
    @ResponseBody
    public ResponseTemplate deleteBySpuId(@PathVariable("spuId")Long spuId){
        this.goodsService.deleteBySpuId(spuId);
       // return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseTemplate.ok();
    }

}
