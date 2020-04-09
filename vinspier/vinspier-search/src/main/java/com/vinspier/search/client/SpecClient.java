package com.vinspier.search.client;

import com.vinspier.item.api.SpecApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 商品规格分组和规格参数索引操作客户端
 * */
@FeignClient(value = "item-service")
public interface SpecClient extends SpecApi{
}
