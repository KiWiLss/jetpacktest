package com.kiwilss.lxkj.zhihu;

import android.app.Application;


public class MyApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        //SkinManager.getInstance().init(this);
    }
}