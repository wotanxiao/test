package com.jxduhai.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxduhai.common.TaoResult;
import com.jxduhai.manager.pojo.Item;
import com.jxduhai.manager.pojo.ItemDesc;
import com.jxduhai.manager.service.ItemDescService;
import com.jxduhai.manager.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public TaoResult<Item> queryItemByPage(Integer page, Integer rows) {

        TaoResult<Item> taoResult = new TaoResult<>();
        PageHelper.startPage(page,rows);
        List<Item> items = super.queryByPage(page, rows);
        PageInfo<Item> info = new PageInfo<>(items);

        taoResult.setRows(items);
        taoResult.setTotal(info.getTotal());
        return taoResult;
    }
}
