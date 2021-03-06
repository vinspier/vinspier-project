package com.vinspier.goods.service.impl;

import com.vinspier.goods.service.GoodsHtmlService;
import com.vinspier.goods.service.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Service
public class GoodsHtmlServiceImpl implements GoodsHtmlService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private ExecutorService executorService;

    public static final String NGINX_PREFIX_PATH = "D:\\nginx-1.14.2\\html\\item\\";

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsHtmlService.class);

    /**
     * 新建线程处理页面静态化
     * @param spuId
     */
    @Override
    public void asyncExecute(Long spuId) {
        executorService.submit(() -> createHtml(spuId));
    }

    /**
     * 创建html页面
     *
     * @param spuId
     * @throws Exception
     */
    @Override
    public void createHtml(Long spuId) {
        PrintWriter writer = null;
        try {
            // 获取页面数据
            Map<String, Object> spuMap = this.goodsService.loadData(spuId);

            // 创建thymeleaf上下文对象
            Context context = new Context();
            // 把数据放入上下文对象
            context.setVariables(spuMap);

            // 创建输出流
            File file = new File(NGINX_PREFIX_PATH + spuId + ".html");
            writer = new PrintWriter(file);

            // 执行页面静态化方法
            templateEngine.process("item", context, writer);
        } catch (Exception e) {
            LOGGER.error("页面静态化出错：{}，"+ e, spuId);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    @Override
    public void deleteHtml(Long spuId) {
        File file = new File(NGINX_PREFIX_PATH + spuId + ".html");
        if (file.exists()){
            file.delete();
        }
       // file.deleteOnExit();
    }
}
