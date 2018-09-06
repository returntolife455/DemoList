package com.returntolife.jjcode.mydemolist.resumedownload;

/**
 * Created by 455 on 2017/8/7.
 */

public class ThreadInfo {

    private int id;
    private String url;
    private int begin;
    private int end;
    private int finished;

    public ThreadInfo(){

    }

    public ThreadInfo(int id, String url, int begin, int end, int finished) {
        this.id = id;
        this.url = url;
        this.begin = begin;
        this.end = end;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }
}
