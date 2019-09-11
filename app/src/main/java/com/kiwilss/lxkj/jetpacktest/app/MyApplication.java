package com.kiwilss.lxkj.jetpacktest.app;

import android.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
            //依赖注入
            inject();

    }

    private void inject() {
        AppComponent component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        ComponentHolder.setAppComponent(component);
    }
}