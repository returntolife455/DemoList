package com.returntolife.jjcode.mydemolist.demo.function.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.tools.jj.tools.utils.DelayUtil;
import com.tools.jj.tools.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by JiaJunHe on 2020/4/12 13:55
 * Email 455hejiajun@gmail.com
 * Description:
 * Version: 1.0
 */
public class MyAccessibilityService extends AccessibilityService {
    private static final String TAG = "MyAccessibilityService";
    String[] PACKAGES = { "com.android.settings" };

    private boolean isStartShuaFen;

    private Handler handler = new Handler();
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        LogUtil.d("config success!");
    }


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        int eventType = event.getEventType();

        if (eventType == AccessibilityEvent.TYPE_VIEW_CLICKED){
            LogUtil.d("==============Start====================");
            AccessibilityNodeInfo accessibilityNodeInfo=event.getSource();
            if(accessibilityNodeInfo==null){
                LogUtil.d("=============END=====================");
                return;
            }

            if (accessibilityNodeInfo.getPackageName().equals(this.getPackageName())) {
                if (!isStartShuaFen) {
                    if (accessibilityNodeInfo.getText() != null && accessibilityNodeInfo.getText().toString().contains("开始学习")) {
                        LogUtil.d("开始学习");
                        startShuaFen();
                    }
                }
            }
            LogUtil.d("=============END=====================");
        }
    }

    private void startShuaFen(){
        isStartShuaFen=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                DelayUtil.timeDelay(10,15);
                findAndClick("android.widget.TextView", "我的");
                DelayUtil.timeDelay(3, 5);
                findAndClick("android.widget.TextView", "学习积分");
            }
        }).start();
    }


    public void findAndClick(String widgetName, String viewText)
    {
        List<AccessibilityNodeInfo> nodeInfoList = findNodes(widgetName, viewText);
        if(nodeInfoList==null||nodeInfoList.isEmpty()){
            return;
        }
        performClick(nodeInfoList.get(0));
    }


    /**
     * 获取所有文本不为空的节点
     * @param paramAccessibilityNodeInfo
     * @return
     */
    public static List<AccessibilityNodeInfo> findNodeInfoIsNotEmptyText(AccessibilityNodeInfo paramAccessibilityNodeInfo)
    {
        if (paramAccessibilityNodeInfo == null)
            return null;
        return findNodeInfoIsNotEmptyText(paramAccessibilityNodeInfo, new ArrayList<AccessibilityNodeInfo>());
    }

    private static List<AccessibilityNodeInfo> findNodeInfoIsNotEmptyText(AccessibilityNodeInfo paramAccessibilityNodeInfo, List<AccessibilityNodeInfo> paramList) {
        if (paramAccessibilityNodeInfo != null) {
            int i = 0;
            if ((TextUtils.isEmpty(paramAccessibilityNodeInfo.getContentDescription())) && (TextUtils.isEmpty(paramAccessibilityNodeInfo.getText()))){
                while (i < paramAccessibilityNodeInfo.getChildCount()) {
                    findNodeInfoIsNotEmptyText(paramAccessibilityNodeInfo.getChild(i), paramList);
                    i++;
                    paramList.add(paramAccessibilityNodeInfo);
                }
            }
        }
        return paramList;
    }


    public void findAndClick(String paramString)
    {
        List<AccessibilityNodeInfo> nodeList = findNodes(paramString);
        if(nodeList==null||nodeList.isEmpty()){
            LogUtil.d("findAndClick  节点为空");
            return;
        }

        if("我的".equals(paramString)){
            performClick(nodeList.get(nodeList.size()-1));
        }
    }


    public void performClick(AccessibilityNodeInfo paramAccessibilityNodeInfo)
    {
        if (paramAccessibilityNodeInfo == null)
            return;
        if (paramAccessibilityNodeInfo.isClickable())
            paramAccessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        else
            performClick(paramAccessibilityNodeInfo.getParent());
    }

    /**
     * 根据文案获取节点
     * @param nodeText
     * @return
     */
    private List<AccessibilityNodeInfo> findNodes(String nodeText) {
        AccessibilityNodeInfo localAccessibilityNodeInfo = getRootInActiveWindow();
        if (localAccessibilityNodeInfo == null) {
            LogUtil.d("rootWindow为空");
            return null;
        }
        List<AccessibilityNodeInfo> resultNodeList = localAccessibilityNodeInfo.findAccessibilityNodeInfosByText(nodeText);
        if ((resultNodeList != null) && (!resultNodeList.isEmpty())) {
            localAccessibilityNodeInfo.recycle();
            return resultNodeList;
        }
        return null;
    }

    /**
     *
     * @param widgetName
     * @param text
     * @return
     */
    private List<AccessibilityNodeInfo> findNodes(String widgetName, String text)
    {
        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        if (rootNodeInfo == null)
        {

            return null;
        }
        List<AccessibilityNodeInfo> widgetNodeList = findAllNodeInfosByClassName(rootNodeInfo,widgetName);
        if(widgetNodeList==null||widgetName.isEmpty()){
            return null;
        }

        List<AccessibilityNodeInfo> accessibilityNodeInfos=new ArrayList<>();
        for (AccessibilityNodeInfo accessibilityNodeInfo : widgetNodeList) {
            if(accessibilityNodeInfo.getText()!=null&&text.equals(accessibilityNodeInfo.getText().toString()))
                accessibilityNodeInfos.add(accessibilityNodeInfo);
        }
        return accessibilityNodeInfos;
    }


    public static List<AccessibilityNodeInfo> findNodesInfosById(AccessibilityNodeInfo paramAccessibilityNodeInfo, String viewId)
    {
        if (Build.VERSION.SDK_INT >= 18)
        {
            List<AccessibilityNodeInfo> nodeList = paramAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId(viewId);
            if ((nodeList != null) && (!nodeList.isEmpty()))
                return nodeList;
        }
        return null;
    }


    public static List<AccessibilityNodeInfo> findNodesInfosByText(AccessibilityNodeInfo paramAccessibilityNodeInfo, String viewText)
    {
        List<AccessibilityNodeInfo > nodeList = paramAccessibilityNodeInfo.findAccessibilityNodeInfosByText(viewText);
        if ((nodeList != null) && (!nodeList.isEmpty()))
            return nodeList;
        return null;
    }



    public static List<AccessibilityNodeInfo> findAllNodeInfosByClassName(AccessibilityNodeInfo paramAccessibilityNodeInfo, String className)
    {
        ArrayList<AccessibilityNodeInfo> localArrayList = new ArrayList<>();
        if (paramAccessibilityNodeInfo == null)
            return localArrayList;
        return findAllNodeInfosByClassName(paramAccessibilityNodeInfo, className, localArrayList);
    }

    private static List<AccessibilityNodeInfo> findAllNodeInfosByClassName(AccessibilityNodeInfo paramAccessibilityNodeInfo, String className, List<AccessibilityNodeInfo> paramList)
    {
        if (paramAccessibilityNodeInfo != null)
            if (className.contentEquals(paramAccessibilityNodeInfo.getClassName()))
                paramList.add(paramAccessibilityNodeInfo);
            else
                for (int i = 0; i < paramAccessibilityNodeInfo.getChildCount(); i++)
                    findAllNodeInfosByClassName(paramAccessibilityNodeInfo.getChild(i), className, paramList);
        return paramList;
    }



    @Override
    public void onInterrupt() {
        LogUtil.d("onInterrupt");

    }


    private void notifyWechat(AccessibilityEvent event) {
        if (event.getParcelableData() != null && event.getParcelableData() instanceof Notification) {
            Notification notification = (Notification) event.getParcelableData();
            if(notification!=null&&notification.tickerText!=null){
                String content = notification.tickerText.toString();
                String[] msg = content.split(":");
                if(msg.length>2){
                    String name = msg[0].trim();
                    String text = msg[1].trim();

                    LogUtil.d("name="+name+"--text="+text);
                }else {
                    LogUtil.d("content="+content);
                }

                PendingIntent pendingIntent = notification.contentIntent;
                try {
                    pendingIntent.send();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(fill()){
                                LogUtil.d("fill success");
                                send();
                            }

                        }
                    },1000);

                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
            }

        }
    }



    /**
     * 寻找窗体中的“发送”按钮，并且点击。
     */
    private void send() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> list = nodeInfo
                    .findAccessibilityNodeInfosByText("发送");
            if (list != null && list.size() > 0) {
                for (AccessibilityNodeInfo n : list) {
                    n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }

            } else {
                List<AccessibilityNodeInfo> liste = nodeInfo
                        .findAccessibilityNodeInfosByText("Send");
                if (liste != null && liste.size() > 0) {
                    for (AccessibilityNodeInfo n : liste) {
                        n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        LogUtil.d("send click");
                    }
                }
            }
        }

    }


        private boolean fill() {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            return findEditText(rootNode, "【自动回复】正在忙，稍后回复");
        }
        return false;
    }


    private boolean findEditText(AccessibilityNodeInfo rootNode, String content) {
        int count = rootNode.getChildCount();

        android.util.Log.d("maptrix", "root class=" + rootNode.getClassName() + ","+ rootNode.getText()+","+count);
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                android.util.Log.d("maptrix", "nodeinfo = null");
                continue;
            }

            android.util.Log.d("maptrix", "class=" + nodeInfo.getClassName());
            android.util.Log.e("maptrix", "ds=" + nodeInfo.getContentDescription());
//            if(nodeInfo.getContentDescription() != null){
//                int nindex = nodeInfo.getContentDescription().toString().indexOf(name);
//                int cindex = nodeInfo.getContentDescription().toString().indexOf(scontent);
//                android.util.Log.e("maptrix", "nindex=" + nindex + " cindex=" +cindex);
//                if(nindex != -1){
//                    itemNodeinfo = nodeInfo;
//                    android.util.Log.i("maptrix", "find node info");
//                }
//            }
            if ("android.widget.EditText".equals(nodeInfo.getClassName())) {
                android.util.Log.i("maptrix", "==================");
                Bundle arguments = new Bundle();
                arguments.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT,
                        AccessibilityNodeInfo.MOVEMENT_GRANULARITY_WORD);
                arguments.putBoolean(AccessibilityNodeInfo.ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN,
                        true);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY,
                        arguments);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                ClipData clip = ClipData.newPlainText("label", content);
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(clip);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PASTE);
                return true;
            }

            if (findEditText(nodeInfo, content)) {
                return true;
            }
        }

        return false;
    }

}
