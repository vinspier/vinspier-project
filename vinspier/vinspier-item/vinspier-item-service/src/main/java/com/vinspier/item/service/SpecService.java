package com.vinspier.item.service;


import com.vinspier.item.pojo.SpecGroup;
import com.vinspier.item.pojo.SpecParam;

import java.util.List;

public interface SpecService {
    List<SpecGroup> queryGroupsByCid(Long cid);
    List<SpecGroup> querySpecsByCid(Long cid);
    void saveGroup(SpecGroup group);
    SpecGroup updateGroup(SpecGroup group);
    void deleteGroup(Long gid);

    List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching);
    void saveParam(SpecParam param);
    SpecParam updateParam(SpecParam param);
    void deleteParam(Long pid);
}
