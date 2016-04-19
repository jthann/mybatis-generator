package com.jd.panda;

import com.jd.jdc.domain.User;
import com.jd.panda.dao.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.Random;

/**
 * Created by hanjuntao on 2016/4/19.
 */
public class UnitTest {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"spring-config.xml"});

        UserDao userDao = applicationContext.getBean(UserDao.class);
        Random random = new Random();
        System.out.println(userDao);
        for(int i=0;i<100;i++) {
            User user = new User();
            user.setName(i+"");
            user.setAge(random.nextInt(100));
            user.setBirthday(new Date());
            user.setCreator("hanjuntao");
            user.setCreator("hjt");
            userDao.insertEntry(user);
        }
    }
}
