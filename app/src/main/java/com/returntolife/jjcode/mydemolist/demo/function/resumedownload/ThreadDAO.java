package com.returntolife.jjcode.mydemolist.demo.function.resumedownload;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 455 on 2017/8/7.
 */

public class ThreadDAO {

    private DBHelper dbHelper;



    public ThreadDAO(Context context){
        this.dbHelper=DBHelper.getDbHelper(context,1);
    }

    public synchronized void insertThread(ThreadInfo threadInfo){

        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("thread_id",threadInfo.getId());
        values.put("url", threadInfo.getUrl());
        values.put("begin", threadInfo.getBegin());
        values.put("end", threadInfo.getEnd());
        values.put("finished", threadInfo.getFinished());

        db.insert("thread_info", null, values);

        db.close();
    }

    public  synchronized  void deleteThread(String url){

        SQLiteDatabase db=dbHelper.getWritableDatabase();

        db.delete("thread_info","url = ?" ,new String[]{url});

        db.close();
    }

    public synchronized void updateThread(String url, int thread_id, int finished){

        SQLiteDatabase db=dbHelper.getWritableDatabase();

        db.execSQL("update thread_info set finished =? where url = ? and thread_id = ?",
                new Object[]{finished,url,thread_id});

        db.close();
    }

    public List<ThreadInfo> queryThread(String url){

        List<ThreadInfo> list=new ArrayList<>();

        SQLiteDatabase db=dbHelper.getReadableDatabase();

        Cursor cursor=db.query("thread_info",null,"url= ? ",new String[]{url},null,null,null);

        while(cursor.moveToNext()){
            ThreadInfo threadInfo=new ThreadInfo();
            threadInfo.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            threadInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            threadInfo.setBegin(cursor.getInt(cursor.getColumnIndex("begin")));
            threadInfo.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            threadInfo.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            list.add(threadInfo);
        }

        cursor.close();
        db.close();

        return  list;
    }

    public List<ThreadInfo> queryThread(){

        List<ThreadInfo> list=new ArrayList<>();

        SQLiteDatabase db=dbHelper.getReadableDatabase();

        Cursor cursor=db.query("thread_info",null,null,null,null,null,null);

        while(cursor.moveToNext()){
            ThreadInfo threadInfo=new ThreadInfo();
            threadInfo.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            threadInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            threadInfo.setBegin(cursor.getInt(cursor.getColumnIndex("begin")));
            threadInfo.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            threadInfo.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            list.add(threadInfo);
        }

        cursor.close();
        db.close();

        return  list;
    }



    public boolean isExists(String url){


        SQLiteDatabase db=dbHelper.getWritableDatabase();

        Cursor cursor=db.query("thread_info",null,"url= ? ",new String[]{url},null,null,null);

        boolean isExists=cursor.moveToNext();

        cursor.close();
        db.close();

        return isExists;

    }

}
