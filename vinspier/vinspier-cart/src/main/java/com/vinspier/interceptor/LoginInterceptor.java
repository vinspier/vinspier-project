package com.vinspier.interceptor;


import com.vinspier.auth.pojo.UserInfo;
import com.vinspier.auth.utils.JwtUtils;
import com.vinspier.common.util.CookieUtils;
import com.vinspier.config.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @auther Mr.Liao
 * @date 2019/5/24 9:27
 */

@Component
@EnableConfigurationProperties(JwtProperties.class)
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtProperties jwtProperties;

    private static final ThreadLocal<UserInfo> THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取cookie中token
        String token = CookieUtils.getCookieValue(request, this.jwtProperties.getCookieName());
        // 解析token，获取用户信息
        UserInfo userInfo = JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());
        if (userInfo == null){
            return false;
        }
        //把userInfo放入线程变量
        THREAD_LOCAL.set(userInfo);
        return true;
    }
    public static UserInfo getUserInfo(){
        return THREAD_LOCAL.get();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清空线程的局部变量，因为使用的tomcat的线程池，线程不会结束，不会释放线程的局部变量
        THREAD_LOCAL.remove();
    }
}
