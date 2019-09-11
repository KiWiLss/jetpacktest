package com.kiwilss.lxkj.jetpacktest.app;

import android.content.SharedPreferences;
import dagger.Component;

/**
 * @author : Lss kiwilss
 * @FileName: AppComponent
 * @e-mail : kiwilss@163.com
 * @time : 2019-08-20
 * @desc : {DESCRIPTION}
 */
@Component(modules = AppModule.class)
public interface AppComponent {

    SharedPreferences sharedPreferences();

    MyApplication myApplication();

}
