package com.kiwilss.lxkj.jetpacktest.qmui;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kiwilss.lxkj.jetpacktest.R;

/**
 * @author : Lss kiwilss
 * @FileName: QmUiActivity
 * @e-mail : kiwilss@163.com
 * @time : 2019-08-23
 * @desc : {DESCRIPTION}
 */
public class QmUiActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qmui);


    }

    public void buttonclick(View view) {
        //全屏显示
        //getWindow().setFlags(WindowManager.LayoutParams.);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void transclick(View view) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
}
