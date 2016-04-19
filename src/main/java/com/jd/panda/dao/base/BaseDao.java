package com.jd.panda.dao.base;

import org.mybatis.spring.SqlSessionTemplate;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * Created by hanjuntao on 2016/4/18.
 */
public abstract class BaseDao<T, KEY extends Serializable> extends MyBatisSupport implements IDao<T, KEY> {


    private static final String DEFAULT_INSERT_KEY = "INSERT";
    private static final String DEFAULT_UPDATE_KEY = "UPDATE";
    private static final String DEFAULT_INSERT_LAST_SEQUENCE_KEY = "lastSequence";
    private static final String DEFAULT_DELETE_ARRAY_KEY = "deleteByArrayKey";
    private static final String DEFAULT_DELETE_CONDITION = "deleteByCondition";
    private static final String DEFAULT_SELECT_ARRAY_KEY = "selectEntryArray";
    private static final String DEFAULT_SELECT_CONDITION = "selectEntryList";
    private static final String DEFAULT_SELECT_CONDITION_COUNT = "selectEntryListCount";

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


    public int insertEntry(T... t) {
        int result = 0;
        if (t == null || t.length <= 0) {
            return result;
        }
        for (T o : t) {
            if (o != null) {
                result += this.insert(getNameSpace(DEFAULT_INSERT_KEY), o);
            }
        }
        return result;
    }

    public int insertEntryCreateId(T t) {
        @SuppressWarnings("unchecked")
        int result = this.insertEntry(t);
        if (result > 0) {
            Integer id = select(getNameSpace(DEFAULT_INSERT_LAST_SEQUENCE_KEY), null);
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

    public int deleteByKey(KEY... key) {
        return this.delete(getNameSpace(DEFAULT_DELETE_ARRAY_KEY), key);
    }

    public int deleteByKey(T t) {
        return this.delete(getNameSpace(DEFAULT_DELETE_CONDITION), t);
    }

    public int updateByKey(T t) {
        return this.update(getNameSpace(DEFAULT_UPDATE_KEY), t);
    }

    public T selectEntry(KEY key) {
        @SuppressWarnings("unchecked")
        List<T> list = this.selectEntryList(key);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public List<T> selectEntryList(KEY... key) {
        if (key == null || key.length <= 0) {
            return null;
        }
        return this.selectList(getNameSpace(DEFAULT_SELECT_ARRAY_KEY), key);
    }

    public List<T> selectEntryList(T t) {
        return this.selectList(getNameSpace(DEFAULT_SELECT_CONDITION), t);
    }

    public Integer selectEntryListCount(T t) {
        return this.select(getNameSpace(DEFAULT_SELECT_CONDITION_COUNT), t);
    }
}
