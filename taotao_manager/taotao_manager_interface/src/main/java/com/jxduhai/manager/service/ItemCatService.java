package com.jxduhai.manager.service;

import com.jxduhai.manager.pojo.ItemCat;

import java.util.List;

/****
 *@author yxw
 *@date 2018/3/11
 */
public interface ItemCatService extends BaseService<ItemCat>{
    List<ItemCat> findByPage(int page, int size);

    List<ItemCat> queryItemCatByParentId(Long parentId);
}
