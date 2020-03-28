package com.vinspier.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 文件上传服务
 * */
@Service
public class UploadServiceImpl implements UploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadService.class);

    private static final List<String> CONTENT_TYPES = Arrays.asList("image/jpeg", "image/gif","image/png","image/jpg");

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    @Override
    public String upload(MultipartFile file) throws IOException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String validateResult = validateFile(file);
        if (StringUtils.hasText(validateResult)){
            return validateResult;
        }
        // ToDo 定义全局报错返回
        try {
            File uploadFile = new File("G:\\vinspier-project\\upload\\" + dateFormat.format(new Date()) + "\\" + file.getOriginalFilename());
            if (!uploadFile.getParentFile().exists()){
                uploadFile.getParentFile().mkdirs();
            }
            // 保存到服务器
            // ToDo 保存到文件服务器
            file.transferTo(uploadFile);
            // 生成url地址，返回
            return "http://image.vinspier.com/" + file.getOriginalFilename();
        } catch (IOException e) {
            LOGGER.info("服务器内部错误：{}", file.getOriginalFilename());
            e.printStackTrace();
            return "文件写入磁盘错误";
        }
    }

    @Override
    public String uploadFast(MultipartFile file) throws FileNotFoundException,IOException {
        String validateResult = validateFile(file);
        if (StringUtils.hasText(validateResult)){
            return validateResult;
        }
        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        StorePath storePath = this.storageClient.uploadFile(file.getInputStream(), file.getSize(), ext, null);
        // 生成url地址，返回
        return "http://image.vinspier.com/" + storePath.getFullPath();
    }

    @Override
    public String uploadFastThumb(MultipartFile file) throws FileNotFoundException,IOException{
        String validateResult = validateFile(file);
        if (StringUtils.hasText(validateResult)){
            return validateResult;
        }
        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), ext, null);
        return "http://image.vinspier.com/" + storePath.getFullPath();
    }

    /**
     * 验证文件
     * */
    private String validateFile(MultipartFile file) throws IOException{
        String originalFilename = file.getOriginalFilename();
        // 校验文件的类型
        String contentType = file.getContentType();
        if (!CONTENT_TYPES.contains(contentType)){
            // 文件类型不合法，直接返回null
            LOGGER.info("文件类型不合法：{}", originalFilename);
            return "文件类型不合法";
        }
        // 校验文件的内容
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        if (bufferedImage == null){
            LOGGER.info("文件内容不合法：{}", originalFilename);
            return null;
        }
        return null;
    }
}
