package com.jxduhai.manager.service.impl;

import com.jxduhai.manager.mapper.ContentCategoryMapper;
import com.jxduhai.manager.pojo.ContentCategory;
import com.jxduhai.manager.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/****
 *@author yxw
 *@date 2018/3/16
 */
@Service
public class ContentCategoryServiceImpl extends BaseServiceImpl<ContentCategory> implements ContentCategoryService {

    @Autowired
    private ContentCategoryMapper contentCategoryMapper;

    @Override
    public ContentCategory addContentCategory(ContentCategory contentCategory) {

        contentCategory.setIsParent(false);
        contentCategory.setStatus(1);
//        contentCategoryMapper.insertSelective(contentCategory);
        super.save(contentCategory);
        ContentCategory contentCategory1 = new ContentCategory();
        contentCategory1.setId(contentCategory.getParentId());
        contentCategory1.setIsParent(true);

        super.updateByIdSelective(contentCategory1);

        return contentCategory;
    }

    /**
     * 删除节点,要递归找出所有的子节点,将其删除,还要判断父节点还有木有其他的子节点,根据条件是否设置为父节点
     * */
    @Override
    public void deleteContentCatgoryByIds(ContentCategory contentCategory) {

        //获取要删除的节点ID
//        Long id = contentCategory.getId();
        //定义个集合用于封装所有的子孙节点
        List<Object> list = new ArrayList<>();
        //根据ID查询出他的起来子节点
        list.add(contentCategory.getId());
        findContentCategoryByParentId(contentCategory, list);
        //删除所有的数据
        super.deleteByIds(list);

        //获取要删除的父节点ID
        Long parentId = contentCategory.getParentId();
        //根据父ID查询是否还有其他的字节点
        ContentCategory contentCategory1 = new ContentCategory();
        contentCategory1.setParentId(parentId);
//        List<ContentCategory> list1 = super.queryListByWhere(contentCategory1);
        Integer integer = super.queryCountByWhere(contentCategory1);
        if (integer == 0){
            ContentCategory contentCategory2 = new ContentCategory();
            contentCategory2.setId(parentId);
            contentCategory2.setIsParent(false);
            super.updateByIdSelective(contentCategory2);
        }
    }


    /**
     * 抽取的方法,由于根据父ID递归查询出所有的数据,并将数据添加到集合中
     */
    private void findContentCategoryByParentId(ContentCategory contentCategory, List<Object> list){
        ContentCategory contentCategory1 = new ContentCategory();
        contentCategory1.setParentId(contentCategory.getId());
        List<ContentCategory> list1 = super.queryListByWhere(contentCategory1);
        if (list1 != null && list1.size() > 0) {
            for (ContentCategory category : list1) {
                list.add(category.getId());
                findContentCategoryByParentId(category,list);
            }
        }
    }
}
