package com.returntolife.jjcode.mydemolist.demo.function.transition;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.returntolife.jjcode.mydemolist.R;


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
