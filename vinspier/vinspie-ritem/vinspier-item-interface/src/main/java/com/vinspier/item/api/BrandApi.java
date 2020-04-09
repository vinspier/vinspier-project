package com.vinspier.item.api;

import com.vinspier.common.template.ResponseTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 品牌同意接口
 * */
@RequestMapping("brand")
public interface BrandApi {

    /**
     * 通过id查询品牌
     * */
    @GetMapping("findById/{bid}")
    ResponseTemplate findById(@PathVariable("bid")Long bid);

}
