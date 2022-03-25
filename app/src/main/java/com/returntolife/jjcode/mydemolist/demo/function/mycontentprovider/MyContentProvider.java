package com.returntolife.jjcode.mydemolist.demo.function.mycontentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tools.jj.tools.utils.LogUtil;

/**
 * Created by HeJiaJun on 2019/2/20.
 * Email:1021661582@qq.com
 * des:
 * version: 1.0.0
 */

public class MyContentProvider extends ContentProvider {

    private Context mContext;

    private MyDbHelper mMyDbHelper;

    // 设置ContentProvider的唯一标识
    public static final String AUTOHORITY = "com.returntolife.myprovider";


    public static final int TABLE_STUDENT_CODE = 1;
    public static final int TABLE_BOOKE_CODE = 2;

    // UriMatcher类使用:在ContentProvider 中注册URI
    private static final UriMatcher mMatcher;

    SQLiteDatabase db = null;

    //重要的一步，将uri和provider绑定
    static {
        mMatcher=new UriMatcher(UriMatcher.NO_MATCH);

        mMatcher.addURI(AUTOHORITY,MyDbHelper.TABLE_SUTEMT, TABLE_STUDENT_CODE);
        mMatcher.addURI(AUTOHORITY, MyDbHelper.TABLE_BOOK, TABLE_BOOKE_CODE);

    }

    @Override
    public boolean onCreate() {
        mContext=getContext();

        mMyDbHelper=new MyDbHelper(mContext);
        db=mMyDbHelper.getWritableDatabase();
        // 初始化两个表的数据(先清空两个表,再各加入一个记录)
        db.execSQL("delete from student");
        db.execSQL("insert into student values(1,'张三');");
        db.execSQL("insert into student values(2,'李四');");

        db.execSQL("delete from book");
        db.execSQL("insert into book values(1,'Android');");
        db.execSQL("insert into book values(2,'iOS');");

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        String table=getTableName(uri);

        return db.query(table,null,null,null,null,null,null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        LogUtil.d("getType uri="+uri);

        return "vnd.android.cursor.dir/harvic.first";
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        String table=getTableName(uri);

        db.insert(table,null,values);
        // 当该URI的ContentProvider数据发生变化时，通知外界（即访问该ContentProvider数据的访问者）
        mContext.getContentResolver().notifyChange(uri, null);

        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }


    /**
     * 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
     */
    private String getTableName(Uri uri){
        String tableName = null;
        switch (mMatcher.match(uri)) {
            case TABLE_STUDENT_CODE:
                tableName = MyDbHelper.TABLE_SUTEMT;
                break;
            case TABLE_BOOKE_CODE:
                tableName = MyDbHelper.TABLE_BOOK;
                break;
            default:
                break;
        }
        return tableName;
    }
}
