package com.vinspier.item.mapper;

import com.vinspier.item.pojo.SpecParam;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;


public interface SpecParamMapper extends Mapper<SpecParam> {
    /**
     * 删除sku 分组属性值信息
     * @param gid 分组id
     * @return
     */
    @Delete("DELETE FROM tb_spec_param WHERE group_id = #{gid}")
    void deleteParams(@Param("gid") Long gid);
}
