package com.jxduhai.manager.service.impl;

import com.jxduhai.manager.pojo.Item;
import com.jxduhai.manager.pojo.ItemDesc;
import com.jxduhai.manager.service.ItemDescService;
import com.jxduhai.manager.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/****
 *@author yxw
 *@date 2018/3/13
 */
@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {

    @Autowired
    private ItemDescService itemDescService;

    @Override
    public void saveItem(Item item, String desc) {

        //新增商品,先给其设置上架状态
        item.setStatus(1);
        super.save(item);

        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);

        itemDescService.save(itemDesc);
    }
}
