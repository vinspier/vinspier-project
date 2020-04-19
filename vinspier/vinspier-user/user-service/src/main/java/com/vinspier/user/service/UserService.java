package com.vinspier.user.service;

import com.vinspier.user.pojo.User;

public interface UserService {

    /**
     * 校验用户名和手机号的合法性
     * @param data
     * @param type
     * @return
     */
    Boolean checkUer(String data, Long type);

    /**
     * 验证码
     * @param phone
     */
    void sendVerifyCode(String phone);

    /**
     * 用户注册功能
     * @param user
     * @param code
     */
    void register(User user, String code);

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    User queryUser(String username, String password);

}
