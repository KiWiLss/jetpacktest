package com.kiwilss.lxkj.jetpacktest.ff;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import static com.kiwilss.lxkj.jetpacktest.ff.FFActivity.TAG;

/**
 * @author : Lss kiwilss
 * @FileName: FFView
 * @e-mail : kiwilss@163.com
 * @time : 2019-08-21
 * @desc : {DESCRIPTION}
 */
@SuppressLint("AppCompatCustomView")
public class FFView extends TextView {
    public FFView(Context context) {
        this(context,null);
    }

    public FFView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FFView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(TAG, "view------dispatchTouchEvent: " );
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "view---onTouchEvent: "+event.getAction() );
        return super.onTouchEvent(event);
    }
}
