package com.jd.panda.dao.base;

import org.mybatis.spring.SqlSessionTemplate;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * Created by hanjuntao on 2016/4/18.
 */
public abstract class BaseDao<T, KEY extends Serializable> extends MyBatisSupport implements IDao<T, KEY> {


    private static final String INSERT_KEY = "INSERT";
    private static final String LAST_SEQUENCE_KEY = "LAST-SEQUENCE-ID";
    private static final String DELETE_BY_CONDITION_KEY = "DELETE";
    private static final String UPDATE_KEY = "UPDATE";
    private static final String SELECT_KEY = "SELECT";
    private static final String SELECT_LIST_BY_CONDITION = "SELECT-LIST";
    private static final String SELECT_LIST_COUNT_BY_CONDITION = "COUNT";

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;
    @Resource
    private SqlSessionTemplate batchSqlSessionTemplate;

    protected SqlSessionTemplate getSqlTemplate(boolean batch, boolean readonly) {
        if (batch) {
            return batchSqlSessionTemplate;
        }
        return sqlSessionTemplate;
    }

    public abstract String getNameSpace(String statementId);


    public int insert(T... t) {
        int result = 0;
        if (t == null || t.length <= 0) {
            return result;
        }
        for (T o : t) {
            if (o != null) {
                result += this.insert(getNameSpace(INSERT_KEY), o);
            }
        }
        return result;
    }

    public int insertAndFetchId(T t) {
        @SuppressWarnings("unchecked")
        int result = this.insert(t);
        if (result > 0) {
            Integer id = select(getNameSpace(LAST_SEQUENCE_KEY), null);
            if (id != null && id > 0) {
                try {
                    Class<?> clz = t.getClass();
                    clz.getMethod("setId", Integer.class).invoke(t, id);//最后一次插入编号
                } catch (Exception e) {
                    throw new DaoExecException("设置新增主键失败", e);
                }
            }
        }
        return result;
    }


    public int delete(T t) {
        return this.delete(getNameSpace(DELETE_BY_CONDITION_KEY), t);
    }

    public int update(T t) {
        return this.update(getNameSpace(UPDATE_KEY), t);
    }

    public T select(KEY key) {
        @SuppressWarnings("unchecked")
        T t = this.getSqlTemplate(false, true).selectOne(getNameSpace(SELECT_KEY), key);
        return t;
    }

    public List<T> selectList(T t) {
        return this.selectList(getNameSpace(SELECT_LIST_BY_CONDITION), t);
    }

    public Integer selectCount(T t) {
        return this.select(getNameSpace(SELECT_LIST_COUNT_BY_CONDITION), t);
    }
}
