package com.vinspier.item.service.impl;

import com.vinspier.item.mapper.SpecGroupMapper;
import com.vinspier.item.mapper.SpecParamMapper;
import com.vinspier.item.pojo.SpecGroup;
import com.vinspier.item.pojo.SpecParam;
import com.vinspier.item.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecServiceImpl implements SpecService {

    @Autowired
    private SpecGroupMapper groupMapper;

    @Autowired
    private SpecParamMapper paramMapper;

    /**
     *  根据分类id查询 属性分组的集合
     * */
    @Override
    public List<SpecGroup> queryGroupsByCid(Long cid) {
        SpecGroup group = new SpecGroup();
        group.setCid(cid);
        return groupMapper.select(group);
    }

    /**
     * 根据分类id查询 属性分组的集合
     * 携带每个分组的具体规格参数
     * */
    @Override
    public List<SpecGroup> querySpecsByCid(Long cid) {
        List<SpecGroup> groupList = queryGroupsByCid(cid);
        groupList.forEach(g -> {
            g.setParams(queryParams(g.getId(),null,null,null));
        });
        return groupList;
    }

    /**
     * 保存新增分组
     * */
    @Override
    public void saveGroup(SpecGroup group) {
        groupMapper.insertSelective(group);
    }

    /**
     * 更新分组
     * */
    @Override
    public SpecGroup updateGroup(SpecGroup group) {
        groupMapper.updateByPrimaryKey(group);
        return group;
    }

    /**
     * 删除分组
     * */
    @Override
    public void deleteGroup(Long gid) {
        paramMapper.deleteParams(gid);
        groupMapper.deleteGroup(gid);
    }

    /**
     * 根据条件查询规格参数
     * @param gid
     * @return
     */
    @Override
    public List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching) {
        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setGeneric(generic);
        param.setSearching(searching);
        return this.paramMapper.select(param);
    }

    /**
     * 保存新增属性值
     * */
    @Override
    public void saveParam(SpecParam param) {
        paramMapper.insertSelective(param);
    }

    /**
     * 更新属性值
     * */
    @Override
    public SpecParam updateParam(SpecParam param) {
        paramMapper.updateByPrimaryKey(param);
        return param;
    }

    /**
     * 删除属性值
     * */
    @Override
    public void deleteParam(Long pid) {
        paramMapper.deleteByPrimaryKey(pid);
    }
}
