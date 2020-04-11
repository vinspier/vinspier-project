package com.vinspier.search.vo;

import java.util.Map;

/**
 * 页面传入的参数
 * */
public class SearchVo {

    private String key;// 搜索条件

    private Integer page;// 当前页

    private String sortBy;// 排序字段

    private Boolean descending;// 是否降序

    private Map<String, Object> filter;

    private static final Integer DEFAULT_SIZE = 10;// 每页大小，不从页面接收，而是固定大小
    private static final Integer DEFAULT_PAGE = 1;// 默认页

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPage() {
        if(page == null){
            return DEFAULT_PAGE;
        }
        // 获取页码时做一些校验，不能小于1
        return Math.max(DEFAULT_PAGE, page);
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Boolean getDescending() {
        return descending;
    }

    public void setDescending(Boolean descending) {
        this.descending = descending;
    }

    public Map<String, Object> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, Object> filter) {
        this.filter = filter;
    }

    public Integer getSize() {
        return DEFAULT_SIZE;
    }
}
