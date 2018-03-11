package com.jxduhai.manager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/****
 *@author yxw
 *@date 2018/3/9
 */

@RestController
public class tEST {

    @RequestMapping("tttt")
    public String ddd(){


        return "ddd";

    }
}
