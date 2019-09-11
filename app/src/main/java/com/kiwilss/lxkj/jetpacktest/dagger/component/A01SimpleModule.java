package com.kiwilss.lxkj.jetpacktest.dagger.component;

import com.kiwilss.lxkj.jetpacktest.dagger.A01SimpleActivity;
import dagger.Module;

/**
 * @author : Lss kiwilss
 * @FileName: A01SimpleModule
 * @e-mail : kiwilss@163.com
 * @time : 2019-08-19
 * @desc : {DESCRIPTION}
 */
@Module
public class A01SimpleModule {

    private A01SimpleActivity a01SimpleActivity;

    public A01SimpleModule(A01SimpleActivity a01SimpleActivity) {
        this.a01SimpleActivity = a01SimpleActivity;
    }
}
