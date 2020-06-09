package com.returntolife.jjcode.mydemolist;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;


import com.returntolife.jjcode.mydemolist.demo.function.tinker.TinkerManager;
import com.returntolife.jjcode.mydemolist.utils.GlideLoader;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;
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

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(getApplication());
        initTinker();
        init();
        pAppContext=getApplication();
    }

    private void initTinker() {
        TinkerManager.setTinkerApplicationLike(this);

        TinkerManager.initFastCrashProtect();
        //should set before tinker is installed
        TinkerManager.setUpgradeRetryEnable(true);

        //optional set logIml, or you can use default debug log
//        TinkerInstaller.setLogIml(new MyLogImp());

        //installTinker after load multiDex
        //or you can put com.tencent.tinker.** to main dex
        TinkerManager.installTinker(this);
        Tinker tinker = Tinker.with(getApplication());
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
