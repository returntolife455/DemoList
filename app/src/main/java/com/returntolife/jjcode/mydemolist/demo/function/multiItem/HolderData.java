package com.returntolife.jjcode.mydemolist.demo.function.multiItem;

/**
 * Created by HeJiaJun on 2019/6/5.
 * Email:hejj@mama.cn
 * des:
 */
public class HolderData {

    private String name;
    private int type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public HolderData(){

    }

    public HolderData(String name, int type) {
        this.name = name;
        this.type = type;
    }
}
