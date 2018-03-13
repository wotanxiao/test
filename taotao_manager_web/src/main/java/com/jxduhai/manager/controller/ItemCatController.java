package com.jxduhai.manager.controller;


import com.jxduhai.manager.pojo.ItemCat;


import com.jxduhai.manager.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/****
 *@author yxw
 *@date 2018/3/11
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;


    /**
     * 用于测试是否可以分页从数据库中查询出数据*/
    @ResponseBody
    @RequestMapping("/page")
    public List<ItemCat> findByPage(int page, int rows){

//        List<ItemCat> list = itemCatService.findByPage(page,size);
//        return list;

        return itemCatService.queryByPage(page,rows);
    }

    /**
     * 用于后台页面加载树节点
     *	url:'/rest/item/cat',
        method:'GET',
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<ItemCat> queryItemCatByParentId(@RequestParam(value = "id",defaultValue = "0")Long parentId){

        List<ItemCat> list = itemCatService.queryItemCatByParentId(parentId);
        return list;
    }
}
