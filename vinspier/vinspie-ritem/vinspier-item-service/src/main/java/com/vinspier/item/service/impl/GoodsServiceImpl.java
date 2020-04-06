package com.vinspier.item.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vinspier.common.pojo.PageResult;
import com.vinspier.item.bo.SpuBo;
import com.vinspier.item.mapper.*;
import com.vinspier.item.pojo.Sku;
import com.vinspier.item.pojo.Spu;
import com.vinspier.item.pojo.SpuDetail;
import com.vinspier.item.pojo.Stock;
import com.vinspier.item.service.CategoryService;
import com.vinspier.item.service.GoodsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private StockMapper stockMapper;

    @Override
    public PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer rows) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        // 搜索条件，模糊查询
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }

        // 添加上下架的过滤条件
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }

        // 添加分页条件
        PageHelper.startPage(page, rows);

        // 执行查询
        List<Spu> spus = this.spuMapper.selectByExample(example);
        PageInfo<Spu> pageInfo = new PageInfo<>(spus);

        List<SpuBo> spuBos = new ArrayList<>();
        spus.forEach(spu->{
            SpuBo spuBo = new SpuBo();
            // copy共同属性的值到新的对象
            BeanUtils.copyProperties(spu, spuBo);

            // 查询分类名称Cname：例如：手机/手机通讯/手机
            List<String> names = this.categoryService.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            spuBo.setCname(StringUtils.join(names, "/"));

            // 查询品牌的名称Bname
            spuBo.setBname(this.brandMapper.selectByPrimaryKey(spu.getBrandId()).getName());

            spuBos.add(spuBo);
        });
        return new PageResult<>(pageInfo.getTotal(), spuBos);
    }

    /**
     * 保存 新增商品信息
     * @param spuBo
     */
    @Transactional
    @Override
    public void saveGoods(SpuBo spuBo) {
        /** 1、新增商品基本信息 */
        spuBo.setId(null);
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        this.spuMapper.insertSelective(spuBo);

        /** 2、新增商品具体信息 */
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());
        this.spuDetailMapper.insertSelective(spuDetail);

        /** 3、增加商品sku组 以及 库存信息 */
        saveSkuAndStock(spuBo);
    }

    /**
     * 更新商品信息
     * @param spuBo
     */
    @Transactional
    @Override
    public void updateGoods(SpuBo spuBo) {
        /** 1、 删除该商品的旧stock数据 */
        this.stockMapper.deleteBySpuId(spuBo.getId());
        /** 2、 删除该商品的旧sku数据集合 */
        this.skuMapper.deleteBySpuId(spuBo.getId());

        /** 5、增加商品sku组 以及 库存信息 */
        saveSkuAndStock(spuBo);

        spuBo.setLastUpdateTime(new Date());
        spuBo.setValid(null);
        spuBo.setSaleable(null);
        /** 3、 更新商品的spu信息 */
        this.spuMapper.updateByPrimaryKeySelective(spuBo);
        /** 4、更新商品spuDetail信息 */
        this.spuDetailMapper.updateByPrimaryKeySelective(spuBo.getSpuDetail());

    }

    @Override
    public int updateSaleable(SpuBo spuBo) {
        Assert.notNull(spuBo.getId(),"必须提供必要的主键ID");
        return spuMapper.updateByPrimaryKeySelective(spuBo);
    }

    @Override
    public SpuDetail querySpuDetailBySpuId(Long spuId) {
        return this.spuDetailMapper.selectByPrimaryKey(spuId);
    }

    @Override
    public List<Sku> querySkusBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skus = this.skuMapper.select(sku);
        skus.forEach(s -> {
            Stock stock = this.stockMapper.selectByPrimaryKey(s.getId());
            s.setStock(stock.getStock());
        });
        return skus;
    }

    private void saveSkuAndStock(SpuBo spuBo){
        /** 4、新增sku */
        spuBo.getSkus().forEach(sku -> {
            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            this.skuMapper.insertSelective(sku);
            /** 5、新增sku对应的库存stock */
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            this.stockMapper.insertSelective(stock);
        });
    }

    @Override
    @Transactional
    public void deleteBySpuId(Long spuId) {
        /** 删除stock库存信息 */
        this.stockMapper.deleteBySpuId(spuId);
        /** 删除sku组合信息集合 */
        this.skuMapper.deleteBySpuId(spuId);
        /** 删除特殊信息 */
        this.spuDetailMapper.deleteBySpuId(spuId);
        /** 删除基本信息 */
        this.spuMapper.deleteByPrimaryKey(spuId);
    }
}
