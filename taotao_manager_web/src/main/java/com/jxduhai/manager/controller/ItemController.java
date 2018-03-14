package com.jxduhai.manager.controller;

import com.jxduhai.common.TaoReslt;
import com.jxduhai.manager.pojo.Item;
import com.jxduhai.manager.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/****
 *@author yxw
 *@date 2018/3/13
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;


    /**
     * 添加商品
     *  type: "POST",
        url: "/rest/item",
     * */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String saveItem(Item item, String desc){

        String msg = "0";
        try {
            itemService.saveItem(item, desc);
        } catch (Exception e) {
            msg = "1";
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 商品查询
     * url:'/rest/item',method:'get',pageSize:30
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public TaoReslt<Item> findItemByPage(@RequestParam(defaultValue = "1") Integer page, Integer rows){


        TaoReslt<Item> taoReslt = itemService.queryItemByPage(page,rows);


        return taoReslt;
    }
}
