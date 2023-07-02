package com.returntolife.jjcode.mydemolist.demo.function.resumedownload;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tools.jj.tools.views.ClearEditText;
import com.returntolife.jjcode.mydemolist.R;
import com.tools.jj.tools.adapter.BaseRecyclerViewHolder;
import com.tools.jj.tools.adapter.CommonRecyclerAdapter;
import com.tools.jj.tools.http.RxBus2;
import com.tools.jj.tools.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

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
    private CommonRecyclerAdapter<FileInfo> mAdapter;

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
        RxBus2.getInstance().tObservable(EventFileInfo.class)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<EventFileInfo>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull EventFileInfo eventFileInfo) {
                    if(eventFileInfo.state==EventFileInfo.STATE_START){
                        mFileList.add(eventFileInfo.fileInfo);
                        mAdapter.notifyDataSetChanged();
                    }else if(eventFileInfo.state==EventFileInfo.STATE_UPDATE_PROGRESS){
                        mFileList.set(eventFileInfo.fileInfo.getId(),eventFileInfo.fileInfo);
                        mAdapter.notifyDataSetChanged();
                    }else if(eventFileInfo.state==EventFileInfo.STATE_FINISHED){
                        eventFileInfo.fileInfo.setFinished(eventFileInfo.fileInfo.getLength());
                        mFileList.set(eventFileInfo.fileInfo.getId(),eventFileInfo.fileInfo);
                        mAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
    }

    private void initAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);
        mAdapter = new CommonRecyclerAdapter<FileInfo>(this,R.layout.item_resumedownload,mFileList) {
            @Override
            protected void convert(BaseRecyclerViewHolder holder,  FileInfo fileInfo, int position) {
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
                            mAdapter.notifyDataSetChanged();
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
                            mAdapter.notifyDataSetChanged();
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
                        mAdapter.notifyDataSetChanged();
                    }
                });

                if(fileInfo.getFinished()>=fileInfo.getLength()){
                    holder.setViewVisible(R.id.btn_start,false);
                }

                ProgressBar progressBar = holder.getView(R.id.pb);
                progressBar.setMax(fileInfo.getLength());
                progressBar.setProgress(fileInfo.getFinished());
            }

        };

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
