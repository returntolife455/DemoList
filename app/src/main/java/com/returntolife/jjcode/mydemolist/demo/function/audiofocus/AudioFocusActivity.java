package com.returntolife.jjcode.mydemolist.demo.function.audiofocus;

import android.Manifest;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.returntolife.jjcode.mydemolist.R;
import com.tbruyelle.rxpermissions2.RxPermissions;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @Author : hejiajun
 * @Time : 2021/5/7
 * @Email : hejiajun@lizhi.fm
 * @Desc :
 */
public class AudioFocusActivity extends Activity {

    private final String TAG = AudioFocusActivity.this.getClass().getSimpleName();


    private MediaPlayer mMediaPlayer;
    private Button btnPlay;

    private AudioFocusManager audioFocusManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_focus);

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull Boolean aBoolean) {
                        if (aBoolean) {
                            initData();
                            initView();
                        }else {
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        finish();
                    }

                    @Override
                    public void onComplete() {
                    }
                });

      ;
    }


    private void initData() {
        //初始化AudioManager对象
        audioFocusManager=new AudioFocusManager(this);

        audioFocusManager.setOnHandleResultListener(new AudioFocusManager.onRequestFocusResultListener() {
            @Override
            public void onHandleResult(int result) {
                if (result == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {

                } else if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    AssetFileDescriptor fileDescriptor;
                    try {
                        //获取音频文件
                        fileDescriptor = AudioFocusActivity.this.getAssets().openFd("test.mp3");
                        //实例化MediaPlayer对象
                        mMediaPlayer = new MediaPlayer();
                        //设置播放流类型
                        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        //设置播放源，有多个参数可以选择，具体参考相关文档，本文旨在介绍音频焦点
                        mMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
                                fileDescriptor.getStartOffset(),
                                fileDescriptor.getLength());
                        //设置循环播放
                        mMediaPlayer.setLooping(true);
                        //准备监听
                        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                //准备完成后自动播放
                                start();
                            }
                        });
                        //异步准备
                        mMediaPlayer.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (result == AudioManager.AUDIOFOCUS_REQUEST_DELAYED) {

                }
            }
        });

        audioFocusManager.setOnAudioFocusChangeListener(new AudioFocusManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange){
                    case AudioManager.AUDIOFOCUS_NONE:
                        Log.d(TAG, "AUDIOFOCUS_NONE");
                        break;
                    case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE:
                        Log.d(TAG, "AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE");
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                        //长时间丢失焦点,当其他应用申请的焦点为AUDIOFOCUS_GAIN时，
                        //会触发此回调事件，例如播放QQ音乐，网易云音乐等
                        //通常需要暂停音乐播放，若没有暂停播放就会出现和其他音乐同时输出声音
                        Log.d(TAG, "AUDIOFOCUS_LOSS");
                        stop();
                        //释放焦点，该方法可根据需要来决定是否调用
                        //若焦点释放掉之后，将不会再自动获得
//                    mAudioManager.abandonAudioFocus(mAudioFocusChange);
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        //短暂性丢失焦点
                        //会触发此回调事件，例如播放短视频，拨打电话等。
                        //通常需要暂停音乐播放
                        stop();
                        Log.d(TAG, "AUDIOFOCUS_LOSS_TRANSIENT");
                        break;
                    case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                        stop();
                        Log.d(TAG, "AUDIOFOCUS_GAIN_TRANSIENT");
                        break;
                    case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
                        stop();
                        Log.d(TAG, "AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK");
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        //短暂性丢失焦点并作降音处理
                        Log.d(TAG, "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
                        break;
                    case AudioManager.AUDIOFOCUS_GAIN:
                        //当其他应用申请焦点之后又释放焦点会触发此回调
                        //可重新播放音乐
                        Log.d(TAG, "AUDIOFOCUS_GAIN");
                        start();
                        break;
                    default:
                        Log.d(TAG, "default focusChange="+focusChange);
                        break;
                }
            }
        });


        audioFocusManager.requestFocus();


    }

    private void initView() {
        btnPlay = findViewById(R.id.btn_play);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaPlayer != null){
                    if (mMediaPlayer.isPlaying()){
                        stop();
                    } else {
                        start();
                    }
                }
            }
        });
    }

    private void start() {
        btnPlay.setText("Stop");
        mMediaPlayer.start();
    }

    private void stop() {
        btnPlay.setText("Start");
        mMediaPlayer.pause();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mMediaPlayer!=null){
            mMediaPlayer.release();
        }

        audioFocusManager.releaseAudioFocus();
    }

}
