package com.returntolife.jjcode.mydemolist.resumedownload;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.returntolife.jjcode.mydemolist.R;

import java.util.List;

/**
 * Created by 455 on 2017/8/7.
 */

public class ResumeDownloadAdapter extends BaseAdapter {

    private Context mContext = null;
    private List<FileInfo> mFilelist = null;

    private LayoutInflater layoutInflater;


    public ResumeDownloadAdapter(Context mContext, List<FileInfo> mFilelist) {
        super();
        this.mContext = mContext;
        this.mFilelist = mFilelist;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mFilelist.size();
    }

    @Override
    public Object getItem(int position) {
        return mFilelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        final FileInfo mFileInfo = mFilelist.get(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_resumedownload, null);
            viewHolder = new ViewHolder();
            viewHolder.textview = (TextView) convertView.findViewById(R.id.file_textview);
            viewHolder.startButton = (Button) convertView.findViewById(R.id.start_button);
            viewHolder.stopButton = (Button) convertView.findViewById(R.id.stop_button);
            viewHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar2);


            viewHolder.textview.setText(mFileInfo.getFileName());
            viewHolder.progressBar.setMax(100);

            viewHolder.startButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ResumeDownloadService.class);
                    intent.setAction(ResumeDownloadService.ACTION_START);
                    intent.putExtra("fileInfo", mFileInfo);
                    mContext.startService(intent);
                }
            });
            viewHolder.stopButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ResumeDownloadService.class);
                    intent.setAction(ResumeDownloadService.ACTION_STOP);
                    intent.putExtra("fileInfo", mFileInfo);
                    mContext.startService(intent);
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.progressBar.setProgress(mFileInfo.getFinished());


        return convertView;
    }


    public void updataProgress(int id, int progress) {
        FileInfo info = mFilelist.get(id);
        info.setFinished(progress);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView textview;
        Button startButton;
        Button stopButton;
        ProgressBar progressBar;
    }
}
