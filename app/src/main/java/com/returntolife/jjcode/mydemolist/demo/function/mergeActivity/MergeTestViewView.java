package com.returntolife.jjcode.mydemolist.demo.function.mergeActivity;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.returntolife.jjcode.mydemolist.R;

/**
 * @Author : hejiajun
 * @Time : 2021/5/24
 * @Email : hejiajun@lizhi.fm
 * @Desc :
 */
public class MergeTestViewView extends LinearLayout {

    public MergeTestViewView(Context context) {
        this(context, null);
    }

    public MergeTestViewView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MergeTestViewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.widget_merge_test,this);
    }
}
