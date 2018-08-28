package com.tools.jj.tools.activity.permission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.util.SimpleArrayMap;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * <p>PermissionsActivity类 检查权限的工具类</p>
 *
 * @author wangchenlong （from net https://github.com/SpikeKing/wcl-permission-demo）
 * @version 1.0
 *          1.1 update by zmingchun on 2016/2/17
 */
public class PermissionsChecker {
    private final Context mContext;

    // Map of dangerous permissions introduced in later framework versions.
    // Used to conditionally bypass permission-hold checks on older devices.
    private static final SimpleArrayMap<String, Integer> MIN_SDK_PERMISSIONS;
    static {
        MIN_SDK_PERMISSIONS = new SimpleArrayMap<>(6);
        MIN_SDK_PERMISSIONS.put("com.android.voicemail.permission.ADD_VOICEMAIL", 14);
        MIN_SDK_PERMISSIONS.put("android.permission.BODY_SENSORS", 20);
        MIN_SDK_PERMISSIONS.put("android.permission.READ_CALL_LOG", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.READ_EXTERNAL_STORAGE", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.USE_SIP", 9);
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_CALL_LOG", 16);
    }

    /**
     * 构造器
     * @param context 上下文
     */
    public PermissionsChecker(Context context) {
        mContext = context.getApplicationContext();
    }
    /**
     * 判断权限集合
     * @param permissions 权限集合
     * @return
     */
    public boolean lacksPermissions(String... permissions) {
        return hasSelfPermissions(permissions);
    }

    /**
     * 判断是否拥有给定的权限集(只有有一个缺失就返回false)
     * Returns true if the Activity or Fragment has access to all given permissions.
     *
     * @param permissions permission list 权限集合
     * @return returns true if the Activity or Fragment has access to all given permissions.
     */
    private boolean hasSelfPermissions(String... permissions) {
        if(permissions==null)return false;
        for (String permission : permissions) {
            if (permissionExists(permission) && !hasSelfPermission(permission)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否获得了权限
     * Determine context has access to the given permission.
     *
     * This is a workaround for RuntimeException of Parcel#readException.
     * For more detail, check this issue https://github.com/hotchemi/PermissionsDispatcher/issues/107
     *
     * @param permission permission 权限码
     * @return returns true if context has access to the given permission, false otherwise.
     * @see #hasSelfPermissions(String...)
     */
    private boolean hasSelfPermission(String permission) {
        try {
            return checkSelfPermission(mContext,permission) == PackageManager.PERMISSION_GRANTED;
        } catch (Exception t) {
            return false;
        }
    }

    /**
     * Returns true if the permission exists in this SDK version
     *
     * @param permission permission
     * @return returns true if the permission exists in this SDK version
     */
    private boolean permissionExists(String permission) {
        // Check if the permission could potentially be missing on this device
        Integer minVersion = MIN_SDK_PERMISSIONS.get(permission);
        // If null was returned from the above call, there is no need for a device API level check for the permission;
        // otherwise, we check if its minimum API level requirement is met
        return minVersion == null || Build.VERSION.SDK_INT >= minVersion;
    }
}
