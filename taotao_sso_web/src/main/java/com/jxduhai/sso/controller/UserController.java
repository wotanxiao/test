package com.jxduhai.sso.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jxduhai.manager.pojo.User;
import com.jxduhai.sso.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/****
 *@author yxw
 *@date 2018/3/20
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    protected UserService userService;

    private Logger logger = Logger.getLogger(UserController.class);

    /*
    *   请求方法	GET
        URL	http://sso.taotao.com/user/check/{param}/{type}
        参数说明	param:要验证的内容
        type:要验证的数据类型
        格式如：zhangsan/ 1，其中zhangsan是校验的数据，type为类型，参数1、2、3分别代表username、phone、email

        示例	http://sso.taotao.com/user/check/zhangsan/1
        返回值	数据可用:
        true
        数据不可用:
        false
*/
    @RequestMapping(value = "/check/{param}/{type}", method = RequestMethod.GET)
    public ResponseEntity<String> cleck(@PathVariable String param, @PathVariable Integer type, String callback){
        if (type != null && (type > 3 || type < 1)){
            //类型范围不对,返回参数错误
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        boolean flag = false;
        try {
            flag = userService.cleck(param,type);
            return ResponseEntity.ok(callback + "(" + flag + ")" );
        } catch (Exception e) {
            logger.error(e);
        }
        //查不到数据,
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /*  请求方法	GET
        URL	http://sso.taotao.com/user/{ticket}
        参数	ticket //用户登录凭证
        参数说明

        示例
        http://sso.taotao.com/user/fe5cb546aeb3ce1bf37abcb08a40493e

        返回值	查询结果：
        {"id":1,"username":"xuziyi","phone":"15800807944",
        "email":"11223344@qq.com","created":1414119176000,
        "updated":1414119179000}"}
*/
    @RequestMapping(value = "/{ticket}", method = RequestMethod.GET)
    public ResponseEntity<String> queryUserByTicket(@PathVariable String ticket, String callback){
        try {
            User user = userService.queryUserByTicket(ticket);
            if (user != null) {
                String s = MAPPER.writeValueAsString(user);
                return ResponseEntity.ok(callback + "(" + s + ")");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error(e);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}
