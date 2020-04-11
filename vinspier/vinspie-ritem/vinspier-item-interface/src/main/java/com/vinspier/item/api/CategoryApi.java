package com.vinspier.item.api;

import com.vinspier.common.template.ResponseTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 分类统一接口
 * */
@RequestMapping("/category")
public interface CategoryApi {

    /**
     * 通过ids查询分类名称集合
     * */
    @GetMapping(value = "/queryNamesByIds")
    ResponseTemplate<List<String>> queryNamesByIds(@RequestParam("ids")List<Long> ids);

}
