package com.returntolife.jjcode.mydemolist.demo.function.whiteService

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build

/**
 * Created by xiej on 2021/2/7
 */

object KeepCompactUtils {

    /**
     * 自启动应用
     */
    private val AUTO_START_INTENTS = arrayOf(
        // 小米
        Intent().setComponent(
            ComponentName("com.miui.securitycenter",
            "com.miui.permcenter.autostart.AutoStartManagementActivity")
        ),
        // 华为
        Intent().setComponent(ComponentName
            .unflattenFromString("com.huawei.systemmanager/.startupmgr.ui.StartupNormalAppListActivity")),
        Intent().setComponent(ComponentName
            .unflattenFromString("com.huawei.systemmanager/.appcontrol.activity.StartupAppControlActivity")),

        // 魅族
        Intent().setComponent(ComponentName.unflattenFromString("com.meizu.safe/.SecurityCenterActivity")),

        // 三星
        Intent().setComponent(ComponentName("com.samsung.android.sm_cn",
            "com.samsung.android.sm.autorun.ui.AutoRunActivity")),
        Intent().setComponent(ComponentName("com.samsung.android.sm_cn",
            "com.samsung.android.sm.ui.ram.AutoRunActivity")),
        Intent().setComponent(ComponentName("com.samsung.android.sm_cn",
            "com.samsung.android.sm.ui.appmanagement.AppManagementActivity")),
        Intent().setComponent(ComponentName("com.samsung.android.sm",
            "com.samsung.android.sm.autorun.ui.AutoRunActivity")),
        Intent().setComponent(ComponentName("com.samsung.android.sm",
            "com.samsung.android.sm.ui.ram.AutoRunActivity")),
        Intent().setComponent(ComponentName("com.samsung.android.sm",
            "com.samsung.android.sm.ui.appmanagement.AppManagementActivity")),
        Intent().setComponent(ComponentName("com.samsung.android.sm_cn",
            "com.samsung.android.sm.ui.cstyleboard.SmartManagerDashBoardActivity")),
        Intent().setComponent(ComponentName("com.samsung.android.sm",
            "com.samsung.android.sm.ui.cstyleboard.SmartManagerDashBoardActivity")),
        Intent().setComponent(ComponentName.unflattenFromString(
            "com.samsung.android.sm_cn/.app.dashboard.SmartManagerDashBoardActivity")),
        Intent().setComponent(ComponentName.unflattenFromString(
            "com.samsung.android.sm/.app.dashboard.SmartManagerDashBoardActivity")),

        // oppo
        Intent().setComponent(ComponentName
            .unflattenFromString("com.coloros.safecenter/.startupapp.StartupAppListActivity")),
        Intent().setComponent(ComponentName
            .unflattenFromString("com.coloros.safecenter/.permission.startupapp.StartupAppListActivity")),
        Intent().setComponent(ComponentName("com.coloros.safecenter",
            "com.coloros.privacypermissionsentry.PermissionTopActivity")),
        Intent().setComponent(
            ComponentName.unflattenFromString("com.oppo.safe/.permission.startup.StartupAppListActivity")),

        // vivo
        Intent().setComponent(ComponentName
            .unflattenFromString("com.vivo.permissionmanager/.activity.BgStartUpManagerActivity")),
        Intent().setComponent(ComponentName
            .unflattenFromString("com.iqoo.secure/.phoneoptimize.BgStartUpManager")),
        Intent().setComponent(ComponentName
            .unflattenFromString("com.vivo.permissionmanager/.activity.PurviewTabActivity")),
        Intent().setComponent(ComponentName
            .unflattenFromString("com.iqoo.secure/.ui.phoneoptimize.SoftwareManagerActivity")),

        // 一加
        Intent().setComponent(ComponentName
            .unflattenFromString("com.oneplus.security/.chainlaunch.view.ChainLaunchAppListActivity")),

        // 乐视
        Intent().setComponent(
            ComponentName.unflattenFromString("com.letv.android.letvsafe/.AutobootManageActivity")),

        // HTC
        Intent().setComponent(
            ComponentName.unflattenFromString("com.htc.pitroad/.landingpage.activity.LandingPageActivity"))
    )

    private val BATTERY_INTENTS = arrayOf(
        // 小米
        Intent().setComponent(ComponentName
            .unflattenFromString("com.miui.powerkeeper/.ui.HiddenAppsContainerManagementActivity")),

        // 华为
        Intent().setComponent(ComponentName
            .unflattenFromString("com.huawei.systemmanager/.power.ui.HwPowerManagerActivity")),

        // 魅族
        Intent().setComponent(ComponentName
            .unflattenFromString("com.meizu.safe/.SecurityCenterActivity")),

        // 三星
        Intent().setComponent(ComponentName("com.samsung.android.sm_cn",
            "com.samsung.android.sm.ui.battery.AppSleepListActivity")),
        Intent().setComponent(ComponentName("com.samsung.android.sm_cn",
            "com.samsung.android.sm.ui.battery.BatteryActivity")),
        Intent().setComponent(ComponentName("com.samsung.android.sm",
            "com.samsung.android.sm.ui.battery.AppSleepListActivity")),
        Intent().setComponent(ComponentName("com.samsung.android.sm",
            "com.samsung.android.sm.ui.battery.BatteryActivity")),
        Intent().setComponent(ComponentName("com.samsung.android.lool",
            "com.samsung.android.sm.battery.ui.BatteryActivity")),
        Intent().setComponent(ComponentName("com.samsung.android.lool",
            "com.samsung.android.sm.ui.battery.BatteryActivity")),
        Intent().setComponent(ComponentName("com.samsung.android.sm",
            "com.samsung.android.sm.ui.battery.BatteryActivity")),
        Intent().setComponent(ComponentName("com.samsung.android.sm_cn",
            "com.samsung.android.sm.ui.cstyleboard.SmartManagerDashBoardActivity")),

        // oppo
        Intent().setComponent(ComponentName
            .unflattenFromString("com.coloros.safecenter/.appfrozen.activity.AppFrozenSettingsActivity")),
        Intent().setComponent(ComponentName("com.coloros.oppoguardelf",
            "com.coloros.powermanager.fuelgaue.PowerUsageModelActivity")),
        Intent().setComponent(ComponentName("com.coloros.oppoguardelf",
            "com.coloros.powermanager.fuelgaue.PowerSaverModeActivity")),
        Intent().setComponent(ComponentName("com.coloros.oppoguardelf",
            "com.coloros.powermanager.fuelgaue.PowerConsumptionActivity")),
        Intent().setComponent(ComponentName
            .unflattenFromString("com.oppo.safe/.SecureSafeMainActivity")),

        // vivo
        Intent().setComponent(ComponentName("com.vivo.abe",
            "com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity")),
        Intent().setComponent(ComponentName.unflattenFromString("com.iqoo.powersaving/.PowerSavingManagerActivity"))
    )


    /**
     * @return 是否为三星s9 型号的手机
     */
    val isSamsungS9: Boolean
        get() = ("samsung".equals(Build.BRAND, ignoreCase = true) && !Build.MODEL.isNullOrBlank()
            && Build.MODEL.startsWith("SM-G9"))


    // 自启动
    fun daemonSet(activity: Activity): Boolean {
        for (intent in AUTO_START_INTENTS) {
            if (activity.packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                try {
                    activity.startActivity(intent)
                    return true
                } catch (e: Exception) {
//                    LogUtil.e("KeepCompactUtil", e.toString())
                    continue
                }
            }
        }
        return false
    }

    // 防睡眠
    fun noSleepSet(activity: Activity): Boolean {
        for (intent in BATTERY_INTENTS) {
            if (activity.packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                try {
                    activity.startActivity(intent)
                    return true
                } catch (e: Exception) {
//                    LogUtil.e("KeepCompactUtil", e.toString())
                    continue
                }
            }
        }
        return false
    }
}