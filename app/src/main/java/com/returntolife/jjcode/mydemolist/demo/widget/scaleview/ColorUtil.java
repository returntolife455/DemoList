package com.returntolife.jjcode.mydemolist.demo.widget.scaleview;

import android.graphics.Color;
import androidx.annotation.ColorInt;

public class ColorUtil {
    /**
     * 计算渐变颜色中间色值
     *
     * @param startColor 起始颜色
     * @param endColor   结束颜色
     * @param ratio      百分比，取值范围【0~1】
     * @return 颜色值
     */
    public static int getColor(@ColorInt int startColor, @ColorInt int endColor, float ratio) {
        int redStart = Color.red(startColor);
        int blueStart = Color.blue(startColor);
        int greenStart = Color.green(startColor);
        int redEnd = Color.red(endColor);
        int blueEnd = Color.blue(endColor);
        int greenEnd = Color.green(endColor);

        int red = (int) (redStart + (redEnd - redStart) * ratio + 0.5);
        int green = (int) (greenStart + (greenEnd - greenStart) * ratio + 0.5);
        int blue = (int) (blueStart + (blueEnd - blueStart) * ratio + 0.5);
        return Color.argb(255, red, green, blue);
    }
}
