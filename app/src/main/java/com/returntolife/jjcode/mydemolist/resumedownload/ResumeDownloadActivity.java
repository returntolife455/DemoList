package com.returntolife.jjcode.mydemolist.resumedownload;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.returntolife.jjcode.mydemolist.R;
import com.tools.jj.tools.adapter.BaseRecyclerViewHolder;
import com.tools.jj.tools.adapter.CommonDelegateAdapter;

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


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private String urlone = "http://f4.market.xiaomi.com/download/AppStore/0bc59a541cfb546f425715027a1a3271281145ef1/com.tencent.wok.apk";

    //private String urlone = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502039037885&di=3b76f156e639418d448822c1533c2010&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F5%2F57b6cfcd3f313.jpg";
    private String urltwo = "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1210/08/c1/14307187_1349676294934.jpg";
    private String urlThree = "http://img2.imgtn.bdimg.com/it/u=3185469209,1916946967&fm=26&gp=0.jpg";


    private List<FileInfo> mFileList;
    private DelegateAdapter mAdapter;
    private CommonDelegateAdapter<FileInfo> commonDelegateAdapter;

    private UpdateProgressBar updateProgressBar;

    private NotificationUtil mNotificationUtil = null;

    private UpdateProgressBar mRecive;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumedownload);
        ButterKnife.bind(this);


        initData();
        initAdapter();
    }

    private void initAdapter() {
        VirtualLayoutManager manager = new VirtualLayoutManager(this);
        recyclerview.setLayoutManager(manager);
        mAdapter=new DelegateAdapter(manager);
        LinearLayoutHelper linearLayoutHelper=new LinearLayoutHelper();
        commonDelegateAdapter=new CommonDelegateAdapter<FileInfo>(this,R.layout.item_resumedownload,mFileList,linearLayoutHelper,0) {
            @Override
            public void convert(BaseRecyclerViewHolder holder, final FileInfo fileInfo, int position) {
                holder.setText(R.id.tv_filename,fileInfo.getFileName());
                holder.setOnClickListener(R.id.btn_start, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(ResumeDownloadActivity.this,ResumeDownloadService.class);
                        intent.setAction(ResumeDownloadService.ACTION_START);
                        intent.putExtra(ResumeDownloadService.INTENT_DATA_FILEINFO, fileInfo);
                        ResumeDownloadActivity.this.startService(intent);
                    }
                });
                holder.setOnClickListener(R.id.btn_stop, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(ResumeDownloadActivity.this,ResumeDownloadService.class);
                        intent.setAction(ResumeDownloadService.ACTION_STOP);
                        intent.putExtra(ResumeDownloadService.INTENT_DATA_FILEINFO, fileInfo);
                        ResumeDownloadActivity.this.startService(intent);
                    }
                });
                ProgressBar progressBar=holder.getView(R.id.pb);
                progressBar.setMax(100);
                progressBar.setProgress(fileInfo.getFinished());
            }

            @Override
            public void convert(BaseRecyclerViewHolder holder, int position) {

            }
        };

        mAdapter.addAdapter(commonDelegateAdapter);
        recyclerview.setAdapter(mAdapter);
    }

    private void initData() {
        // 初始化文件对象
        FileInfo fileInfo1 = new FileInfo(0, urlone, getFileName(urlone), 0, 0);
        FileInfo fileInfo2 = new FileInfo(1, urltwo, getFileName(urltwo), 0, 0);
        FileInfo fileInfo3 = new FileInfo(2, urlThree, getFileName(urlThree), 0, 0);

        mFileList = new ArrayList<>();
        mFileList.add(fileInfo1);
        mFileList.add(fileInfo2);
        mFileList.add(fileInfo3);






        mRecive = new UpdateProgressBar();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ResumeDownloadService.ACTION_UPDATE);
        intentFilter.addAction(ResumeDownloadService.ACTION_FINISHED);
        intentFilter.addAction(ResumeDownloadService.ACTION_START);
        registerReceiver(mRecive, intentFilter);
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(updateProgressBar);
        super.onDestroy();
    }

    private String getFileName(String str) {
        return str.substring(str.lastIndexOf("/") + 1);
    }


    public class UpdateProgressBar extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(TextUtils.isEmpty(action)){
                return;
            }
            switch (intent.getAction()) {
                case ResumeDownloadService.ACTION_UPDATE:
                    int finished = (int) intent.getFloatExtra("finished", 0);
                    int id = intent.getIntExtra("id", 0);

                    mFileList.get(id).setFinished(finished);
                    commonDelegateAdapter.notifyDataSetChanged();
                    break;
                case ResumeDownloadService.ACTION_FINISHED:
                    FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
                    mFileList.get(fileInfo.getId()).setFinished(100);
                    commonDelegateAdapter.notifyDataSetChanged();
                    Toast.makeText(ResumeDownloadActivity.this, mFileList.get(fileInfo.getId()).getFileName() + "下载完毕", Toast.LENGTH_SHORT).show();
                    break;
                case ResumeDownloadService.ACTION_START:
                    break;
            }
        }
    }
}
