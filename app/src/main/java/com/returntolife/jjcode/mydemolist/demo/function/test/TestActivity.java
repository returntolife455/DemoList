package com.returntolife.jjcode.mydemolist.demo.function.test;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.returntolife.jjcode.mydemolist.R;
import com.tools.jj.tools.utils.LogUtil;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HeJiaJun on 2019/8/26.
 * Email:hejj@mama.cn
 * des:
 */
public class TestActivity extends Activity implements UserView, View.OnClickListener{

    UserPresenter userPresenter;

    Button btnClear,btnText;
    TextView tvContent;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        userPresenter = new UserPresenterImpl(new Retrofit.Builder().baseUrl("http://**.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(UserService.class),this);

        btnClear=findViewById(R.id.btn_clear);
        btnText=findViewById(R.id.btn_test);
        tvContent=findViewById(R.id.tv_content);

        btnClear.setOnClickListener(this);
        btnText.setOnClickListener(this);
    }

    @Override
    public void onUserLoaded(User user) {
        LogUtil.d("user ="+user.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_clear:
                tvContent.setText("");
                break;
            case R.id.btn_test:
                tvContent.setText("test");
                break;
        }
    }
}
