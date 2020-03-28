package com.vinspier.upload.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface UploadService {

    /**
     * 上传文件到本地服务器
     * */
    String upload(MultipartFile file)  throws IOException;

    /**
     * fastDFS客户端上传到fastFDS服务器
     * 返回全路径
     * */
    String uploadFast(MultipartFile file) throws IOException;

    /**
     * fastDFS客户端上传到fastFDS服务器
     * 返回全路径 生成缩略图
     * */
    String uploadFastThumb(MultipartFile file) throws IOException;

}
