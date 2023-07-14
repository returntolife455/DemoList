package com.returntolife.jjcode.mydemolist.demo.function.record;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.FrameLayout;

import com.returntolife.jjcode.mydemolist.R;
import com.tools.jj.tools.utils.LogUtil;

public class MyAccessibilityService2 extends AccessibilityService {
    FrameLayout mLayout;

    private boolean isAdd =false;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        LogUtil.d("onAccessibilityEvent eventType="+accessibilityEvent.getEventType());

//        if(accessibilityEvent.getEventType() == 32){
//            if(isAdd){
//                return;
//            }
//            isAdd=true;
//
//
//
//
//        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("onDestroy ");
    }

    @Override
    protected void onServiceConnected() {
        LogUtil.d("onServiceConnected ");
//
//        AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
//        accessibilityServiceInfo.eventTypes = 32;
//        accessibilityServiceInfo.feedbackType = 16;
//        accessibilityServiceInfo.notificationTimeout = 100;
//        if (Build.VERSION.SDK_INT >= 30) {
//            accessibilityServiceInfo.flags |= 64;
//        }
//        if (true) {
//            accessibilityServiceInfo.flags = 16 | accessibilityServiceInfo.flags;
//            accessibilityServiceInfo.eventTypes = 32 | 2048;
//        }
//        setServiceInfo(accessibilityServiceInfo);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
                mLayout = new FrameLayout(MyAccessibilityService2.this);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
                lp.format = PixelFormat.TRANSLUCENT;
                lp.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.TOP;
                LayoutInflater inflater = LayoutInflater.from(MyAccessibilityService2.this);
                inflater.inflate(R.layout.action_bar, mLayout);
                wm.addView(mLayout, lp);

                configureStopRecording();
            }
        },5000);

    }

    private void configureStartRecording() {
        Button startRecordingButton = mLayout.findViewById(R.id.btnStartRecording);
        startRecordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    AudioMediaRecord.INSTANCE.startRecord();
                }

            }
        });
    }

    private void configureStopRecording() {
        Button startRecordingButton = mLayout.findViewById(R.id.btnStartRecording);
        startRecordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    AudioMediaRecord.INSTANCE.startRecord();
                }

            }
        });

        Button button = mLayout.findViewById(R.id.btnStopRecording);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioMediaRecord.INSTANCE.stopRecord();
            }
        });

        Button btnPlay = mLayout.findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    AudioMediaRecord.INSTANCE.startPlaying();
                }

            }
        });

        Button btnStopPlay = mLayout.findViewById(R.id.btnStopPlay);
        btnStopPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    AudioMediaRecord.INSTANCE.stopPlaying();
                }

            }
        });
    }
}
