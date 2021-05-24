package com.returntolife.jjcode.mydemolist.demo.function.alibabaAlpha;

import com.alibaba.android.alpha.Task;
import com.tools.jj.tools.utils.LogUtil;

/**
 * @Author : hejiajun
 * @Time : 2021/5/20
 * @Email : hejiajun@lizhi.fm
 * @Desc :
 */
public class MyTask extends Task {
    public MyTask(String name) {
        super(name);
    }

    public MyTask(String name, int threadPriority) {
        super(name, threadPriority);
    }

    public MyTask(String name, boolean isInUiThread) {
        super(name, isInUiThread);
    }

    @Override
    public void run() {
        LogUtil.d("task name="+mName);
    }
}
