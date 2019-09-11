package com.kiwilss.lxkj.jetpacktest.dagger.component;

import com.kiwilss.lxkj.jetpacktest.dagger.A01SimpleActivity;
import dagger.Component;

/**
 * @author : Lss kiwilss
 * @FileName: A01SimpleComponent
 * @e-mail : kiwilss@163.com
 * @time : 2019-08-19
 * @desc : {DESCRIPTION}
 */
@Component(modules = A01SimpleModule.class)
public interface A01SimpleComponent {

    void inject(A01SimpleActivity a01SimpleActivity);

}
