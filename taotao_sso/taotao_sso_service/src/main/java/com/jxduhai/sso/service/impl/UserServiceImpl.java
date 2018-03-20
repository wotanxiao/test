package com.jxduhai.sso.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jxduhai.manager.mapper.UserMapper;
import com.jxduhai.manager.pojo.User;
import com.jxduhai.sso.redis.RedisUtils;
import com.jxduhai.sso.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/****
 *@author yxw
 *@date 2018/3/20
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    private Logger logger = Logger.getLogger(UserServiceImpl.class);

    //对用户的参数进行查询
    @Override
    public boolean cleck(String param, Integer type) {
        User user = new User();
        switch (type){
            case 1 :
            user.setUsername(param);
            break;
            case 2:
            user.setPhone(param);
            break;
            case 3:
            user.setEmail(param);
            break;
        }
        int i = this.userMapper.selectCount(user);
        //表示找到了
        if (i > 0) {
            return false;
        }
        return true;
    }

    //票据前缀
    @Value("${TAOTAO_TICKET_KEY}")
    private String TAOTAO_TICKET_KEY;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private static final ObjectMapper MAPPER = new ObjectMapper();

    //根据用户携带的ticket查询redis
    @Override
    public User queryUserByTicket(String ticket) {

        User user = null;
        String s = redisUtils.get(TAOTAO_TICKET_KEY + ticket);
        //重复在活动状态,重置redis中此key的时间
        if (StringUtils.isNotBlank(s)){
            redisUtils.expire(TAOTAO_TICKET_KEY + ticket,30*60);
        }
        try {
            user = MAPPER.readValue(s,User.class);
        } catch (IOException e) {
            logger.error(e);
        }
        return user;
    }

    //用户注册功能
    @Override
    public void doRegister(User user) {
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //对密码使用MD5加密
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        userMapper.insert(user);

    }

    //用户登录功能
    @Override
    public String doLogin(User user) {
        //首先先查询数据库,看用户是否存在,先给登录的密码进行密,否则无法匹配密码
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        User login = userMapper.selectOne(user);
        if (login == null){
            return null;
        }
        //用户存在的话,就需要将用户的信息存进Redis中,使用统一的用户key+MD5加密ID
        String ticket = DigestUtils.md5Hex(login.getId() + "");
        try {
            redisUtils.set(TAOTAO_TICKET_KEY + ticket, MAPPER.writeValueAsString(login), 30*60);
            return ticket;
        } catch (JsonProcessingException e) {
            logger.error(e);
        }
        return null;
    }
}
