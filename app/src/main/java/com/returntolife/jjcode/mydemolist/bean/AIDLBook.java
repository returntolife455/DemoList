package com.returntolife.jjcode.mydemolist.bean;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * Create by JiaJun He on 2019/6/1$
 * Email:1021661582@qq.com
 * des:
 * version:1.0.0
 */
public class AIDLBook implements Parcelable {

    public String name;
    public int id;

    public AIDLBook(){

    }

    public AIDLBook(String name, int id) {
        this.name = name;
        this.id = id;
    }

    protected AIDLBook(Parcel in) {
        name = in.readString();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
    }

    public void readFromParcel(Parcel in){
        name = in.readString();
        id = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AIDLBook> CREATOR = new Creator<AIDLBook>() {
        @Override
        public AIDLBook createFromParcel(Parcel in) {
            return new AIDLBook(in);
        }

        @Override
        public AIDLBook[] newArray(int size) {
            return new AIDLBook[size];
        }
    };

    @Override
    public String toString() {
        return "AIDLBook{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
