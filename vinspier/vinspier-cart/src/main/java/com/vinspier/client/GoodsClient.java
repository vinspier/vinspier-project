package com.vinspier.client;

import com.vinspier.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @auther Mr.Liao
 * @date 2019/5/24 11:03
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
