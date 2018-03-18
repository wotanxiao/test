package com.jxduhai.portal.controller;

import com.jxduhai.manager.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/****
 *@author yxw
 *@date 2018/3/16
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @Value("${TAOTAO_AD_ID}")
    private Long TAOTAO_AD_ID;

    @Autowired
    private ContentService contentService;

    @RequestMapping(method = RequestMethod.GET)
    public String toIndex(Model model){
        //在用户访问首页的时候查询出首页数据,将数据存放进model中,携带回首页
        String json = contentService.queryContentByCategoryId(TAOTAO_AD_ID);
        model.addAttribute("data",json);
        return "index";
    }
}
