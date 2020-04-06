package com.vinspier.item.mapper;

import com.vinspier.item.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CategoryMapper extends Mapper<Category> ,SelectByIdListMapper<Category, Long> {

    @Select("SELECT * FROM tb_category WHERE parent_id = #{pid}")
    List<Category> queryByPid(Long pid);

    @Select("SELECT * FROM tb_category WHERE id IN (SELECT category_id FROM tb_category_brand WHERE brand_id = #{bid})")
    List<Category> queryByBid(Long bid);

}
