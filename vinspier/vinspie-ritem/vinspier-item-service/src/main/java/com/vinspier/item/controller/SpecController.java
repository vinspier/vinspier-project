package com.vinspier.item.controller;


import com.vinspier.common.template.ResponseTemplate;
import com.vinspier.item.pojo.SpecGroup;
import com.vinspier.item.pojo.SpecParam;
import com.vinspier.item.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "spec")
public class SpecController {

    @Autowired
    private SpecService specService;

    /**
     * 根据分类id查询分组
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    @ResponseBody
    public ResponseTemplate queryGroupsByCid(@PathVariable("cid")Long cid){
        List<SpecGroup> groups = this.specService.queryGroupsByCid(cid);
        if (CollectionUtils.isEmpty(groups)){
            return ResponseTemplate.ok("no data");
        }
        return ResponseTemplate.ok(groups);
    }

    /**
     * 新增 sku特性分组
     * */
    @PostMapping("group")
    @ResponseBody
    public ResponseTemplate saveGroup(SpecGroup group){
        this.specService.saveGroup(group);
        return ResponseTemplate.ok();
    }

    /**
     * 更新 sku特性分组
     * */
    @PutMapping("group")
    @ResponseBody
    public ResponseTemplate updateGroup(SpecGroup group){
        this.specService.saveGroup(group);
        return ResponseTemplate.ok();
    }

    /**
     * 删除 sku特性分组
     * */
    @RequestMapping("group/{gid}")
    @ResponseBody
    public ResponseTemplate deleteGroup(@PathVariable("gid")Long gid){
        specService.deleteGroup(gid);
        return ResponseTemplate.ok();
    }

    /**************************************************** 特性分组-参数值 分割线**************************************************************/

    /**
     * 根据条件查询规格参数
     * @param gid
     * @return
     */
    @GetMapping("params")
    @ResponseBody
    public ResponseTemplate queryParams(@RequestParam(value = "gid", required = false)Long gid,
                                                       @RequestParam(value = "cid", required = false)Long cid,
                                                       @RequestParam(value = "generic", required = false)Boolean generic,
                                                       @RequestParam(value = "searching", required = false)Boolean searching){
        List<SpecParam>  params = this.specService.queryParams(gid,cid,generic,searching);
        if (CollectionUtils.isEmpty(params)){
            return ResponseTemplate.ok("no data");
        }
        return ResponseTemplate.ok(params);
    }

    /**
     * 新增 sku特性分组特性属性
     * */
    @PostMapping("param")
    @ResponseBody
    public ResponseTemplate saveParam(SpecParam param){
        this.specService.saveParam(param);
        return ResponseTemplate.ok();
    }

    /**
     * 更新 sku特性分组特性属性
     * */
    @PutMapping("param")
    @ResponseBody
    public ResponseTemplate updateParam(SpecParam param){
        this.specService.updateParam(param);
        return ResponseTemplate.ok();
    }

    /**
     * 删除 sku分组特性属性
     * */
    @RequestMapping("param/{pid}")
    @ResponseBody
    public ResponseTemplate deleteParam(@PathVariable("pid")Long pid){
        specService.deleteParam(pid);
        return ResponseTemplate.ok();
    }
}
