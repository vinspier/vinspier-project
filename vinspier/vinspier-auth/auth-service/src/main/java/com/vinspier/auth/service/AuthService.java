package com.vinspier.auth.service;

import com.vinspier.auth.pojo.UserInfo;

public interface AuthService {

   /** 登录授权 */
   String authentication(String username, String password);

   UserInfo verify();
}
