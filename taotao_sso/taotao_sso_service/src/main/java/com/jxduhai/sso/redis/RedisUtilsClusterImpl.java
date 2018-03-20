package com.jxduhai.sso.redis;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

/****
 *@author yxw
 *@date 2018/3/17
 */

public class RedisUtilsClusterImpl implements RedisUtils {

    @Autowired
    private JedisCluster jedisCluster;

//    public void setJedisCluster(JedisCluster jedisCluster) {
//        this.jedisCluster = jedisCluster;
//    }

    @Override
    public void set(String key, String value) {
        jedisCluster.set(key,value);
    }

    @Override
    public String get(String key) {
        return jedisCluster.get(key);
    }

    @Override
    public void del(String key) {
        jedisCluster.del(key);
    }

    @Override
    public void expire(String key, Integer seconds) {
        jedisCluster.expire(key,seconds);
    }

    @Override
    public void set(String key, String value, Integer seconds) {
        jedisCluster.set(key,value);
        jedisCluster.expire(key,seconds);
    }

    @Override
    public Long incr(String key) {
        return jedisCluster.incr(key);
    }
}
