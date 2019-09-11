package com.kiwilss.lxkj.jetpacktest.dagger.a02;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.kiwilss.lxkj.jetpacktest.R;
import com.kiwilss.lxkj.jetpacktest.app.ComponentHolder;
import com.kiwilss.lxkj.jetpacktest.dagger.Student;

import javax.inject.Inject;

/**
 * @author : Lss kiwilss
 * @FileName: A02Activity
 * @e-mail : kiwilss@163.com
 * @time : 2019-08-20
 * @desc : {DESCRIPTION}
 */
public class A02Activity extends AppCompatActivity {

    @Inject
    Student student;

    @Inject
    SharedPreferences sp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a02);

        DaggerA02Component.builder()
                .appComponent(ComponentHolder.getAppComponent())
                .a02Module(new A02Module(this))
                .build().inject(this);

        Log.i("MMM", "注入完毕，Student = " + student.toString());
        Log.i("MMM", "注入完毕，sp = " + sp.toString());

    }
}
