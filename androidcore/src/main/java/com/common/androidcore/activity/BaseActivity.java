package com.common.androidcore.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @author lifan 创建于 2013年11月13日 上午11:16:40
 * @version Ver 1.1 2014年2月4日 改订
 *          Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 初始化View,子类必须实现此方法，setContentView需在此方法中先设置
     */
    public abstract void initView();

    /**
     * 初始化数据，子类必须实现此方法
     */
    public abstract void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initView();
        this.initData();
    }

    /**
     * 重载startActivity
     *
     * @param cls
     */
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 重载startActivity
     *
     * @param cls
     */
    public void startActivity(Class<?> cls, Object obj) {
        Intent intent = new Intent(this, cls);
        if (obj != null)
            intent.putExtra("data", (Serializable) obj);
        startActivity(intent);
    }

    /**
     * 重载startActivityForResult
     *
     * @param cls
     */
    public void startActivityForResult(Class<?> cls, Object obj, int requestCode) {
        Intent intent = new Intent(this, cls);
        if (obj != null)
            intent.putExtra("data", (Serializable) obj);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 创建桌面快捷方式
     *
     * @param resourceId 桌面icon
     * @param cls        点击打开的activity
     * @param title      标题
     */
    public void createShut(int resourceId, Class<?> cls, String title) {
        Intent addIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        Parcelable icon = Intent.ShortcutIconResource.fromContext(this, resourceId);

        Intent intent = new Intent(this, cls);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        sendBroadcast(addIntent);
    }

    /**
     * 隐藏View
     *
     * @param view 需要隐藏的View
     */
    public void hideView(View view) {
        view.setVisibility(View.GONE);
    }

    /**
     * 隐藏多个View
     *
     * @param views 需要隐藏的多个View
     */
    public void hideView(View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 显示View
     *
     * @param view 需要显示的View
     */
    public void showView(View view) {
        view.setVisibility(View.VISIBLE);
    }

    /**
     * 显示多个View
     *
     * @param views 需要显示的多个View
     */
    public void showView(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置点击事件
     *
     * @param listener 点击事件
     * @param views    需要设置事件的Views
     */
    public void setOnClickListener(View.OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

    /**
     * 隐藏输入面板
     *
     * @param editText 哪个EditText触发的
     */
    public void hideInput(EditText editText) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 检测SDCard是否插入
     *
     * @return 是否插入
     */
    public boolean isSDCardLoad() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * px值转dp值
     *
     * @param pxValue px值
     * @return dp值
     */
    public int px2dp(float pxValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * dp值转px值
     *
     * @param dpValue dp值
     * @return px 值
     */
    public int dp2px(float dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
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
            statusBarHeight = this.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 字符串排版半角转全角
     * @param input
     * @return
     */
    public  String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i< c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }if (c[i]> 65280&& c[i]< 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
    /**
     * 获得屏幕宽度
     *
     * @return 屏幕宽度
     */
    public int getWidth() {
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @return 屏幕高度
     */
    public int getHeight() {
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }
}
