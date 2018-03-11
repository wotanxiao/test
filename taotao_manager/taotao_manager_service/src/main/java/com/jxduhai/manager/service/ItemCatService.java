package com.jxduhai.manager.service;

import com.jxduhai.manager.pojo.ItemCat;

import java.util.List;

/****
 *@author yxw
 *@date 2018/3/11
 */
public interface ItemCatService {
    List<ItemCat> findByPage(int page, int size);
}
