package com.returntolife.jjcode.mydemolist.demo.function.test;

/**
 * Created by HeJiaJun on 2019/8/26.
 * Email:hejj@mama.cn
 * des:
 */
public class User {

    public int    uid;
    public String name;

    public User(){}

    public User(int uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
            "uid=" + uid +
            ", name='" + name + '\'' +
            '}';
    }
}
