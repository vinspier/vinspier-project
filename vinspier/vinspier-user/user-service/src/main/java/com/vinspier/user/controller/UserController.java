package com.vinspier.user.controller;

import com.vinspier.common.template.ResponseTemplate;
import com.vinspier.user.pojo.User;
import com.vinspier.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 校验用户名和手机号的合法性
     * @param data
     * @param type
     * @return
     */
    @GetMapping("/check/{data}/{type}")
    @ResponseBody
    public ResponseEntity<Boolean> checkUser(@PathVariable("data") String data, @PathVariable("type") Long type){
        Boolean bool = this.userService.checkUer(data, type);
        if (bool == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bool);
    }

    /**
     * 发送验证码请求
     * @param phone
     * @return
     */
    @PostMapping("sendCode")
    @ResponseBody
    public ResponseTemplate sendVerifyCode(@RequestParam("phone") String phone){
        this.userService.sendVerifyCode(phone);
        return ResponseTemplate.ok("短信验证码已发送");
    }
    /**
     * 注册功能
     */
    @PostMapping("register")
    @ResponseBody
    public ResponseTemplate register(@Valid User user, @RequestParam("code")String code){
        this.userService.register(user, code);
        return ResponseTemplate.ok("用户注册成功");
    }

    /**
     * 根据用户名和密码查询用户
     * 为auth授权中心提供验证账户信息
     */
    @GetMapping("query")
    @ResponseBody
    public ResponseTemplate<User> queryUser(@RequestParam("username")String username,@RequestParam("password")String password){
        System.out.println("进入UserController：" + username + ": "+password);
        User user = this.userService.queryUser(username, password);
        System.out.println("从数据库查询：" + username + "的结果是：" + user);
        return ResponseTemplate.ok(user);
    }

}
