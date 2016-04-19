package com.common.androidcore.ui;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by lifan on 2014/3/21.
 * 显示Toast
 */
public class ToastSingle {
    private final static int NULL = -1;

    /**
     * 唯一Toast
     */
    private static Toast toast;

    /**
     * 显示Toast
     *
     * @param context
     * @param str     显示的文字
     */
    public static void showToast(Context context, String str) {
        showToast(context, NULL, str, Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast
     *
     * @param context
     * @param strId   显示的文字资源Id
     */
    public static void showToast(Context context, int strId) {
        showToast(context, strId, null, Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast
     *
     * @param context
     * @param str     显示的文字
     */
    public static void showLongToast(Context context, String str) {
        showToast(context, NULL, str, Toast.LENGTH_LONG);
    }

    /**
     * 显示Toast
     *
     * @param context
     * @param strId   显示的文字资源Id
     */
    public static void showLongToast(Context context, int strId) {
        showToast(context, strId, null, Toast.LENGTH_LONG);
    }

    private static void showToast(Context context, int strId, String str, int duration) {
        if (strId != NULL) {
            str = context.getResources().getString(strId);
        }
        if (!TextUtils.isEmpty(str)) {
            getToast(context, str, duration).show();
        }
    }

    private static android.widget.Toast getToast(Context context, String str, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, str, duration);
        } else {
            toast.setText(str);
            toast.setDuration(duration);
        }
        return toast;
    }

}
