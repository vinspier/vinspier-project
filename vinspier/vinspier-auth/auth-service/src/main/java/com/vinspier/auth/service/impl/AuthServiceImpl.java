package com.vinspier.auth.service.impl;

import com.vinspier.auth.client.UserClient;
import com.vinspier.auth.config.JwtProperties;
import com.vinspier.auth.pojo.UserInfo;
import com.vinspier.auth.service.AuthService;
import com.vinspier.auth.utils.JwtUtils;
import com.vinspier.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties properties;

    @Override
    public String authentication(String username, String password) {
        try {
            // 调用 用户 微服务，执行查询用户信息
            User user = (this.userClient.queryUser(username, password)).getData();

            // 如果查询结果为null，则直接返回null
            if (user == null) {
                return null;
            }

            // 如果有查询结果，则生成token
            String token = JwtUtils.generateToken(new UserInfo(user.getId(), user.getUsername()), properties.getPrivateKey(), properties.getExpire());
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
