package com.kiwilss.lxkj.jetpacktest.dagger.a02;

import com.kiwilss.lxkj.jetpacktest.dagger.Student;
import dagger.Module;
import dagger.Provides;

/**
 * @author : Lss kiwilss
 * @FileName: A02Module
 * @e-mail : kiwilss@163.com
 * @time : 2019-08-20
 * @desc : {DESCRIPTION}
 */
@Module
public class A02Module {

    private A02Activity a02Activity;

    public A02Module(A02Activity a02Activity) {
        this.a02Activity = a02Activity;
    }

    @Provides
    public Student providerStudent(){
        return new Student();
    }
}
