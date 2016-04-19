package com.jd.domain;

import com.jd.domain.base.BaseVO;

import java.util.Date;

/**
 * Created by hanjuntao on 2016/4/18.
 */
public class User extends BaseVO {

    private String name;
    private String userPin;
    private Date birthDay;
    private Integer age;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserPin() {
        return userPin;
    }

    public void setUserPin(String userPin) {
        this.userPin = userPin;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
