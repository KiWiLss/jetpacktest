package com.kiwilss.lxkj.mvvmtest.utils.recycerview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author : Lss kiwilss
 * @FileName: MyLinearLayout
 * @e-mail : kiwilss@163.com
 * @time : 2019-08-28
 * @desc : {DESCRIPTION}
 */
public class MyLinearLayout extends LinearLayoutManager {

    private boolean canScroll = true;

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 垂直方向
     * @return
     */
    @Override
    public boolean canScrollVertically() {
        return canScroll && super.canScrollVertically();
    }

    /**
     * 水平方向
     * @return
     */
    @Override
    public boolean canScrollHorizontally() {
        return super.canScrollHorizontally();
    }

    /**
     * 设置是否可以滑动
     * @param canScroll
     */
    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

}
