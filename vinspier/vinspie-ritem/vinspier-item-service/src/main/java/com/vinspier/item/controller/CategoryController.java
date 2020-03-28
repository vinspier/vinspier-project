package com.vinspier.item.controller;

import com.vinspier.common.template.ResponseTemplate;
import com.vinspier.item.pojo.Category;
import com.vinspier.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping(value = "/list")
    @ResponseBody
    public ResponseTemplate list(@RequestParam("pid") Long pid){
        if (pid == null){
            return ResponseTemplate.error("param pid must not be null");
        }
        List<Category> categories = this.categoryService.queryByPid(pid);
        return ResponseTemplate.ok(categories);
    }

}
