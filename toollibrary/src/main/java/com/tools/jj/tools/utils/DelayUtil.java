package com.tools.jj.tools.utils;

import java.util.Random;

/**
 * Create by JiaJunHe on 2020/4/19 15:10
 * Email 455hejiajun@gmail.com
 * Description:
 * Version: 1.0
 */
public class DelayUtil {

    /**
     *
     * @param paramInt1 延时范围
     * @param paramInt2 延时范围
     */
    public static void timeDelay(int paramInt1, int paramInt2)
    {
        paramInt1 = new Random().nextInt((paramInt2 - paramInt1) * 1000) + paramInt1 * 1000;
        long l = paramInt1;
        try
        {
            Thread.sleep(l);
            LogUtil.d("延时了几秒 ="+String.valueOf(paramInt1 / 1000));
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
    }
}
