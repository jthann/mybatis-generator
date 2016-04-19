package com.jd.panda.dao.base;

import java.io.Serializable;
import java.util.List;

public interface IDao<T, KEY extends Serializable> {

    /**
     * 添加对象
     *
     * @param t
     * @return 影响条数
     */
    int insert(T... t);

    /**
     * 添加对象并设置ID到对象上(需开启事务)
     *
     * @param t
     * @return 影响条数
     */
    int insertAndFetchId(T t);

    /**
     * 删除对象,主键
     *
     * @param key
     * @return 影响条数
     */
    int deleteByKey(KEY... key);

    /**
     * 删除对象,条件
     *
     * @param condtion
     * @return 影响条数
     */
    int deleteByKey(T condtion);

    /**
     * 更新对象,条件主键ID
     *
     * @param t
     * @return 影响条数
     */
    int updateByKey(T t);

    /**
     * 查询对象,条件主键
     *
     * @param key
     * @return
     */
    T select(KEY key);

    /**
     * 查询对象,条件主键数组
     *
     * @param key
     * @return
     */
    List<T> selectList(KEY... key);

    /**
     * 查询对象,只要不为NULL与空则为条件
     *
     * @param t
     * @return
     */
    List<T> selectList(T t);

    /**
     * 查询对象总数
     *
     * @param t
     * @return
     */
    Integer selectListCount(T t);
    
}