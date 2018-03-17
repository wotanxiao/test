package com.jxduhai.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxduhai.common.TaoResult;
import com.jxduhai.manager.mapper.ContentMapper;
import com.jxduhai.manager.pojo.Content;
import com.jxduhai.manager.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/****
 *@author yxw
 *@date 2018/3/16
 */
@Service
public class ContentServiceImpl extends BaseServiceImpl<Content> implements ContentService {

    @Autowired
    private ContentMapper contentMapper;

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
}
