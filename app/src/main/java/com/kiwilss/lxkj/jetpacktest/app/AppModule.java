package com.kiwilss.lxkj.jetpacktest.app;

import android.content.Context;
import android.content.SharedPreferences;
import dagger.Module;
import dagger.Provides;

/**
 * @author : Lss kiwilss
 * @FileName: AppModule
 * @e-mail : kiwilss@163.com
 * @time : 2019-08-20
 * @desc : {DESCRIPTION}
 */
@Module
public class AppModule {

    public MyApplication myApplication;

    public AppModule(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    @Provides
    SharedPreferences provideSharedPreferences(){
        return myApplication.getSharedPreferences("spfile", Context.MODE_PRIVATE);
    }

    @Provides
    public MyApplication getMyApplication(){
        return myApplication;
    }


}
