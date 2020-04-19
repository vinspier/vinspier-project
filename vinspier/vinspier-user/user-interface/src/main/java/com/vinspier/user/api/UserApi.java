package com.vinspier.user.api;


import com.vinspier.user.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


public interface UserApi {
    @GetMapping("query")
    User queryUser(@RequestParam("username") String username, @RequestParam("password") String password);
}
