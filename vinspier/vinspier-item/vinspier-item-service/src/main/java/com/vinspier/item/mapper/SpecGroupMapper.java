package com.vinspier.item.mapper;

import com.vinspier.item.pojo.SpecGroup;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;


public interface SpecGroupMapper extends Mapper<SpecGroup> {
    /**
     * 删除sku 分组信息
     * @param gid 分组id
     * @return
     */
    @Delete("DELETE FROM tb_spec_group WHERE id = #{gid}")
    void deleteGroup(@Param("gid") Long gid);
}
