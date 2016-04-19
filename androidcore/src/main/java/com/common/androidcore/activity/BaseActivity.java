package com.common.androidcore.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.common.androidcore.BaseApplication;
import com.common.androidcore.R;
import com.common.androidcore.app.AppInfoUtil;
import com.common.androidcore.logger.Logger;
import com.common.androidcore.ui.ToastSingle;

import org.greenrobot.eventbus.EventBus;

/**
 * @author lifan 创建于 2013年11月13日 上午11:16:40
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
            ToastSingle.showToast(getContext(), R.string.no_data_tips);
            finish();
        } else {
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
        BaseApplication.setIsForeground(AppInfoUtil.getInstance().checkAppIsForeground());
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
        return this;
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


}