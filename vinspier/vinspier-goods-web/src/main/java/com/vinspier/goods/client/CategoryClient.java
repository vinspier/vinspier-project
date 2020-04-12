package com.vinspier.goods.client;

import com.vinspier.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 商品分类索引操作客户端
 * */
@FeignClient(value = "item-service")
public interface CategoryClient extends CategoryApi{
}
