package com.jd.panda.dao.impl;

import com.jd.jdc.domain.User;
import com.jd.panda.dao.UserDao;
import com.jd.panda.dao.base.BaseDao;
import org.springframework.stereotype.Component;

/**
 * Created by hanjuntao on 2016/4/18.
 */
@Component("userDao")
public class UserDaoImpl extends BaseDao<User, Integer> implements UserDao {

    private static final String NAMESPACE = "User";

    @Override
    public String getNameSpace(String statementId) {
        return NAMESPACE + "." + statementId;
    }

}
