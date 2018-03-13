package com.jxduhai.manager.service;

import java.util.List;

/****
 *@author yxw
 *@date 2018/3/12
 */
public interface BaseService<T> {


    /**
     * 根据Id查询数据
     * @param id
     * @return
     */
    T queryById(long id);

    /**
     * 查询所有数据
     */
    List<T> queryAll();

    /**
     * 根据条件查询数量
     */
    Integer queryCountByWhere(T t);

    /**根据条件查询list集合
     * @param t
     * @return
     */
    List<T> queryListByWhere(T t);

    /**根据条件分页查询
     * @return
     */
    List<T> queryByPage(Integer page, Integer rows);

    /**根据条件查询1条数据
     * @param t
     * @return
     */
    T queryOne(T t);

    /**保存数据
     * @param t
     */
    void save(T t);

    /**根据Id保存修改
     * @param t
     */
    void updateById(T t);

    /**根据id删除数据
     * @param id
     */
    void deleteById(long id);

    /**根据集合批量删除数据
     * @param ids
     */
    void deleteByIds(List<Object> ids);

    /**
     * 忽略空参保存数据
     * @param t
     */
    void saveSelective(T t);

    /**
     * 忽略空参保存数据
     * @param t
     */
    void updateByIdSelective(T t);
}
