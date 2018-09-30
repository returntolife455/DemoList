package com.returntolife.jjcode.mydemolist.resumedownload;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.returntolife.jjcode.mydemolist.R;
import com.tools.jj.tools.adapter.BaseRecyclerViewHolder;
import com.tools.jj.tools.adapter.CommonDelegateAdapter;
import com.tools.jj.tools.http.RxBus2;
import com.tools.jj.tools.utils.StringUtil;
import com.tools.jj.tools.view.ClearEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by HeJiaJun on 2018/8/28.
 * des:
 * version:1.0.0
 */

public class ResumeDownloadActivity extends Activity {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.clear_et)
    ClearEditText clearEt;
    @BindView(R.id.btn_start)
    Button btnStart;


    private List<FileInfo> mFileList;
    private DelegateAdapter mAdapter;
    private CommonDelegateAdapter<FileInfo> commonDelegateAdapter;

//    private ThreadDAO threadDAO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumedownload);
        ButterKnife.bind(this);
        initData();
        initEvent();


        initAdapter();

    }

    private void initEvent() {
        RxBus2.getInstance().tObservable(EventFileInfo.class).subscribe(new Action1<EventFileInfo>() {
            @Override
            public void call(final EventFileInfo eventFileInfo) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(eventFileInfo.state==EventFileInfo.STATE_START){
                            mFileList.add(eventFileInfo.fileInfo);
                            commonDelegateAdapter.notifyDataSetChanged();
                        }else if(eventFileInfo.state==EventFileInfo.STATE_UPDATE_PROGRESS){
                            mFileList.set(eventFileInfo.fileInfo.getId(),eventFileInfo.fileInfo);
                            commonDelegateAdapter.notifyDataSetChanged();
                        }else if(eventFileInfo.state==EventFileInfo.STATE_FINISHED){
                            eventFileInfo.fileInfo.setFinished(eventFileInfo.fileInfo.getLength());
                            mFileList.set(eventFileInfo.fileInfo.getId(),eventFileInfo.fileInfo);
                            commonDelegateAdapter.notifyDataSetChanged();
                        }
                    }
                });

            }
        });
    }

    private void initAdapter() {
        VirtualLayoutManager manager = new VirtualLayoutManager(this);
        recyclerview.setLayoutManager(manager);
        mAdapter = new DelegateAdapter(manager);
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        commonDelegateAdapter = new CommonDelegateAdapter<FileInfo>(this, R.layout.item_resumedownload, mFileList, linearLayoutHelper, 0) {
            @Override
            public void convert(final BaseRecyclerViewHolder holder, final FileInfo fileInfo, final int position) {
                holder.setText(R.id.tv_filename, fileInfo.getFileName());

                Button btn=holder.getView(R.id.btn_start);
                String btnText=btn.getText().toString();

                if(btnText.equals("暂停")){
                    holder.setOnClickListener(R.id.btn_start, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ResumeDownloadActivity.this, ResumeDownloadService.class);
                            intent.setAction(ResumeDownloadService.ACTION_PAUSE);
                            intent.putExtra(ResumeDownloadService.INTENT_DATA_FILEINFO, fileInfo);
                            ResumeDownloadActivity.this.startService(intent);
                            holder.setText(R.id.btn_start,"继续");
                            commonDelegateAdapter.notifyDataSetChanged();
                        }
                    });
                }else {
                    holder.setOnClickListener(R.id.btn_start, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ResumeDownloadActivity.this, ResumeDownloadService.class);
                            intent.setAction(ResumeDownloadService.ACTION_CONTINUE);
                            intent.putExtra(ResumeDownloadService.INTENT_DATA_FILEINFO, fileInfo);
                            ResumeDownloadActivity.this.startService(intent);
                            holder.setText(R.id.btn_start,"暂停");
                            commonDelegateAdapter.notifyDataSetChanged();
                        }
                    });
                }


                holder.setOnClickListener(R.id.btn_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ResumeDownloadActivity.this, ResumeDownloadService.class);
                        intent.setAction(ResumeDownloadService.ACTION_STOP);
                        intent.putExtra(ResumeDownloadService.INTENT_DATA_FILEINFO, fileInfo);
                        ResumeDownloadActivity.this.startService(intent);
                        holder.setText(R.id.btn_start,"暂停");
                        mFileList.remove(position);
                        commonDelegateAdapter.notifyDataSetChanged();
                    }
                });

                if(fileInfo.getFinished()>=fileInfo.getLength()){
                    holder.setViewVisible(R.id.btn_start,false);
                }

                ProgressBar progressBar = holder.getView(R.id.pb);
                progressBar.setMax(fileInfo.getLength());
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
        mFileList = new ArrayList<>();
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private String getFileName(String str) {
        return str.substring(str.lastIndexOf("/") + 1);
    }

    @OnClick(R.id.btn_start)
    public void onViewClicked() {
        String url=clearEt.getText().toString();
        if(!StringUtil.isEmpty(url)){
            clearEt.setText("");
            if(mFileList==null){
                mFileList=new ArrayList<>();
            }else{
                for (FileInfo info : mFileList) {
                    if(info.getUrl().equals(url)){
                        Toast.makeText(this, "已在列表中", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            FileInfo fileInfo=new FileInfo();
            fileInfo.setFileName(getFileName(url));
            fileInfo.setId(mFileList.size());
            fileInfo.setUrl(url);
            Intent intent = new Intent(ResumeDownloadActivity.this, ResumeDownloadService.class);
            intent.setAction(ResumeDownloadService.ACTION_START);
            intent.putExtra(ResumeDownloadService.INTENT_DATA_FILEINFO, fileInfo);
            ResumeDownloadActivity.this.startService(intent);
        }
    }



}
