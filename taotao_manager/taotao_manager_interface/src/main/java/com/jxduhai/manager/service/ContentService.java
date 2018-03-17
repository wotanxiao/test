package com.jxduhai.manager.service;

import com.jxduhai.common.TaoResult;
import com.jxduhai.manager.pojo.Content;

/****
 *@author yxw
 *@date 2018/3/16
 */
public interface ContentService extends BaseService<Content> {
    TaoResult<Content> queryPageByContentCategoryId(Integer page, Integer rows, Long categoryId);
}
