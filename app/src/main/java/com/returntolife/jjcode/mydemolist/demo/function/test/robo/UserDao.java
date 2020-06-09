package com.returntolife.jjcode.mydemolist.demo.function.test.robo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.returntolife.jjcode.mydemolist.AppApplication;
import com.returntolife.jjcode.mydemolist.SimpleTinkerInApplicationLike;
import com.returntolife.jjcode.mydemolist.demo.function.test.User;

/**
 * Created by HeJiaJun on 2019/8/26.
 * Email:hejj@mama.cn
 * des:
 */
public class UserDao {
    static boolean isTableExist;

    SQLiteDatabase db;

    public UserDao(SQLiteDatabase db){
        this.db=db;
    }

    public UserDao() {
        this.db = new DbHelper(SimpleTinkerInApplicationLike.pAppContext, "User").getWritableDatabase();
    }

    /**
     * 插入Bean
     */
    public void insert(User user) {
        checkTable();

        ContentValues values = new ContentValues();
        values.put("id", user.uid);
        values.put("name",user.name);

        db.insert("User", "", values);
    }

    /**
     * 获取对应id的Bean
     */
    public User get(int id) {
        checkTable();

        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM User", null);

            if (cursor != null && cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));

                return new User(id, name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            cursor = null;
        }
        return null;
    }

    /**
     * 检查表是否存在，不存在则创建表
     */
    private void checkTable() {
        if (!isTableExist()) {
            db.execSQL("CREATE TABLE IF NOT EXISTS User ( id INTEGER PRIMARY KEY, name )");
        }
    }

    private boolean isTableExist() {
        if (isTableExist) {
            return true; // 上次操作已确定表已存在于数据库，直接返回true
        }

        Cursor cursor = null;
        try {
            String sql = "SELECT COUNT(*) AS c FROM sqlite_master WHERE type ='table' AND name ='User' ";

            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    isTableExist = true; // 记录Table已创建，下次执行isTableExist()时，直接返回true
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            cursor = null;
        }
        return false;
    }
}
