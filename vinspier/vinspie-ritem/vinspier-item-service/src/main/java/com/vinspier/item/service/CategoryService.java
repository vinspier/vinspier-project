package com.vinspier.item.service;

import com.vinspier.item.pojo.Category;

import java.util.List;

public interface CategoryService {

    /**
     * 查询所有类目
     * @return
     */
    List<Category> findAll();

    /**
     * 根据parentId查询子类目
     * @param pid
     * @return
     */
    List<Category> queryByPid(Long pid);

    /**
     * 获取某一品牌所属的分类
     * @param bid
     * @return
     */
    List<Category> queryByBid(Long bid);
}
