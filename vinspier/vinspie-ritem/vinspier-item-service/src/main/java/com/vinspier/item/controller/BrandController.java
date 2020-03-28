package com.vinspier.item.controller;

import com.vinspier.common.pojo.PageResult;
import com.vinspier.common.template.ResponseTemplate;
import com.vinspier.item.pojo.Brand;
import com.vinspier.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 根据查询条件分页并排序查询品牌信息
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @GetMapping("page")
    @ResponseBody
    public ResponseTemplate<PageResult<Brand>> queryBrandsByPage(
            @RequestParam(value = "key", required = false)String key,
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "rows", defaultValue = "5")Integer rows,
            @RequestParam(value = "sortBy", required = false)String sortBy,
            @RequestParam(value = "desc", required = false)Boolean desc
    ){
        PageResult<Brand> result = this.brandService.queryByPage(key, page, rows, sortBy, desc);

        return ResponseTemplate.ok(result);
    }

    /**
     * 新增品牌
     * @param brand
     * @param cids
     */
    @PostMapping("save")
    @ResponseBody
    public ResponseTemplate saveBrand(Brand brand, @RequestParam("cids") List<Long> cids){
        this.brandService.saveBrand(brand, cids);
        return ResponseTemplate.ok();
    }

    /**
     * 更新品牌
     * @param brand
     * @param cids
     */
    @PostMapping("update")
    @ResponseBody
    public ResponseTemplate updateBrand(Brand brand, @RequestParam("cids") List<Long> cids){
        return null;
    }

}
