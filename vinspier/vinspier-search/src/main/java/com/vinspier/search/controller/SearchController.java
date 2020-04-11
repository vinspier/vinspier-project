package com.vinspier.search.controller;

import com.vinspier.common.pojo.PageResult;
import com.vinspier.common.template.ResponseTemplate;
import com.vinspier.search.pojo.Goods;
import com.vinspier.search.service.SearchService;
import com.vinspier.search.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 搜索商品信息
     * */
    @PostMapping("/page")
    public ResponseTemplate search(@RequestBody SearchVo searchVo){
        PageResult<Goods> pageResult = searchService.search(searchVo);
        return ResponseTemplate.ok(pageResult);
    }

}
