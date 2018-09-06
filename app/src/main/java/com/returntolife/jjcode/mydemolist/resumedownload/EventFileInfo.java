package com.returntolife.jjcode.mydemolist.resumedownload;

/**
 * Created by HeJiaJun on 2018/9/6.
 * Email:1021661582@qq.com
 * des:
 * version: 1.0.0
 */

public class EventFileInfo {

    public static final int STATE_START=0;
    public static final int STATE_UPDATE_PROGRESS=1;
    public static final int STATE_FINISHED=2;


    public int state;
    public FileInfo fileInfo;


    public EventFileInfo(int state, FileInfo fileInfo) {
        this.state = state;
        this.fileInfo = fileInfo;
    }
}
