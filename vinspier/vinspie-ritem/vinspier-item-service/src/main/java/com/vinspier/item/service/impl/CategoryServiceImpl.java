package com.vinspier.item.service.impl;

import com.vinspier.item.mapper.CategoryMapper;
import com.vinspier.item.pojo.Category;
import com.vinspier.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findAll() {
        return categoryMapper.selectAll();
    }

    @Override
    @ResponseBody
    public List<Category> queryByPid(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        // return categoryMapper.select(category);
        return categoryMapper.queryByPid(pid);
    }

    /**
     * 通过品牌id查询商品分类
     * @param bid
     * @return
     */
    @Override
    public List<Category> queryByBid(Long bid) {
        return categoryMapper.queryByBid(bid);
    }

    /**
     * 根据cid1,cid2,cid3 获取分类的各个节点名称
     * @param ids
     * @return
     */
    @Override
    public List<String> queryNamesByIds(List<Long> ids) {
        List<Category> list = this.categoryMapper.selectByIdList(ids);
        List<String> names = new ArrayList<>();
        for (Category category : list) {
            names.add(category.getName());
        }
        return names;
    }
}
