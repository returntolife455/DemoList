package com.returntolife.jjcode.mydemolist.editimage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.returntolife.jjcode.mydemolist.R;
import com.tools.jj.tools.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HeJiaJun on 2018/9/9.
 * Email:1021661582@qq.com
 * des:
 * version: 1.0.0
 */

public class PickingPictureActivity extends Activity {


    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.iv_main)
    ImageView ivMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickpicture);
        ButterKnife.bind(this);

        ivBg.post(new Runnable() {
            @Override
            public void run() {
                pickColor();
            }
        });

    }

    private void pickColor() {
        Glide.with(this).asBitmap()
                .load(R.drawable.bg_invert_image)
                .into(new SimpleTarget<Bitmap>(750,750) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                if(palette==null)return;
                                if (palette.getDarkVibrantColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
                                    createLinearGradientBitmap(palette.getDarkVibrantColor(Color.TRANSPARENT), palette.getVibrantColor(Color.TRANSPARENT));
                                } else if (palette.getDarkMutedColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
                                    createLinearGradientBitmap(palette.getDarkMutedColor(Color.TRANSPARENT), palette.getMutedColor(Color.TRANSPARENT));
                                } else {
                                    createLinearGradientBitmap(palette.getLightMutedColor(Color.TRANSPARENT), palette.getLightVibrantColor(Color.TRANSPARENT));
                                }
                            }
                        });
                        ivMain.setImageBitmap(getImageToChange(resource));
                    }
                });
    }


    //创建线性渐变背景色
    private void createLinearGradientBitmap(int darkColor,int color) {
        int bgColors[] = new int[2];
        bgColors[0] = darkColor;
        bgColors[1] = color;

        Bitmap  bgBitmap= Bitmap.createBitmap(ivBg.getWidth(),ivBg.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas  canvas=new Canvas();
        Paint   paint=new Paint();
        canvas.setBitmap(bgBitmap);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        LinearGradient gradient=new LinearGradient(0, 0, 0, bgBitmap.getHeight(),bgColors[0],bgColors[1], Shader.TileMode.CLAMP);
        paint.setShader(gradient);
        paint.setAntiAlias(true);
        RectF rectF=new RectF(0,0,bgBitmap.getWidth(),bgBitmap.getHeight());
        canvas.drawRoundRect(rectF,20,20,paint);
        canvas.drawRect(rectF,paint);
        ivBg.setImageBitmap(bgBitmap);
    }


    //修改透明度
    private static Bitmap getImageToChange(Bitmap mBitmap) {
        LogUtil.d("with="+mBitmap.getWidth()+"--height="+mBitmap.getHeight());
        Bitmap createBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_4444);
        int mWidth = mBitmap.getWidth();
        int mHeight = mBitmap.getHeight();
        for (int i = 0; i < mHeight; i++) {
            for (int j = 0; j < mWidth; j++) {
                int color = mBitmap.getPixel(j, i);
                int g = Color.green(color);
                int r = Color.red(color);
                int b = Color.blue(color);
                int a = Color.alpha(color);

                float index=i*1.0f/mHeight;
                if(index>0.5f ){
                    float temp=i-mHeight/2.0f;
                    a= 255-(int) (temp/375*255);
                }
                color = Color.argb(a, r, g, b);
                createBitmap.setPixel(j, i, color);
            }
        }
        return createBitmap;
    }
}
