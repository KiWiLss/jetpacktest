package com.kiwilss.lxkj.jetpacktest.ch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.billy.android.swipe.SmartSwipe;
import com.billy.android.swipe.consumer.SpaceConsumer;
import com.kiwilss.lxkj.jetpacktest.R;

/**
 * @author : Lss kiwilss
 * @FileName: CHActivity
 * @e-mail : kiwilss@163.com
 * @time : 2019-08-21
 * @desc : {DESCRIPTION}
 */
public class CHActivity extends AppCompatActivity {

    private ScrollView mSv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch);

        mSv = (ScrollView) findViewById(R.id.sv_ch_sv);

        SmartSwipe.wrap(mSv)
                .addConsumer(new SpaceConsumer())
                .enableVertical(); //工作方向：纵向


    }

    /**仿 ios 弹性留白
     * @param view
     */
    public void spaceListener(View view) {
        Intent intent = new Intent(this, RvActivity.class);
        startActivity(intent);
    }
}
