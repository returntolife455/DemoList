package com.returntolife.jjcode.mydemolist.demo.function.exceltest;

/**
 * Created by HeJiaJun on 2021/2/9. Email:hejj@mama.cn des:
 */
public class ProjectBean {
    private String name;
    private int age;
    private boolean boy;

    public ProjectBean(String name, int age, boolean boy) {
        this.name = name;
        this.age = age;
        this.boy = boy;
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

    public boolean isBoy() {
        return boy;
    }

    public void setBoy(boolean boy) {
        this.boy = boy;
    }
}
