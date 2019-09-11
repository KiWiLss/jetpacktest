package com.kiwilss.lxkj.jetpacktest.dagger.a02;

import com.kiwilss.lxkj.jetpacktest.app.AppComponent;
import dagger.Component;

/**
 * @author : Lss kiwilss
 * @FileName: A02Component
 * @e-mail : kiwilss@163.com
 * @time : 2019-08-20
 * @desc : {DESCRIPTION}
 */
@Component(modules = A02Module.class, dependencies = AppComponent.class)
public interface A02Component {

    void inject(A02Activity activity);

}
