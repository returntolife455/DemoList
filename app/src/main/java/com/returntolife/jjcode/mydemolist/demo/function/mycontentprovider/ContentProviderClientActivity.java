package com.returntolife.jjcode.mydemolist.demo.function.mycontentprovider;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.returntolife.jjcode.mydemolist.R;
import com.tools.jj.tools.utils.LogUtil;

/**
 * Created by HeJiaJun on 2019/2/20.
 * Email:1021661582@qq.com
 * des:
 * version: 1.0.0
 */

public class ContentProviderClientActivity extends Activity  implements View.OnClickListener{


    private Button btnGetData,btnSkip;
    private TextView tvTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contentproviderclient);

        btnGetData=findViewById(R.id.btn_getdata);
        btnSkip=findViewById(R.id.btn_skip);
        tvTest=findViewById(R.id.tv_test);

        btnGetData.setOnClickListener(this);
        btnSkip.setOnClickListener(this);
    }

    private void getData() {
        ContentResolver contentResolver=getContentResolver();

        Uri uri=Uri.parse("content://com.returntolife.myprovider/student");

        Cursor cursor=contentResolver.query(uri,new String[]{"_id,name"},null,null,null);
        if(cursor!=null){
            while(cursor.moveToNext()){
                LogUtil.d("id="+cursor.getInt(0)+"--name="+cursor.getString(1));
                tvTest.append("id="+cursor.getInt(0)+"--name="+cursor.getString(1)+"\n");
            }
            cursor.close();
        }

        Uri uriBook=Uri.parse("content://com.returntolife.myprovider/book");

        Cursor cursorBook=contentResolver.query(uriBook,new String[]{"_id,name"},null,null,null);
        if(cursorBook!=null){
            while(cursorBook.moveToNext()){
                LogUtil.d("id="+cursorBook.getInt(0)+"--name="+cursorBook.getString(1));
            }
            cursorBook.close();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_skip:
                Uri uri=Uri.parse("content://com.returntolife.myprovider/student");
                Intent intent = new Intent();
                intent.setAction("harvic.test.qijian");
                intent.setData(uri);
                try {
                    startActivity(intent);
                }catch (ActivityNotFoundException e){
                    LogUtil.e(e.toString());
                }

                break;
            case R.id.btn_getdata:
                getData();
                break;
            default:
                break;
        }
    }


}
