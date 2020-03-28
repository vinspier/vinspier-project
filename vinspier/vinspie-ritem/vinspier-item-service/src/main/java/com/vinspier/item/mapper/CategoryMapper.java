package com.vinspier.item.mapper;

import com.vinspier.item.pojo.Category;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CategoryMapper extends Mapper<Category> {


    List<Category> queryByPid(Long pid);

}
