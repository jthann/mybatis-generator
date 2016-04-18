package com.jd.dao.impl;

import com.jd.dao.UserDao;
import com.jd.dao.base.BaseDao;
import com.jd.domain.User;
import org.springframework.stereotype.Component;

/**
 * Created by hanjuntao on 2016/4/18.
 */
@Component("userDao")
public class UserDaoImpl extends BaseDao<User, Integer> implements UserDao {

    private static final String NAMESPACE = "com.jd.jdc.dao.User";

    @Override
    public String getNameSpace(String statementId) {
        return NAMESPACE + "." + statementId;
    }

}
