package com.returntolife.jjcode.mydemolist.demo.function.transition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.widget.Button;

import com.returntolife.jjcode.mydemolist.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ShareElementByFragmentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_element_fragment);

        ShareElement1Fragment fragment1 = ShareElement1Fragment.newInstance();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment, fragment1)
                .commit();


    }
}
