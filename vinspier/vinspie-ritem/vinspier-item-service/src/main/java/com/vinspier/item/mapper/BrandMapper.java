package com.vinspier.item.mapper;

import com.vinspier.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {

    /**
     * 新增商品分类和品牌中间表数据
     * @param cid 商品分类id
     * @param bid 品牌id
     * @return
     */
    @Insert("INSERT INTO tb_category_brand(category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertBrandAndCategory(@Param("cid") Long cid, @Param("bid") Long bid);

    /**
     * 更新或删除商品品牌时
     * 清楚中间表关联数据
     * */
    @Delete("DELETE FROM tb_category_brand WHERE brand_id = #{bid}")
    void deleteBrandsAndCategoryByBid(Long bid);

    /**
     * 批量删除品牌
     * */
    void removeBrandsByBids(@Param("bids") List<Long> bids);

    /**
     * 批量删除品牌分类中间表信息
     * */
    void removeBrandsAndCategoryByBids(@Param("bids") List<Long> bids);

    /**
     * 查询某一分类下的品牌
     * @param cid
     * @return
     */
    @Select("SELECT b.* FROM tb_brand b INNER JOIN tb_category_brand cb on b.id = cb.brand_id WHERE cb.category_id = #{cid}")
    List<Brand> queryBrandsByCid(@Param("cid")Long cid);
}
