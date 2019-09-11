package com.kiwilss.lxkj.jetpacktest.dagger;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.kiwilss.lxkj.jetpacktest.R;
import com.kiwilss.lxkj.jetpacktest.dagger.component.DaggerA01SimpleComponent;

import javax.inject.Inject;

/**
 * @author : Lss kiwilss
 * @FileName: A01SimpleActivity
 * @e-mail : kiwilss@163.com
 * @time : 2019-08-19
 * @desc : {DESCRIPTION}
 */
public class A01SimpleActivity extends AppCompatActivity {

        @Inject
        Student student;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a01simple);

        DaggerA01SimpleComponent.builder()
                //.a01SimpleModule(new A01SimpleModule(this))
                .build().inject(this);



        //ComponentHolder.getAppComponent().myApplication();
        //ComponentHolder.getAppComponent().sharedPreferences().getBoolean()

    }

    public void clickListener(View view) {
        Toast.makeText(this, student.toString(), Toast.LENGTH_SHORT).show();
    }
}
