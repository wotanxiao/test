package com.jxduhai.manager.controller;

import com.jxduhai.common.TaoResult;
import com.jxduhai.manager.pojo.Content;
import com.jxduhai.manager.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/****
 *@author yxw
 *@date 2018/3/16
 */
@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * method:'get',pageSize:20,url:'/rest/content',queryParams:{categoryId:0}
     * 根据内容管理页面提供的请求查询数据
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String saveConteng(Content content){
        String msg = "0";
        try {
            contentService.saveSelective(content);
        } catch (Exception e) {
            msg = "1";
            e.printStackTrace();
        }
        return msg;
    }

    //method:'get',pageSize:20,url:'/rest/content',queryParams:{categoryId:0}
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public TaoResult<Content> queryPageByContentCategoryId(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                                      @RequestParam(value = "rows",defaultValue = "20")Integer rows,
                                                      Long categoryId){
        TaoResult<Content> taoResult = contentService.queryPageByContentCategoryId(page,rows,categoryId);
        return taoResult;
    }
}
