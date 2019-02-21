package com.returntolife.jjcode.mydemolist.mycontentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.returntolife.jjcode.mydemolist.resumedownload.DBHelper;

/**
 * Created by HeJiaJun on 2019/2/20.
 * Email:1021661582@qq.com
 * des:
 * version: 1.0.0
 */

public class MyDbHelper extends SQLiteOpenHelper {


    private static final String DB_NAME="myprovider.db";

    public static final String TABLE_SUTEMT="student";
    public static final String TABLE_BOOK="book";



    private static final int VERSION=1;

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SUTEMT + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + " name TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_BOOK + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + " name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
