package com.jxduhai.sso.service;

import com.jxduhai.manager.pojo.User;

/****
 *@author yxw
 *@date 2018/3/20
 */
public interface UserService {
    boolean cleck(String param, Integer type);

    User queryUserByTicket(String ticket);

    void doRegister(User user);

    String doLogin(User user);
}
