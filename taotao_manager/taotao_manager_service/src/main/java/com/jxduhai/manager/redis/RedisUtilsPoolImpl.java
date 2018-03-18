package com.jxduhai.manager.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/****
 *@author yxw
 *@date 2018/3/17
 */
//@Component
public class RedisUtilsPoolImpl implements RedisUtils {

    @Autowired
    private JedisPool jedisPool;

    @Override
    public void set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        jedis.set(key,value);
        jedis.close();
    }

    @Override
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String s = jedis.get(key);
        jedis.close();
        return s;
    }

    @Override
    public void del(String key) {
        Jedis jedis = jedisPool.getResource();
        jedis.del(key);
        jedis.close();
    }

    @Override
    public void expire(String key, Integer seconds) {
        Jedis jedis = jedisPool.getResource();
        jedis.expire(key,seconds);
        jedis.close();
    }

    @Override
    public void set(String key, String value, Integer seconds) {
        Jedis jedis = jedisPool.getResource();
        jedis.set(key,value);
        jedis.expire(key,seconds);
        jedis.close();
    }

    @Override
    public Long incr(String key) {
        Jedis jedis = jedisPool.getResource();
        Long incr = jedis.incr(key);
        jedis.close();
        return incr;
    }
}
