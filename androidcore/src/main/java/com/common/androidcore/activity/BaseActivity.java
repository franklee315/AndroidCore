package com.common.androidcore.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.common.androidcore.BaseApplication;
import com.common.androidcore.R;
import com.common.androidcore.app.AppInfoUtil;
import com.common.androidcore.logger.Logger;
import com.common.androidcore.widget.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

/**
 * @author lifan 创建于 2013年11月13日 上午11:16:40
 * @version Ver 1.1 2014年2月4日 改订
 * @version Ver 1.2 2016年4月15日 改订
 *          Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 初始化View,子类必须实现此方法，setContentView需在此方法中先设置
     */
    public abstract void initView();

    /**
     * 初始化数据，子类必须实现此方法
     *
     * @return 是否继续加载页面
     */
    protected abstract boolean initData();

    /**
     * 获取activity的布局id
     *
     * @return activity布局ID
     */
    protected abstract int getActivityContentViewId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.setIsForeground(true);
        BaseApplication.setTopActivity(this);
        Logger.d("onCreate-activity:%s", BaseApplication.getTopActivity());
        setContentView(getActivityContentViewId());
        initView();
        if (!initData()) {
            ToastUtil.showToast(getContext(), R.string.no_data_tips);
            finish();
        }else{
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("onResume-activity:%s", BaseApplication.getTopActivity());
        BaseApplication.setTopActivity(this);
        if (!BaseApplication.isForeground()) {
            BaseApplication.setIsForeground(true);
        }
    }

    @Override
    protected void onPause() {
        BaseApplication.setIsForeground(AppInfoUtil.getInstance(getContext()).checkAppIsForeground());
        super.onPause();
        Logger.d("onPause-activity:%s", BaseApplication.getTopActivity());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 获取Application的Context
     *
     * @return Application的Context
     */
    public Context getContext() {
        return getApplication();
    }

    /**
     * 获取Activity的Context
     *
     * @return Activity的Context
     */
    public Context getSelfContext() {
        return this;
    }

    /**
     * 重载startActivity
     *
     * @param cls 需要跳转的activity的class
     */
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 重载startActivity
     *
     * @param cls 需要跳转的activity的class
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
     * @param cls 需要跳转的activity的class
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
}