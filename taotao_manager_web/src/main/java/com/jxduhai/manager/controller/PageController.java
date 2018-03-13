package com.jxduhai.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/****
 *@author yxw
 *@date 2018/3/12
 */
@Controller
@RequestMapping("/page")
public class PageController {

    @RequestMapping("/{pageName}")
    public String toPage(@PathVariable String pageName){

        return pageName;
    }
}
