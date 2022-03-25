package com.returntolife.jjcode.mydemolist.demo.function.databinding;

import android.app.Activity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.returntolife.jjcode.mydemolist.R;
import com.returntolife.jjcode.mydemolist.databinding.ActivityDatabindingBinding;

/**
 * Created by HeJiaJun on 2020/8/4. Email:hejj@mama.cn des:
 */
public class DataBindingActivity extends Activity {

    private UserBean mUserInfo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDatabindingBinding dataBinding=DataBindingUtil.setContentView(this,R.layout.activity_databinding);

        mUserInfo=new UserBean();
        mUserInfo.setAge(18);
        mUserInfo.setName("hjj");

        dataBinding.setUserInfo(mUserInfo);
    }
}
