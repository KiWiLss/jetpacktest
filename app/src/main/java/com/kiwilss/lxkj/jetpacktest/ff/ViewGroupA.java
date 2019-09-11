package com.kiwilss.lxkj.jetpacktest.ff;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class ViewGroupA extends LinearLayout {

    public ViewGroupA(Context context) {
         super(context);
    }
    public ViewGroupA(Context context, AttributeSet attrs) {
        super(context, attrs);
     }
    public ViewGroupA(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.d("MMM","ViewGroupA dispatchTouchEvent");
       return super.dispatchTouchEvent(ev);
    }

     @Override
     public boolean onInterceptTouchEvent(MotionEvent ev) {

      Log.d("MMM","ViewGroupA onInterceptTouchEvent");
         return super.onInterceptTouchEvent(ev);
    }

     @Override
     public boolean onTouchEvent(MotionEvent event) {
         Log.d("MMM","ViewGroupA onTouchEvent");
        return super.onTouchEvent(event);
     }
 }