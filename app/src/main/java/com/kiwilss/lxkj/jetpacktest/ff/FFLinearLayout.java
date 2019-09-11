package com.kiwilss.lxkj.jetpacktest.ff;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import static com.kiwilss.lxkj.jetpacktest.ff.FFActivity.TAG;

/**
 * @author : Lss kiwilss
 * @FileName: FFLinearLayout
 * @e-mail : kiwilss@163.com
 * @time : 2019-08-21
 * @desc : {DESCRIPTION}
 */
public class FFLinearLayout extends LinearLayout {
    public FFLinearLayout(Context context) {
        this(context,null);
    }

    public FFLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FFLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "viewgroup-----dispatchTouchEvent: " );
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG, "viewgroup--------onInterceptTouchEvent: " );
        //return super.onInterceptTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "viewgroup------onTouchEvent: " );
        return super.onTouchEvent(event);
    }
}
