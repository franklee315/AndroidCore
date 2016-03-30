package com.common.androidcore.info;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by lifan on 15/6/26.
 */
public class AppInfoUtil {
    public static AppInfoUtil appInfoUtil;
    private Context context;

    private AppInfoUtil(Context context) {
        this.context = context;
    }

    public static AppInfoUtil getInstance(Context context) {
        if (appInfoUtil == null) {
            appInfoUtil = new AppInfoUtil(context);
        }
        return appInfoUtil;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersionName() {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public int getVersionCode() {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
