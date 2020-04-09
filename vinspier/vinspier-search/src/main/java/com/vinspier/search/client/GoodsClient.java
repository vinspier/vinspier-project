package com.vinspier.search.client;

import com.vinspier.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 商品索引操作客户端
 * */
@FeignClient(value = "item-service")
public interface GoodsClient extends GoodsApi{
}
