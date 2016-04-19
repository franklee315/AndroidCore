package com.common.androidcore.ui;

/**
 * Created by Shawn on 2015/7/10.
 */
public class AppBarLayoutMath {
    /**
     * onOffsetChanged中用于切换Toolbar中控件因白色导致的看不见
     *  在i从0到x时，导出1-0-1算法
     * @param i  onOffsetChanged参数
     * @param half AppBarLayoutMath中可滑动的高度的一半
     * @return  1-0-1
     */
    public static int countTheAlpha(int i, int half) {
        if (i >= half) {
            return  (int) (255*(1-(float) i / half) );
        } else {
            return (int) (255*((float) i / half-1));
        }
    }
}
