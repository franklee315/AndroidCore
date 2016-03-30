package com.common.androidcore.app;

import android.content.Context;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/3/30.
 */
public class DimenUtil {
    public static DimenUtil dimenUtil;
    private Context context;

    private DimenUtil(Context context) {
        this.context = context;
    }

    public static DimenUtil getInstance(Context context) {
        if (dimenUtil == null) {
            dimenUtil = new DimenUtil(context);
        }
        return dimenUtil;
    }

    /**
     * px值转dp值
     *
     * @param pxValue px值
     * @return dp值
     */
    public int px2dp(float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * dp值转px值
     *
     * @param dpValue dp值
     * @return px 值
     */
    public int dp2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获得屏幕宽度
     *
     * @return 屏幕宽度
     */
    public int getWidth() {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @return 屏幕高度
     */
    public int getHeight() {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }


    /**
     * 获得状态栏高度
     *
     * @return 状态栏高度
     */
    public int getStatusBarHeight() {
        int statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
}
