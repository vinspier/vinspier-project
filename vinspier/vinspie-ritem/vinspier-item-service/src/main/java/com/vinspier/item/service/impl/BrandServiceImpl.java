package com.vinspier.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vinspier.common.pojo.PageResult;
import com.vinspier.item.mapper.BrandMapper;
import com.vinspier.item.mapper.CategoryMapper;
import com.vinspier.item.pojo.Brand;
import com.vinspier.item.service.BrandService;
import com.vinspier.item.service.CategoryService;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageResult<Brand> queryByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        // 初始化example对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        // 根据name模糊查询，或者根据首字母查询
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("name", "%" + key + "%").orEqualTo("letter", key);
        }
        // 添加分页条件
        PageHelper.startPage(page, rows);
        // 添加排序条件
        if (StringUtils.isNotBlank(sortBy)) {
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }
        List<Brand> brands = this.brandMapper.selectByExample(example);
        // 包装成pageInfo
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        // 包装成分页结果集返回
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        // 先新增brand
        brandMapper.insertSelective(brand);
        // 在新增中间表关联数据
        cids.forEach(cid -> brandMapper.insertBrandAndCategory(cid, brand.getId()));
    }

    @Override
    @Transactional
    public void updateBrand(Brand brand, List<Long> cids) {
        // 更新品牌信息
        brandMapper.updateByPrimaryKey(brand);
        // 删除原先绑定的分类信息
        brandMapper.deleteBrandsAndCategoryByBid(brand.getId());
        //重新保存分类信息
        cids.forEach(cid -> brandMapper.insertBrandAndCategory(cid,brand.getId()));
    }

    @Override
    @Transactional
    public void removeById(Long bid) {
        // 删除品牌关联的分类信息
        brandMapper.deleteBrandsAndCategoryByBid(bid);
        // 删除品牌
        brandMapper.deleteByPrimaryKey(bid);
    }

    @Override
    @Transactional
    public void removeBrands(List<Long> bids) {
        brandMapper.removeBrandsAndCategoryByBids(bids);
        brandMapper.removeBrandsByBids(bids);
    }
}
