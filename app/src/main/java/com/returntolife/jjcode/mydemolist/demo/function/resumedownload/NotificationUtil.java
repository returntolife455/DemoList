package com.returntolife.jjcode.mydemolist.demo.function.resumedownload;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.returntolife.jjcode.mydemolist.main.activity.MainActivity;
import com.returntolife.jjcode.mydemolist.R;

import java.util.HashMap;
import java.util.Map;

public class NotificationUtil {

	private Context mContext;
	private NotificationManager mNotificationManager = null;
	private Map<Integer, Notification> mNotifications = null;
	
	public NotificationUtil(Context context) {
		this.mContext = context;
		// 获得系统通知管理者
		mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		// 创建通知的集合
		mNotifications = new HashMap<Integer, Notification>();
	}

	/**
	 * 显示通知栏
	 * @param fileInfo
	 */
	public void showNotification(FileInfo fileInfo) {
		// 判断通知是否已经显示
		if(!mNotifications.containsKey(fileInfo.getId())){
			Notification notification = new Notification();
			notification.tickerText = fileInfo.getFileName() + "开始下载";
			notification.when = System.currentTimeMillis();
			notification.icon = R.mipmap.ic_launcher;
			notification.flags = Notification.FLAG_AUTO_CANCEL;

			// 点击通知之后的意图
			Intent intent = new Intent(mContext, MainActivity.class);
			PendingIntent pd = PendingIntent.getActivity(mContext, 0, intent, 0);
			notification.contentIntent = pd;

			// 设置远程试图RemoteViews对象
			RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.resume_download_notification);
			// 控制远程试图，设置开始点击事件
			Intent intentStart = new Intent(mContext, ResumeDownloadService.class);
			intentStart.setAction(ResumeDownloadService.ACTION_START);
			intentStart.putExtra("fileInfo", fileInfo);
			PendingIntent piStart = PendingIntent.getService(mContext, 0, intentStart, 0);
			remoteViews.setOnClickPendingIntent(R.id.start_button, piStart);

			// 控制远程试图，设置结束点击事件
			Intent intentStop = new Intent(mContext, ResumeDownloadService.class);
			intentStop.setAction(ResumeDownloadService.ACTION_STOP);
			intentStop.putExtra("fileInfo", fileInfo);
			PendingIntent piStop = PendingIntent.getService(mContext, 0, intentStop, 0);
			remoteViews.setOnClickPendingIntent(R.id.stop_button, piStop);
			// 设置TextView中文件的名字
			remoteViews.setTextViewText(R.id.file_textview, fileInfo.getFileName());
			// 设置Notification的视图
			notification.contentView = remoteViews;
			// 发出Notification通知
			mNotificationManager.notify(fileInfo.getId(), notification);
			// 把Notification添加到集合中
			mNotifications.put(fileInfo.getId(), notification);
		}
	}
	/**
	 * 取消通知栏通知
	 */
	public void cancelNotification(int id) {
		mNotificationManager.cancel(id);
		mNotifications.remove(id);
	}
	/**
	 * 更新通知栏进度条
	 * @param id 获取Notification的id
	 * @param progress 获取的进度
	 */
	public void updataNotification(int id, int progress) {
		Notification notification = mNotifications.get(id);
		if (notification != null) {
			// 修改进度条进度
			notification.contentView.setProgressBar(R.id.progressBar2, 100, progress, false);
			mNotificationManager.notify(id, notification);
		}
	}
}
