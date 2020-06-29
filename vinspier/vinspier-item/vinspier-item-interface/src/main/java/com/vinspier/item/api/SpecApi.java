package com.vinspier.item.api;

import com.vinspier.common.template.ResponseTemplate;
import com.vinspier.item.pojo.SpecGroup;
import com.vinspier.item.pojo.SpecParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 规格参数和规格分组统一操作接口
 * */
@RequestMapping("spec")
public interface SpecApi {

    /**
     * 根据条件查询规格参数
     * @param gid
     * @return
     */
    @GetMapping("params")
    ResponseTemplate<List<SpecParam>> queryParams(@RequestParam(value = "gid", required = false)Long gid,
                                                 @RequestParam(value = "cid", required = false)Long cid,
                                                 @RequestParam(value = "generic", required = false)Boolean generic,
                                                 @RequestParam(value = "searching", required = false)Boolean searching);

    /**
     * 根据分类id查询分组
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    ResponseTemplate<List<SpecGroup>> queryGroupsByCid(@PathVariable("cid")Long cid);

    /**
     * 根据分类id查询分组
     * 携带每个分组的具体规格参数
     * @param cid
     * @return
     */
    @GetMapping("{cid}")
    ResponseTemplate<List<SpecGroup>> querySpecsByCid(@PathVariable("cid") Long cid);

}
