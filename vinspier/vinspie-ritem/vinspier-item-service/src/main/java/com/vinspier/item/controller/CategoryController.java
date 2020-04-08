package com.vinspier.item.controller;

import com.vinspier.common.template.ResponseTemplate;
import com.vinspier.item.pojo.Category;
import com.vinspier.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/findAll")
    @ResponseBody
    public ResponseEntity<List<Category>> findAll(){
        return ResponseEntity.ok(categoryService.findAll());
    }

    /**
     * 获取指定父级节点下的子节点
     * */
    @GetMapping(value = "/list")
    @ResponseBody
    public ResponseTemplate list(@RequestParam("pid") Long pid){
        if (pid == null){
            return ResponseTemplate.error("param pid must not be null");
        }
        List<Category> categories = this.categoryService.queryByPid(pid);
        return ResponseTemplate.ok(categories);
    }

    /**
     * 查询某一品牌所属的分类
     * */
    @GetMapping(value = "/queryByBid/{bid}")
    @ResponseBody
    public ResponseTemplate queryByBid(@PathVariable("bid") Long bid){
        List<Category> categoryList = this.categoryService.queryByBid(bid);
        return ResponseTemplate.ok(categoryList);
    }

    /**
     * 通过ids查询分类名称集合
     * */
    @GetMapping(value = "/queryNamesByIds")
    @ResponseBody
    public ResponseTemplate queryNamesByIds(@RequestParam("ids")List<Long> ids){
        List<String> categoryNames = categoryService.queryNamesByIds(ids);
        return ResponseTemplate.ok(categoryNames);
    }

}
