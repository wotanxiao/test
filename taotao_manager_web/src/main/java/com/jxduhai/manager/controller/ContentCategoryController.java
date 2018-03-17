package com.jxduhai.manager.controller;

import com.jxduhai.manager.pojo.ContentCategory;
import com.jxduhai.manager.service.ContentCategoryService;
import org.apache.log4j.Logger;
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
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    private Logger logger = Logger.getLogger(ContentCategoryController.class);

    /**
     * 	    url : '/rest/content/category',
     *      animate: true,
     *      method : "GET",
     */

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<ContentCategory> queryByContentCategoryParentId(@RequestParam(value = "id",defaultValue = "0")Long parentId){

        ContentCategory contentCategory = new ContentCategory();
        contentCategory.setParentId(parentId);

        List<ContentCategory> list = contentCategoryService.queryListByWhere(contentCategory);
        return list;
    }


    @ResponseBody()
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ContentCategory addContentCategory(ContentCategory contentCategory){

        ContentCategory contentCategory1 = null;
//        String msg = "0";
        try {
            contentCategory1 = contentCategoryService.addContentCategory(contentCategory);
        } catch (Exception e) {
//            msg = "1";
           logger.error("新增内容管理分类失败",e);
        }
        return contentCategory1;
    }


    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public String updateContentCategory(ContentCategory contentCategory){
        String msg = "0";
        try {
            contentCategoryService.updateByIdSelective(contentCategory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public String deleteContentCatgoryByIds(ContentCategory contentCategory){
//        String msg = "0";

        try {
            contentCategoryService.deleteContentCatgoryByIds(contentCategory);
            return "1";
        } catch (Exception e) {
            logger.error("删除商品分类失败",e);
//            e.printStackTrace();
        }

        return null;
    }
}
