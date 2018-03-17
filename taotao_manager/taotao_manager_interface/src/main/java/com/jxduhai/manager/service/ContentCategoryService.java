package com.jxduhai.manager.service;

import com.jxduhai.manager.pojo.ContentCategory;

import java.util.List;

/****
 *@author yxw
 *@date 2018/3/16
 */
public interface ContentCategoryService extends BaseService<ContentCategory> {
    ContentCategory addContentCategory(ContentCategory contentCategory);

    void deleteContentCatgoryByIds(ContentCategory contentCategory);

//    void updateContentCategory(ContentCategory contentCategory);
}
