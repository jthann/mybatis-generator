package com.jd.panda;

import com.jd.jdc.domain.User;
import com.jd.panda.dao.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by hanjuntao on 2016/4/19.
 */
public class UnitTest {

    static UserDao userDao;

    private static void init(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"spring-config.xml"});
        userDao = applicationContext.getBean(UserDao.class);
    }

    public static void main(String[] args) {
        init();
        test();
    }

    static void test(){
        testUpdate();
    }


    static void testUpdate(){
        User condition = new User();
        condition.setId(1);
        condition.setName("fdafdasfasdfasfs");
        userDao.update(condition);
    }

    static void testSelect(){
        User condition = new User();
        condition.setStartIndex(100);
        condition.setEndIndex(100+20);
        List<User> userList = userDao.selectList(condition);
        System.out.println(userList.size());
    }


    static void testInsert(){
        Random random = new Random();
        for(int i=0;i<100;i++) {
            User user = new User();
            user.setName(i+"");
            user.setAge(random.nextInt(100));
            user.setBirthday(new Date());
            user.setCreator("hanjuntao");
            userDao.insert(user);
        }
    }
}
