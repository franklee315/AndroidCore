package com.common.androidcore;

import android.app.Activity;
import android.app.Application;
import android.text.TextUtils;

import com.common.androidcore.app.AppInfoUtil;
import com.common.androidcore.logger.Logger;

public class BaseApplication extends Application {
    private static BaseApplication instance;
    private static Activity topActivity;
    private static boolean isForeground;

    @Override
    public void onCreate() {
        super.onCreate();
        String processName = AppInfoUtil.getInstance(this).getCurProcessName();
        Logger.d("current process name is %s", processName);
        if (!TextUtils.equals(processName, getPackageName())) {
            return;
        }
        if (instance == null) {
            instance = this;
        }
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public static Activity getTopActivity() {
        return topActivity;
    }

    public static void setTopActivity(Activity topActivity) {
        BaseApplication.topActivity = topActivity;
    }

    public static boolean isForeground() {
        return isForeground;
    }

    public static void setIsForeground(boolean isForeground) {
        BaseApplication.isForeground = isForeground;
    }
}