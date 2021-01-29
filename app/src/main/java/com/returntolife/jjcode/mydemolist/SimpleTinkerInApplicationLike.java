package com.returntolife.jjcode.mydemolist;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;


import com.returntolife.jjcode.mydemolist.demo.function.tinker.SampleResultService;
import com.returntolife.jjcode.mydemolist.demo.function.tinker.TinkerManager;
import com.returntolife.jjcode.mydemolist.utils.GlideLoader;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.entry.ApplicationLike;
import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tools.jj.tools.http.Http;
import com.tools.jj.tools.imageload.ImageLoader;
import com.tools.jj.tools.utils.LogUtil;


@DefaultLifeCycle(application = ".SimpleTinkerInApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class SimpleTinkerInApplicationLike extends ApplicationLike {
    public static Context pAppContext=null;

    public SimpleTinkerInApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }
    /**
     * install multiDex before install tinker
     * so we don't need to put the tinker lib classes in the main dex
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        DefaultLoadReporter defaultLoadReporter = new DefaultLoadReporter(getApplication());
        DefaultPatchReporter patchReporter = new DefaultPatchReporter(getApplication());
        DefaultPatchListener defaultPatchListener = new DefaultPatchListener(getApplication());
        AbstractPatch upgradePatchProcessor = new UpgradePatch();
        TinkerInstaller.install(this, defaultLoadReporter, patchReporter,
            defaultPatchListener,
            SampleResultService.class, upgradePatchProcessor);

    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(getApplication());
        init();
        pAppContext=getApplication();
    }

    private void init() {
        //开启debug
        LogUtil.OPEN_LOG=true;
        //图片框架初始化
        ImageLoader.getInstance().setImageLoader(new GlideLoader());
        //接口请求初始化
        Http.initHttp(getApplication());
    }
}
