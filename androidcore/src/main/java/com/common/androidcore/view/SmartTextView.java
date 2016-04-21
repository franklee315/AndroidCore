package com.common.androidcore.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by lifan on 2015/7/10.
 * 智能textview，可以根据文本控件自动显示和隐藏(gone)自身
 */
public class SmartTextView extends TextView {

    public SmartTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    public SmartTextView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public SmartTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

    public void setText(String str) {
        setText(str, true);
    }

    /**
     * 当文本为空时可以自动隐藏该视图
     *
     * @param str      文本
     * @param autoHide 是否自动隐藏
     */
    public void setText(String str, boolean autoHide) {
        if (autoHide) {
            if (TextUtils.isEmpty(str)) {
                setVisibility(View.GONE);
            } else {
                setVisibility(View.VISIBLE);
            }
        }
        super.setText(str);
    }
}
