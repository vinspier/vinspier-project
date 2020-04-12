package com.vinspier.goods.controller;

import com.vinspier.goods.service.GoodsHtmlService;
import com.vinspier.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("item")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsHtmlService goodsHtmlService;

    /**
     * 跳转到商品详情页
     * @param model
     * @param id
     * @return
     */
    @GetMapping("{id}.html")
    public String toItemPage(Model model, @PathVariable("id")Long id){
        // 加载所需的数据
        Map<String, Object> loadData = goodsService.loadData(id);
        // 放入模型
        model.addAllAttributes(loadData);
        // 生成静态文件 存放在nginx的html下
        goodsHtmlService.asyncExecute(id);
        return "item";
    }

}
