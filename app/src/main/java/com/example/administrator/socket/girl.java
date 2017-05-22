package com.example.administrator.socket;

/**
 * Created by Administrator on 2017/5/5 0005.
 */

public class girl {
    private String name;
    private int age;
    private String school;


    @Override
    public String toString() {
        return "girl{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", school='" + school + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

}
