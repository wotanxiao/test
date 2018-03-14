package com.jxduhai.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.jxduhai.manager.mapper.ItemCatMapper;
import com.jxduhai.manager.pojo.ItemCat;
import com.jxduhai.manager.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/****
 *@author yxw
 *@date 2018/3/11
 */
@Service
public class ItemCatServiceImpl extends BaseServiceImpl<ItemCat> implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public List<ItemCat> findByPage(int page, int size) {
        PageHelper.startPage(page,size);
        List<ItemCat> list = itemCatMapper.select(null);
        return list;
    }


}
