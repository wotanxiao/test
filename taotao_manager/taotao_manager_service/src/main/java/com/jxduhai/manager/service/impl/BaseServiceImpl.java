package com.jxduhai.manager.service.impl;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.jxduhai.manager.pojo.BasePojo;
import com.jxduhai.manager.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/****
 *@author yxw
 *@date 2018/3/12
 */
public class BaseServiceImpl<T extends BasePojo> implements BaseService<T> {

    @Autowired
    private Mapper<T> mapper;

//    @Autowired
    private Class<T> clazz;

    public BaseServiceImpl(){


        // 获取父类的type
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        // 强转为ParameterizedType，可以使用获取泛型类型的方法
        ParameterizedType type = (ParameterizedType ) genericSuperclass;
        // 获取泛型的class
        clazz = (Class<T>) type.getActualTypeArguments()[0];
    }


    @Override
    public T queryById(long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> queryAll() {
        return mapper.select(null);
    }

    @Override
    public Integer queryCountByWhere(T t) {
        return mapper.selectCount(t);
    }

    @Override
    public List<T> queryListByWhere(T t) {
        return mapper.select(t);
    }

    @Override
    public List<T> queryByPage(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        return mapper.selectByExample(null);
    }

    @Override
    public T queryOne(T t) {
        return mapper.selectOne(t);
    }

    @Override
    public void save(T t) {
        //需要判断，如果调用者没有设置时间，则这里设置，如果设置了时间，则这里不设置了
        if (t.getCreated() == null){
            t.setCreated(new Date());
            t.setUpdated(t.getCreated());
        }else if (t.getUpdated() == null){
            t.setUpdated(t.getCreated());
        }
        mapper.insert(t);
    }

    @Override
    public void saveSelective(T t) {
        if (t.getCreated() == null){
            t.setCreated(new Date());
            t.setUpdated(t.getCreated());
        }else if (t.getUpdated() == null){
            t.setUpdated(t.getCreated());
        }
        mapper.insertSelective(t);
    }

    @Override
    public void updateById(T t) {
        t.setUpdated(new Date());
        mapper.updateByPrimaryKey(t);
    }

    @Override
    public void updateByIdSelective(T t) {
        t.setUpdated(new Date());
        mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public void deleteById(long id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(List<Object> ids) {
        Example example = new Example(clazz);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id",ids);
        mapper.deleteByExample(example);
    }


}
