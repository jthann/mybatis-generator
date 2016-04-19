package com.jd.jdc.domain;

import com.jd.panda.domain.base.BaseVO;

import java.util.Date;


/**
 * User POJO
 *
 * @author HanJunTao
 */
public class User extends BaseVO {
    private String name;
    private Integer age;
    private Date birthday;


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


}
