package com.vinspier.upload.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.vinspier.common.template.ResponseTemplate;
import com.vinspier.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * FastDFS分布式文件上传
 * */
@Controller
@RequestMapping("uploadFast")
public class FastDfsController {

    @Autowired
    private UploadService uploadService;

    /**
     * 图片上传
     * @param file
     * @return
     */
    @PostMapping("upload")
    @ResponseBody
    public ResponseTemplate upload(@RequestParam("file") MultipartFile file) throws IOException {
        String path = uploadService.uploadFast(file);
        return ResponseTemplate.ok(path);
    }

    /**
     * 图片上传 生成缩略图
     * @param file
     * @return
     */
    @PostMapping("uploadThumb")
    @ResponseBody
    public ResponseTemplate uploadThumb(@RequestParam("file") MultipartFile file) throws IOException {
        String path = uploadService.uploadFastThumb(file);
        return ResponseTemplate.ok(path);
    }

}
