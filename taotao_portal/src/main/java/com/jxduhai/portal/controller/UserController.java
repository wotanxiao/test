package com.jxduhai.portal.controller;

import com.jxduhai.manager.pojo.User;
import com.jxduhai.portal.utils.CookieUtils;
import com.jxduhai.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/****
 *@author yxw
 *@date 2018/3/20
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = Logger.getLogger(UserController.class);

    //注入票据kety
    @Value("${TT_TICKET}")
    private String TT_TICKET;

    /**
     * doRegister
     */
    @RequestMapping("/doRegister")
    @ResponseBody
    public Map<String, Object> doRegister(User user){
        Map<String, Object> result = new HashMap<>();
        try {
            userService.doRegister(user);
            result.put("status",200);
        } catch (Exception e) {
            logger.error(e);
        }
        return result;
    }


    /** 用户登录的逻辑*/
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doLogin(User user, HttpServletRequest request, HttpServletResponse response){

        //查询数据库,并将返回一个ticket
        Map<String,Object> result = new HashMap<>();
        try {
            String ticket = userService.doLogin(user);
            if (StringUtils.isNotBlank(ticket)) {
                //表示已经查询到了数据,返回200验证码,并将ticket存放进用户的cookie中
                CookieUtils.setCookie(request,response,TT_TICKET,ticket,true);
                result.put("status","200");
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return result;
    }
}
