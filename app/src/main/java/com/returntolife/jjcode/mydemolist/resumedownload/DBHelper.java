package com.returntolife.jjcode.mydemolist.resumedownload;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 455 on 2017/8/7.
 */

public  class  DBHelper extends SQLiteOpenHelper {

    private static DBHelper dbHelper=null;

    private static final String DB_NAME="resumedownload.db";

    private static final String CREATE_TABLE="create table thread_info(_id integer primary key autoincrement, " +
            "thread_id integer, url text, begin integer, end integer, finished integer)";

    private static final String DELETE_TABLE="drop table if exists thread_info";


    private DBHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    public static DBHelper getDbHelper(Context context, int version){
        if(dbHelper==null){
            dbHelper=new DBHelper(context,version);
        }

        return dbHelper;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DELETE_TABLE);
        db.execSQL(CREATE_TABLE);

    }
}
