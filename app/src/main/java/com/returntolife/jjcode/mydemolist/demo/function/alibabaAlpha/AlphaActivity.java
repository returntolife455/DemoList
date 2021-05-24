package com.returntolife.jjcode.mydemolist.demo.function.alibabaAlpha;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.alpha.AlphaManager;
import com.alibaba.android.alpha.ITaskCreator;
import com.alibaba.android.alpha.Project;
import com.alibaba.android.alpha.Task;
import com.returntolife.jjcode.mydemolist.R;
import com.tools.jj.tools.utils.LogUtil;

/**
 * @Author : hejiajun
 * @Time : 2021/5/20
 * @Email : hejiajun@lizhi.fm
 * @Desc :
 */
public class AlphaActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailbaba_alpha);
        alibabaAlphaTask();

    }

    private void alibabaAlphaTask(){
        //构造方法，true为主线程执行
        Task task1=new MyTask("task1",false);
        Task task2=new MyTask("task2",false);
        Task task3=new MyTask("task3",false);
        Task task4=new MyTask("task4",false);
        Task task5=new MyTask("task5",false);
        Task task6=new MyTask("task6",false);

        //设置优先级，耗时操作优先级较高
//        task4.setExecutePriority(1);
//        task5.setExecutePriority(2);

        Project.Builder builder = new Project.Builder();

        builder.withTaskCreator(new MyCreator());
//        builder.add("task1");
//        builder.add("task2").after("task1");
//        builder.add("task3").after("task1");
//        builder.add("task4").after("task2","task3");
//        builder.add("task5").after("task2","task3");
//        builder.add("task6").after("task4","task5");

//
        builder.add(task1);
        builder.add(task2);
        builder.add(task3);
        builder.add(task4);
        builder.add(task5);
        builder.add(task6);
        builder.setProjectName("innerGroup");
        builder.create().start();

        AlphaManager.getInstance(this).addProject(builder.create());
        AlphaManager.getInstance(this).start();
        LogUtil.d("AlphaManager start after");
    }


    class MyCreator implements ITaskCreator {

        @Override
        public Task createTask(String s) {
            if ("task1".equals(s)) {
                return new MyTask("task1",false);
            } else if ("task2".equals(s)) {
                return new MyTask("task2",false);
            } else if ("task3".equals(s)) {
                return new MyTask("task3",false);
            } else if ("task4".equals(s)) {
                return new MyTask("task4",false);
            }else if ("task5".equals(s)) {
                return new MyTask("task5",false);
            }else if ("task6".equals(s)) {
                return new MyTask("task6",false);
            }else {
                return null;
            }
        }
    }
}
