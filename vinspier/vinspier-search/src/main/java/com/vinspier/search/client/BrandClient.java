package com.vinspier.search.client;

import com.vinspier.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 商品品牌索引操作客户端
 * */
@FeignClient(value = "item-service")
public interface BrandClient extends BrandApi{
}
