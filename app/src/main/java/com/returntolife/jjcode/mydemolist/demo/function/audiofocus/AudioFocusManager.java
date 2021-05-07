package com.returntolife.jjcode.mydemolist.demo.function.audiofocus;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Build;

/**
 * @Author : hejiajun
 * @Time : 2021/5/7
 * @Email : hejiajun@lizhi.fm
 * @Desc :
 */
public class AudioFocusManager implements AudioManager.OnAudioFocusChangeListener {
    private AudioManager mAudioManager;
    private AudioFocusRequest mFocusRequest;
    private AudioAttributes mAudioAttributes;

    private onRequestFocusResultListener mOnRequestFocusResultListener;
    private OnAudioFocusChangeListener mAudioFocusChangeListener;

    public AudioFocusManager(Context context) {
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    /**
     * Request audio focus.
     */
    public void requestFocus() {
        int result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (mFocusRequest == null) {
                if (mAudioAttributes == null) {
                    mAudioAttributes = new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build();
                }
                mFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                        .setAudioAttributes(mAudioAttributes)
                        .setWillPauseWhenDucked(true)
                        .setOnAudioFocusChangeListener(this)
                        .build();
            }
            result = mAudioManager.requestAudioFocus(mFocusRequest);
        } else {
            result = mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        }
        if (mOnRequestFocusResultListener != null) {
            mOnRequestFocusResultListener.onHandleResult(result);
        }
    }


    @Override
    public void onAudioFocusChange(int focusChange) {
        if (mAudioFocusChangeListener != null) {
            mAudioFocusChangeListener.onAudioFocusChange(focusChange);
        }
    }

    /**
     * Release audio focus.
     */
    public void releaseAudioFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mAudioManager.abandonAudioFocusRequest(mFocusRequest);
        } else {
            mAudioManager.abandonAudioFocus(this);
        }
    }

    /**
     * Handle the result of audio focus.
     */
    public interface onRequestFocusResultListener {
        void onHandleResult(int result);
    }


    public void setOnHandleResultListener(onRequestFocusResultListener listener) {
        mOnRequestFocusResultListener = listener;
    }


    /**
     * Same as AudioManager.OnAudioFocusChangeListener.
     */
    public interface OnAudioFocusChangeListener {
        void onAudioFocusChange(int focusChange);
    }


    public void setOnAudioFocusChangeListener(OnAudioFocusChangeListener listener) {
        mAudioFocusChangeListener = listener;
    }

}
