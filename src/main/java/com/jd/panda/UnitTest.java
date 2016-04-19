package com.jd.panda;

import com.jd.panda.dao.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by hanjuntao on 2016/4/19.
 */
public class UnitTest {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"spring-config.xml"});

        UserDao userDao = applicationContext.getBean(UserDao.class);

        System.out.println(userDao);

    }
}
