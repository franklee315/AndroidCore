package com.common.androidcore.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class UIUtil {

    private UIUtil() {
        throw new AssertionError();
    }

    /**
     * 创建桌面快捷方式
     *
     * @param resourceId 桌面icon
     * @param cls        点击打开的activity
     * @param title      标题
     */
    public static void createShut(Activity context, int resourceId, Class<?> cls, String title) {
        Intent addIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        Parcelable icon = Intent.ShortcutIconResource.fromContext(context, resourceId);

        Intent intent = new Intent(context, cls);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        context.sendBroadcast(addIntent);
    }

    /**
     * 隐藏输入面板
     *
     * @param editText 哪个EditText触发的
     */
    public static void hideInput(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
