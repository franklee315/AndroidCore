package com.common.androidcore;

import android.app.Activity;
import android.app.Application;

/**
 * Created by lifan on 2015/7/10.
 * Application基类
 */
public class BaseApplication extends Application {
    private static BaseApplication instance;
    private static Activity topActivity;
    private static boolean isForeground;

    @Override
    public void onCreate() {
        super.onCreate();
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