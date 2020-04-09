package com.vinspier.item.api;

import com.vinspier.common.template.ResponseTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    ResponseTemplate queryParams(@RequestParam(value = "gid", required = false)Long gid,
                                        @RequestParam(value = "cid", required = false)Long cid,
                                        @RequestParam(value = "generic", required = false)Boolean generic,
                                        @RequestParam(value = "searching", required = false)Boolean searching);

}
