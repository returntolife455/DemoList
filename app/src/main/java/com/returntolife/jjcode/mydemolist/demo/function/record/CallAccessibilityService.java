package com.returntolife.jjcode.mydemolist.demo.function.record;

import static android.media.MediaRecorder.AudioSource.VOICE_RECOGNITION;

import android.accessibilityservice.AccessibilityService;
import android.graphics.PixelFormat;
import android.media.MediaDataSource;
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

public class CallAccessibilityService extends AccessibilityService {
    FrameLayout mLayout;



    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        LogUtil.d("onAccessibilityEvent eventType="+accessibilityEvent.getEventType());

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


        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            mLayout = new FrameLayout(CallAccessibilityService.this);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
            lp.format = PixelFormat.TRANSLUCENT;
            lp.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.TOP;
            LayoutInflater inflater = LayoutInflater.from(CallAccessibilityService.this);
            inflater.inflate(R.layout.action_bar, mLayout);
            wm.addView(mLayout, lp);

            configure();

        },0);

    }



    private void configure() {
        Button startRecordingButton = mLayout.findViewById(R.id.btnStartRecording);
        startRecordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                    AudioMediaRecord.INSTANCE.startRecord();
//                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    AudioRecordManager.INSTANCE.initAudioRecord(0,null, VOICE_RECOGNITION);
                }
                AudioRecordManager.INSTANCE.startRecord();
            }
        });

        Button button = mLayout.findViewById(R.id.btnStopRecording);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AudioMediaRecord.INSTANCE.stopRecord();
                AudioRecordManager.INSTANCE.stopRecord();
            }
        });

        Button btnPlay = mLayout.findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                    AudioMediaRecord.INSTANCE.startPlaying();
//                }
                AudioRecordManager.INSTANCE.startPlaying();
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
