package com.jxduhai.manager.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxduhai.common.TaoResult;
//import com.jxduhai.manager.mapper.ContentMapper;
import com.jxduhai.manager.pojo.Content;
import com.jxduhai.manager.redis.RedisUtils;
import com.jxduhai.manager.redis.RedisUtilsClusterImpl;
import com.jxduhai.manager.redis.RedisUtilsPoolImpl;
import com.jxduhai.manager.service.ContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/****
 *@author yxw
 *@date 2018/3/16
 */
@Service
public class ContentServiceImpl extends BaseServiceImpl<Content> implements ContentService {

//    @Autowired
//    private ContentMapper contentMapper;

    @Autowired
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private RedisUtils redisUtils;


    @Value("${TAOTAO_PORTAL_AD_KEY}")
    private String TAOTAO_PORTAL_AD_KEY;

    /*后台查询*/
    @Override
    public TaoResult<Content> queryPageByContentCategoryId(Integer page, Integer rows, Long categoryId) {
        //根据分类
        PageHelper.startPage(page,rows);
        Content content = new Content();
        content.setCategoryId(categoryId);
        List<Content> contents = super.queryListByWhere(content);
        //将查询出的数据进行封装
        PageInfo<Content> info = new PageInfo<>(contents);
        TaoResult<Content> taoResult = new TaoResult<>();
        taoResult.setTotal(info.getTotal());
        taoResult.setRows(contents);
        return taoResult;
    }

    /*前台查询*/
    @Override
    public String queryContentByCategoryId(Long categoryId) {

//        RedisUtils redisUtils = new RedisUtilsPoolImpl();

        String s = redisUtils.get(TAOTAO_PORTAL_AD_KEY);

        if (StringUtils.isNotBlank(s)){
            return s;
        }

        Content content = new Content();
        content.setCategoryId(categoryId);
        //查询出所有的大广告内容
        List<Content> contents = super.queryListByWhere(content);
        //封装数据,再讲起返回给前端显示
        List<Map<String,Object>> list = new ArrayList<>();
        for (Content cont : contents) {
            Map<String,Object> map = new HashMap<>();
            map.put("srcB",cont.getPic());
            map.put("height",240);
            map.put("alt","");
            map.put("width",670);
            map.put("src",cont.getPic());
            map.put("widthB",550);
            map.put("href",cont.getUrl());
            map.put("height",240);
            list.add(map);
        }
        //将集合对象转换成json数据
        String json = null;
        try {
            json = MAPPER.writeValueAsString(list);
            redisUtils.set(TAOTAO_PORTAL_AD_KEY,json,3600*24*7);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
