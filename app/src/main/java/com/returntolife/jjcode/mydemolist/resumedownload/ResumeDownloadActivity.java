package com.returntolife.jjcode.mydemolist.resumedownload;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.Toast;

import com.returntolife.jjcode.mydemolist.MainActivity;
import com.returntolife.jjcode.mydemolist.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HeJiaJun on 2018/8/28.
 * des:
 * version:1.0.0
 */

public class ResumeDownloadActivity extends Activity {

    @BindView(R.id.list_view)
    ListView listView;

    private String urlone = "http://f12.baidu.com/it/u=1812393756,3601871637&fm=72";

    //private String urlone = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502039037885&di=3b76f156e639418d448822c1533c2010&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F5%2F57b6cfcd3f313.jpg";
    private String urltwo = "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1210/08/c1/14307187_1349676294934.jpg";
    private String urlThree = "http://img2.imgtn.bdimg.com/it/u=3185469209,1916946967&fm=26&gp=0.jpg";


    private List<FileInfo> mFileList;
    private MyAdapter mAdapter;

    private UpdateProgressBar updateProgressBar;

    private NotificationUtil mNotificationUtil = null;

    private UpdateProgressBar mRecive;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumedownload);
        ButterKnife.bind(this);


        initData();
    }

    private void initData() {
        // 初始化文件对象
        FileInfo fileInfo1 = new FileInfo(0, urlone, getFileName(urlone), 0, 0);
        FileInfo fileInfo2 = new FileInfo(1, urltwo, getFileName(urltwo), 0, 0);
        FileInfo fileInfo3 = new FileInfo(2,urlThree,getFileName(urlThree),0,0);

        mFileList=new ArrayList<>();
        mFileList.add(fileInfo1);
        mFileList.add(fileInfo2);
        mFileList.add(fileInfo3);

        mNotificationUtil = new NotificationUtil(ResumeDownloadActivity.this);
        mAdapter = new MyAdapter(this, mFileList);

        listView.setAdapter(mAdapter);

        mRecive = new UpdateProgressBar();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyService.ACTION_UPDATE);
        intentFilter.addAction(MyService.ACTION_FINISHED);
        intentFilter.addAction(MyService.ACTION_START);
        registerReceiver(mRecive, intentFilter);
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(updateProgressBar);
        super.onDestroy();
    }

    private String getFileName(String str){
        return str.substring(str.lastIndexOf("/")+1);
    }


    public class UpdateProgressBar extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            switch (intent.getAction()) {
                case MyService.ACTION_UPDATE:
                    int finished = intent.getIntExtra("finished", 0);
                    int id = intent.getIntExtra("id", 0);
                    mAdapter.updataProgress(id, finished);
                    mNotificationUtil.updataNotification(id, finished);
                    break;
                case MyService.ACTION_FINISHED:
                    FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
                    mAdapter.updataProgress(fileInfo.getId(), 0);
                    Toast.makeText(ResumeDownloadActivity.this, mFileList.get(fileInfo.getId()).getFileName() + "下载完毕", Toast.LENGTH_SHORT).show();
                    mNotificationUtil.cancelNotification(fileInfo.getId());
                    break;
                case MyService.ACTION_START:
                    mNotificationUtil.showNotification((FileInfo) intent.getSerializableExtra("fileInfo"));
                    break;
            }
        }
    }
}
