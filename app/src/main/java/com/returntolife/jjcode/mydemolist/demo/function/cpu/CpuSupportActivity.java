package com.returntolife.jjcode.mydemolist.demo.function.cpu;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.returntolife.jjcode.mydemolist.R;

import org.w3c.dom.Text;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author : hejiajun
 * @Time : 2021/4/25
 * @Email : hejiajun@lizhi.fm
 * @Desc :
 */
public class CpuSupportActivity extends Activity {

    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu_support);

        textView=findViewById(R.id.tv_cpu);
        getCPUABI();
    }


    /**
     * @date 创建时间:2020/10/12 0012
     * @auther gaoxiaoxiong
     * @Descriptiion 获取CPU支持的架构
     **/
    public void getCPUABI() {
        final String[] supportAbisArray = getStringList("ro.product.cpu.abilist", ",");
        if (supportAbisArray != null && supportAbisArray.length > 0) {
            StringBuilder stringBuilder=new StringBuilder();
            for (String s : supportAbisArray) {
                stringBuilder.append("支持的架构有=" + s);
                stringBuilder.append("\n");
            }
            textView.setText(stringBuilder.toString());
        }
    }


    private String[] getStringList(String property, String separator) {
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, property);
            return navBarOverride.isEmpty() ? new String[0] : navBarOverride.split(separator);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
