package com.kiwilss.lxkj.jetpacktest.qq;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kiwilss.lxkj.jetpacktest.R;

/**
 * @author : Lss kiwilss
 * @FileName: QQActivity
 * @e-mail : kiwilss@163.com
 * @time : 2019-08-20
 * @desc : {DESCRIPTION}
 */
public class QQActivity extends AppCompatActivity {

    private SlidingMenu mSmMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq);

        mSmMenu = (SlidingMenu) findViewById(R.id.sm_qq_menu);




    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mSmMenu.isOpen()){
            mSmMenu.closeMenu();
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
