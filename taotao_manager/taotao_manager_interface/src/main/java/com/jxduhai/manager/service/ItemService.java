package com.jxduhai.manager.service;

import com.jxduhai.common.TaoResult;
import com.jxduhai.manager.pojo.Item;

/****
 *@author yxw
 *@date 2018/3/13
 */
public interface ItemService extends BaseService<Item>{


    /**
     * 添加商品
     * @param item
     */
    void saveItem(Item item, String desc);

    TaoResult<Item> queryItemByPage(Integer page, Integer rows);
}
