package com.returntolife.jjcode.mydemolist.editimage;

import android.app.Activity;
import android.graphics.Bitmap;
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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.returntolife.jjcode.mydemolist.R;
import com.tools.jj.tools.utils.Dp2PxUtil;
import com.tools.jj.tools.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HeJiaJun on 2018/9/9.
 * Email:1021661582@qq.com
 * des:
 * version: 1.0.0
 */

public class PickingPictureActivity extends Activity {


    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.btn_method1)
    Button btnMethod1;
    @BindView(R.id.btn_method2)
    Button btnMethod2;
    @BindView(R.id.iv_pick)
    ImageView ivPick;
    @BindView(R.id.tv_time)
    TextView tvTime;

    private boolean isMethod1=true;
    private long time;

    private int imageWidth;
    private int imageHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickpicture);
        ButterKnife.bind(this);

//        ivBg.post(new Runnable() {
//            @Override
//            public void run() {
//                pickColor();
//            }
//        });




    }

    private void pickColor() {
        Glide.with(this).asBitmap()
                .load(R.drawable.bg_invert_image)
                .into(new SimpleTarget<Bitmap>(500,500) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                if (palette == null) return;
                                if (palette.getDarkVibrantColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
                                    createLinearGradientBitmap(palette.getDarkVibrantColor(Color.TRANSPARENT), palette.getVibrantColor(Color.TRANSPARENT));
                                } else if (palette.getDarkMutedColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
                                    createLinearGradientBitmap(palette.getDarkMutedColor(Color.TRANSPARENT), palette.getMutedColor(Color.TRANSPARENT));
                                } else {
                                    createLinearGradientBitmap(palette.getLightMutedColor(Color.TRANSPARENT), palette.getLightVibrantColor(Color.TRANSPARENT));
                                }
                            }
                        });

                        imageWidth=resource.getWidth();
                        imageHeight=resource.getHeight();
                        LogUtil.d("resourcewith=" + resource.getWidth() + "--resourceheight=" + resource.getHeight());
                        time=System.currentTimeMillis();
                        ivPick.setImageBitmap(isMethod1?getImageToChange(resource):handleBigmap(resource));
                        time=System.currentTimeMillis()-time;

                        tvTime.setText("耗时:"+(time/1000f)+"S");

                    }
                });
    }




    //创建线性渐变背景色
    private void createLinearGradientBitmap(int darkColor, int color) {
        int bgColors[] = new int[2];
        bgColors[0] = darkColor;
        bgColors[1] = color;

        Bitmap bgBitmap = Bitmap.createBitmap(ivBg.getWidth(), ivBg.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas();
        Paint paint = new Paint();
        canvas.setBitmap(bgBitmap);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        LinearGradient gradient = new LinearGradient(0, 0, 0, bgBitmap.getHeight(), bgColors[0], bgColors[1], Shader.TileMode.CLAMP);
        paint.setShader(gradient);
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0, 0, bgBitmap.getWidth(), bgBitmap.getHeight());
        canvas.drawRoundRect(rectF, 20, 20, paint);
        canvas.drawRect(rectF, paint);
        ivBg.setImageBitmap(bgBitmap);
    }


    //修改透明度
    private Bitmap getImageToChange(Bitmap mBitmap) {
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

                float index = i * 1.0f / mHeight;
                if (index > 0.5f) {
                    float temp = i - mHeight / 2.0f;
                    a = 255 - (int) (i / (mHeight / 2.0f) * 255);
                }
                color = Color.argb(a, r, g, b);
                createBitmap.setPixel(j, i, color);
            }
        }
        return createBitmap;
    }


    private Bitmap handleBigmap(Bitmap localBitmap) {
        //透明渐变
        int[] argb = new int[localBitmap.getWidth() * localBitmap.getHeight()];
        localBitmap.getPixels(argb, 0, localBitmap.getWidth(), 0, 0, localBitmap.getWidth(), localBitmap.getHeight());

        //循环开始的下标，设置从什么时候开始改变
        int start = argb.length / 2;
        int mid = argb.length * 83 / 100;
        int lines = ((mid - start) / localBitmap.getHeight()) + 2;

        int width = localBitmap.getWidth();
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < width; j++) {
                int index = start - width + i * width + j;
                if (argb[index] != 0) {
                    argb[index] = ((int) ((1 - ((float) i / lines)) * 255) << 24) | (argb[index] & 0x00FFFFFF);
                }
            }
        }
        for (int i = mid; i < argb.length; i++) {
            argb[i] = (argb[i] & 0x00FFFFFF);
        }

        localBitmap = Bitmap.createBitmap(argb, localBitmap.getWidth(), localBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        return localBitmap;
    }

    @OnClick({R.id.btn_method1, R.id.btn_method2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_method1:
                isMethod1=true;
                pickColor();
                break;
            case R.id.btn_method2:
                isMethod1=false;
                pickColor();
                break;
        }
    }
}
