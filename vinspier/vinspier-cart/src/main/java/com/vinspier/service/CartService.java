package com.vinspier.service;


import com.vinspier.auth.pojo.UserInfo;
import com.vinspier.client.GoodsClient;
import com.vinspier.common.util.JsonUtils;
import com.vinspier.interceptor.LoginInterceptor;
import com.vinspier.item.pojo.Sku;
import com.vinspier.pojo.Cart;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther Mr.Liao
 * @date 2019/5/24 10:27
 */
@Service
public class CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private GoodsClient goodsClient;

    private static final String KEY_PEFIX = "user:cart:";
    public void addCart(Cart cart) {
        // 获取用户信息
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        // 查询购物车记录
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(KEY_PEFIX + userInfo.getId());
        String key = cart.getSkuId().toString();
        Integer num = cart.getNum();
        // 判断当前商品是否在购物车中
        if (hashOperations.hasKey(key)){
            // 在，更新数量
            String cartJson = hashOperations.get(key).toString();
            cart = JsonUtils.parse(cartJson, Cart.class);
            cart.setNum(cart.getNum() + num);
        } else {
            // 不在，新增购物车
            Sku sku = this.goodsClient.querySkuBySkuId(cart.getSkuId());
            cart.setUserId(userInfo.getId());
            cart.setTitle(sku.getTitle());
            cart.setOwnSpec(sku.getOwnSpec());
            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(),",")[0]);
            cart.setPrice(sku.getPrice());
        }
        hashOperations.put(key, JsonUtils.serialize(cart));
    }

    /**
     * 查询购物车
     * @return
     */
    public List<Cart> queryCarts() {
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        // 判断用户是否有购物车记录
        if (!this.redisTemplate.hasKey(KEY_PEFIX + userInfo.getId())){
            return null;
        }
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(KEY_PEFIX + userInfo.getId());
        // 获取购物车Map中所有cart值集合
        List<Object> cartsJson = hashOperations.values();
        // 如果购物车集合为空，直接返回null
        if (CollectionUtils.isEmpty(cartsJson)){
            return null;
        }

        // 把List<Object>集合转化为List<Cart>集合
        List<Cart> carts = cartsJson.stream().map(cartJson -> JsonUtils.parse(cartJson.toString(), Cart.class)).collect(Collectors.toList());
        return carts;
    }

    /**
     * 修改购物车商品数量
     * @param cart
     */
    public void updateNum(Cart cart) {
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        // 判断用户是否有购物车记录
        if (!this.redisTemplate.hasKey(KEY_PEFIX + userInfo.getId())){
            return;
        }
        Integer num = cart.getNum();

        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(KEY_PEFIX + userInfo.getId());
        String cartJson = hashOperations.get(cart.getSkuId().toString()).toString();
        cart = JsonUtils.parse(cartJson, Cart.class);
        cart.setNum(num);
        hashOperations.put(cart.getSkuId().toString(),JsonUtils.serialize(cart));
    }
}
