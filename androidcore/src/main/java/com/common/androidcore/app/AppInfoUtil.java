package com.common.androidcore.app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.common.androidcore.BaseApplication;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 *  == =
 * Created by lifan on 15/6/26.
 * app信息
 */
public class AppInfoUtil {
    public static AppInfoUtil appInfoUtil;
    private Context context;

    private AppInfoUtil() {
        this.context = BaseApplication.getInstance();
    }

    public static AppInfoUtil getInstance() {
        if (appInfoUtil == null) {
            appInfoUtil = new AppInfoUtil();
        }
        return appInfoUtil;
    }

    /**
     * 获得屏幕方向
     *
     * @return 屏幕方向
     */
    public int getViewState() {
        return context.getResources().getConfiguration().orientation;
    }

    /**
     * 获取版本名称
     *
     * @return 当前应用的版本名称
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

    /**
     * 当前app是否为中文
     *
     * @return 是否为中文
     */
    public boolean isSystemZh() {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        return language.endsWith("zh");
    }

    /**
     * 切换app语言，只有中文和英文两种
     *
     * @param isZh 是否切换到中文
     */
    public void switchLanguage(boolean isZh) {
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        if (!isZh) {
            config.locale = Locale.ENGLISH;
        } else {
            config.locale = Locale.SIMPLIFIED_CHINESE;
        }
        resources.updateConfiguration(config, dm);
    }

    /**
     * 检查App是否还在前台运行
     *
     * @return App是否还在前台运行
     */
    public boolean checkAppIsForeground() {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfoList = activityManager.getRunningAppProcesses();
        Iterator<ActivityManager.RunningAppProcessInfo> l = appProcessInfoList.iterator();
        String packageName = context.getPackageName();
        while (l.hasNext()) {
            ActivityManager.RunningAppProcessInfo AppInfo = l.next();
            if (AppInfo.processName.equals(packageName)) {
                return AppInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
            }
        }
        return false;
    }

    /**
     * 获取当前进程的名字
     *
     * @return 当前进程的名字
     */
    public String getCurProcessName() {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appList = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appList) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 判断当前应用的是否为前台task
     *
     * @return 当前应用的是否为前台task
     */
    public boolean isTopActivity() {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            String currentTask = tasksInfo.get(0).topActivity.getPackageName();
            if (TextUtils.equals(context.getPackageName(), currentTask)) {
                return true;
            }
        }
        return false;
    }
}
