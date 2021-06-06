package com.kiwilss.lxkj.zhihu

import android.app.Application
import com.kiwilss.lxkj.okhttp.OkhttpConfig


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //SkinManager.getInstance().init(this);
        OkhttpConfig.init(this)
    }
}