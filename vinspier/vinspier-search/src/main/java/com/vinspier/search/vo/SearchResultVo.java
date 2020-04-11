package com.vinspier.search.vo;

import com.vinspier.common.pojo.PageResult;
import com.vinspier.item.pojo.Brand;
import com.vinspier.search.pojo.Goods;

import java.util.List;
import java.util.Map;

/**
 * @auther Mr.Liao
 * @date 2019/5/15 19:01
 */
public class SearchResultVo extends PageResult<Goods> {
    private List<Map<String, Object>> categories;
    private List<Brand> brands;
    private List<Map<String, Object>> specs;
    public SearchResultVo() {
    }

    public SearchResultVo(List<Map<String, Object>> categories, List<Brand> brands, List<Map<String, Object>> specs) {
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }

    public SearchResultVo(Long total, List<Goods> items, List<Map<String, Object>> categories, List<Brand> brands, List<Map<String, Object>> specs) {
        super(total, items);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }

    public SearchResultVo(Long total, Integer totalPage, List<Goods> items, List<Map<String, Object>> categories, List<Brand> brands, List<Map<String, Object>> specs) {
        super(total, totalPage, items);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }

    public List<Map<String, Object>> getCategories() {
        return categories;
    }

    public void setCategories(List<Map<String, Object>> categories) {
        this.categories = categories;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

    public List<Map<String, Object>> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Map<String, Object>> specs) {
        this.specs = specs;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "categories=" + categories +
                ", brands=" + brands +
                ", specs=" + specs +
                '}';
    }
}
