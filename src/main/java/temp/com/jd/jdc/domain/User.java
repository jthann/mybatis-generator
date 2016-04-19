package com.jd.jdc.domain;
import java.util.Date;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;


/**
*
* User POJO
* @author HanJunTao
*
*/
public class User extends BaseVO{
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
