package com.vinspier.item.service;

import com.vinspier.common.pojo.PageResult;
import com.vinspier.item.pojo.Brand;

import java.util.List;

public interface BrandService {

    /**
     * 根据查询条件分页并排序查询品牌信息
     * @param key 搜索关键词
     * @param page 当前页
     * @param rows 每页大小
     * @param sortBy 排序字段
     * @param desc 是否为降序
     * @return
     */
    PageResult<Brand> queryByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc);


    /**
     * 新增品牌
     * @param brand
     * @param cids 分类信息
     */
    void saveBrand(Brand brand, List<Long> cids);

}
