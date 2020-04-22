package com.vinspier.auth.controller;

import com.vinspier.auth.config.JwtProperties;
import com.vinspier.auth.pojo.UserInfo;
import com.vinspier.auth.service.AuthService;
import com.vinspier.auth.utils.JwtUtils;
import com.vinspier.common.template.ResponseTemplate;
import com.vinspier.common.util.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties prop;

    /**
     * 登录授权
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("accredit")
    public ResponseTemplate authentication(@RequestParam("username") String username, @RequestParam("password") String password,
            HttpServletRequest request, HttpServletResponse response) {
        // 登录校验
        String token = authService.authentication(username, password);
        if (StringUtils.isBlank(token)) {
            ResponseTemplate.error();
        }
        // 将token写入cookie,并指定httpOnly为true，防止通过JS获取和修改
        CookieUtils.setCookie(request, response, prop.getCookieName(), token, prop.getCookieMaxAge(), null, true);
        return ResponseTemplate.ok();
    }

    /**
     * 验证登录用户，返回user对象
     */
    @GetMapping("verify")
    public ResponseTemplate<UserInfo> verify(@CookieValue("access_token") String access_token,HttpServletRequest request,HttpServletResponse response){
        try {
            UserInfo user = JwtUtils.getInfoFromToken(access_token, this.prop.getPublicKey());
            if (user == null){
                return ResponseTemplate.error("用户信息已失效");
            }

            //刷新jwt中有效时间
            access_token = JwtUtils.generateToken(user, this.prop.getPrivateKey(), this.prop.getExpire());

            //刷新cookie中的有效时间
            CookieUtils.setCookie(request,response,this.prop.getCookieName(),access_token,this.prop.getExpire());

            return ResponseTemplate.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
