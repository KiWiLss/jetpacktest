package com.kiwilss.lxkj.jetpacktest.app;

/**
 * @author : Lss kiwilss
 * @FileName: ComponentHolder
 * @e-mail : kiwilss@163.com
 * @time : 2019-08-20
 * @desc : {DESCRIPTION}
 */
public class ComponentHolder {

    public static AppComponent appComponent;


    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static void setAppComponent(AppComponent appComponent) {
        ComponentHolder.appComponent = appComponent;
    }
}
