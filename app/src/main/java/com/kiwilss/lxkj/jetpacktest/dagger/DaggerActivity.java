package com.kiwilss.lxkj.jetpacktest.dagger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.kiwilss.lxkj.jetpacktest.R;
import com.kiwilss.lxkj.jetpacktest.dagger.a02.A02Activity;

/**
 * @author : Lss kiwilss
 * @FileName: DaggerActivity
 * @e-mail : kiwilss@163.com
 * @time : 2019-08-19
 * @desc : {DESCRIPTION}
 */
public class DaggerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);


    }

    public void a01Listener(View view) {
        startActivity(new Intent(this,A01SimpleActivity.class));
    }

    public void a02Listener(View view) {
        startActivity(new Intent(this, A02Activity.class));
    }
}
