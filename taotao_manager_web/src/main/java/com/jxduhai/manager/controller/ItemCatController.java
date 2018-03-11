package com.jxduhai.manager.controller;


import com.jxduhai.manager.pojo.ItemCat;


import com.jxduhai.manager.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/****
 *@author yxw
 *@date 2018/3/11
 */
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @ResponseBody
    @RequestMapping("/page")
    public List<ItemCat> findByPage(int page, int size){

        List<ItemCat> list = itemCatService.findByPage(page,size);
        return list;
    }
}
