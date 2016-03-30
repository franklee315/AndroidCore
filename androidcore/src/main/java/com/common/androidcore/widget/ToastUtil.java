package com.common.androidcore.widget;

import android.content.Context;
import android.widget.Toast;

import com.common.androidcore.type.StringUtil;

/**
 * Created by lifan on 2014/3/21.
 * 显示Toast
 */
public class ToastUtil {
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
        if (!StringUtil.isEmpty(str)) {
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
