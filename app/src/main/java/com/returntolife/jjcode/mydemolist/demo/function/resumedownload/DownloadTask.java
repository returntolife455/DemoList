package com.returntolife.jjcode.mydemolist.demo.function.resumedownload;

import android.content.Context;

import com.tools.jj.tools.http.RxBus2;
import com.tools.jj.tools.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 455 on 2017/8/7.
 */

public class DownloadTask {

    private ThreadDAO threadDAO;
    private Context context;
    private FileInfo fileInfo;

    public static ExecutorService sExecutorService = Executors.newCachedThreadPool();

    private int current_finished=0;
    public   boolean isPause=false;

    private List<DownloadRunnable> mThreadlist = null;

    public DownloadTask(Context context, FileInfo fileInfo) {
        this.threadDAO = new ThreadDAO(context);
        this.context = context;
        this.fileInfo = fileInfo;
    }


    public void download(){
        List<ThreadInfo> list = threadDAO.queryThread(fileInfo.getUrl());

        if(list.size()==0){
            LogUtil.d("没有历史下载数据");
            int length = fileInfo.getLength();
            int block = length / 3;
            for (int i = 0; i < 3; i++) {
                // 划分每个线程开始下载和结束下载的位置
                int start = i * block;
                int end = (i + 1) * block - 1;
                if (i == 3 - 1) {
                    end = length - 1;
                }

                ThreadInfo threadInfo = new ThreadInfo(i, fileInfo.getUrl(), start, end, 0);
                list.add(threadInfo);

                threadDAO.insertThread(threadInfo);
            }
        }else {
            LogUtil.d("有历史下载数据");
        }
        mThreadlist = new ArrayList<>();
        for(ThreadInfo info:list){

            DownloadRunnable thread = new DownloadRunnable(info);
//			thread.start();
            // 使用线程池执行下载任务
            DownloadTask.sExecutorService.execute(thread);
            mThreadlist.add(thread);
            // 如果數據庫不存在下載信息，添加下載信息
          //  mDao.insertThread(info);
        }

    }

    private synchronized void checkAllFinished(){

        boolean allFinished=true;

        for(DownloadRunnable temp: mThreadlist){
            if(!temp.isFinished){
                allFinished = false;
                break;
            }
        }

        if(allFinished){
            threadDAO.deleteThread(fileInfo.getUrl());
            // 通知UI哪个线程完成下载
            RxBus2.getInstance().post(new EventFileInfo(EventFileInfo.STATE_FINISHED,fileInfo));
            LogUtil.d("下载完成");

        }
    }


    public void delete(){
        isPause=true;
        threadDAO.deleteThread(fileInfo.getUrl());
    }


    private  class DownloadRunnable implements Runnable {

        ThreadInfo threadInfo;

        public  boolean isFinished=false;

        public DownloadRunnable(ThreadInfo threadInfo) {
            this.threadInfo = threadInfo;
        }

        @Override
        public void run() {

            HttpURLConnection conn=null;
            InputStream is = null;
            RandomAccessFile raf=null;

            try {

                URL url=new URL(threadInfo.getUrl());

                conn=(HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.setReadTimeout(3000);

                int start=threadInfo.getBegin()+threadInfo.getFinished();
                conn.setRequestProperty("Range","bytes= "+start +"-"+threadInfo.getEnd());


                current_finished+=threadInfo.getFinished();



                conn.connect();
                if(!(conn.getResponseCode()== HttpURLConnection.HTTP_PARTIAL||conn.getResponseCode()== HttpURLConnection.HTTP_OK)){
                    return;
                }

                File file=new File(ResumeDownloadService.DownloadPath,fileInfo.getFileName());
                raf=new RandomAccessFile(file,"rw");
                raf.seek(start);

                is=conn.getInputStream();
                byte[] buf=new byte[1024*1024];
                int len;

                long time= System.currentTimeMillis();
                while((len=is.read(buf))!=-1){
                    raf.write(buf,0,len);
                    //累加总的进度
                    current_finished+=len;
                    //每个线程进度
                    threadInfo.setFinished(threadInfo.getFinished()+len);


                    if(isPause){
                        threadDAO.updateThread(threadInfo.getUrl(),threadInfo.getId(),threadInfo.getFinished());
                        return;
                    }

                    if((System.currentTimeMillis()-time)>1500) {
                        time= System.currentTimeMillis();
                        fileInfo.setFinished(current_finished);
                        RxBus2.getInstance().post(new EventFileInfo(EventFileInfo.STATE_UPDATE_PROGRESS,fileInfo));
                        threadDAO.updateThread(threadInfo.getUrl(),threadInfo.getId(),threadInfo.getFinished());
                    }

                }

                isFinished=true;
                checkAllFinished();


            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (conn != null) {
                    conn.disconnect();
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (raf != null) {
                        raf.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
