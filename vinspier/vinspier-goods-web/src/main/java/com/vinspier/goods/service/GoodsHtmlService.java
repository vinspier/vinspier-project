package com.vinspier.goods.service;

public interface GoodsHtmlService {

    /**
     * 新建线程处理页面静态化
     * @param spuId
     */
    void asyncExecute(Long spuId);

    /**
     * 创建html页面
     *
     * @param spuId
     */
    void createHtml(Long spuId);

    /**
     * 删除html页面
     *
     * @param spuId
     */
    void deleteHtml(Long spuId);
}
