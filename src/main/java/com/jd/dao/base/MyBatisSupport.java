package com.jd.dao.base;

import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;
import java.util.Map;

/**
 * 对mybatis的支持<br/>
 * spring配置文件需定义sqlTemplate与batchSqlTemplate
 *
 * @author J-ONE
 * @since 2013-12-30
 */
abstract class MyBatisSupport {

    /**
     * SqlSessionTemplate,不同的数据源分开处理
     *
     * @param batch    是否批处理
     * @param readonly 是否只读
     * @return
     */
    protected abstract SqlSessionTemplate getSqlTemplate(boolean batch, boolean readonly);


    /**
     * 新增对象
     *
     * @param statement
     * @param parameter
     * @return
     */
    protected int insert(String statement, Object parameter) {
        int res = 0;
        try {
            if (parameter != null) {
                res = getSqlTemplate(false, false).insert(statement, parameter);
            }
        } catch (Exception ex) {
            throw new DaoExecException("Mybatis执行新增异常", ex);
        }
        return res;
    }

    /**
     * 删除对象
     *
     * @param statement
     * @param parameter
     * @return
     */
    protected int delete(String statement, Object parameter) {
        int res = 0;
        try {
            res = getSqlTemplate(false, false).delete(statement, parameter);
        } catch (Exception ex) {
            throw new DaoExecException("Mybatis执行删除异常", ex);
        }
        return res;
    }

    /**
     * 更新对象
     *
     * @param statement
     * @param parameter
     * @return
     */
    protected int update(String statement, Object parameter) {
        int res = 0;
        try {
            if (parameter != null) {
                res = getSqlTemplate(false, false).update(statement, parameter);
            }
        } catch (Exception ex) {
            throw new DaoExecException("Mybatis执行更新异常", ex);
        }
        return res;
    }

    /**
     * 查询一条记录
     *
     * @param <T>
     * @param statement
     * @param parameter
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <T> T select(String statement, Object parameter) {
        T obj = null;
        try {
            obj = (T) getSqlTemplate(false, true).selectOne(statement, parameter);
        } catch (Exception ex) {
            throw new DaoExecException("Mybatis执行单条查询异常", ex);
        }
        return obj;
    }

    /**
     * 查询列表
     *
     * @param <T>
     * @param statement
     * @param parameter
     * @return
     */
    protected <T> List<T> selectList(String statement, Object parameter) {
        List<T> list = null;
        try {
            list = getSqlTemplate(false, true).selectList(statement, parameter);
        } catch (Exception ex) {
            throw new DaoExecException("Mybatis执行列表查询异常", ex);
        }
        return list;
    }

    /**
     * 查询Map
     *
     * @param <K>
     * @param <V>
     * @param statement
     * @param parameter
     * @param mapKey
     * @return
     */
    protected <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
        Map<K, V> map = null;
        try {
            map = getSqlTemplate(false, true).selectMap(statement, parameter, mapKey);
        } catch (Exception ex) {
            throw new DaoExecException("Mybatis执行Map查询异常", ex);
        }
        return map;
    }
}