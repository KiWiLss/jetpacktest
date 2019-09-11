package com.kiwilss.lxkj.jetpacktest.ff;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.kiwilss.lxkj.jetpacktest.R;

/**
 * @author : Lss kiwilss
 * @FileName: FFActivity
 * @e-mail : kiwilss@163.com
 * @time : 2019-08-21
 * @desc : {DESCRIPTION}
 */
public class FFActivity extends AppCompatActivity {

    public static final String TAG = "MMM";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ff);



    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "activity------dispatchTouchEvent: " );
        return super.dispatchTouchEvent(ev);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "activity------onTouchEvent: " );
        return super.onTouchEvent(event);
    }

    public void ffClick(View view) {
        Log.e(TAG, "ffClick: " );
    }
}
