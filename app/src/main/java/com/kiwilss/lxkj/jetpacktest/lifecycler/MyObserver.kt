/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: MyObserver
 * Author:   kiwilss
 * Date:     2019-08-15 16:01
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.jetpacktest.lifecycler

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent


/**
 *@FileName: MyObserver
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-15
 * @desc   : {DESCRIPTION}
 */
class MyObserver(var lifecycler: Lifecycle, var callback: Callback): LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public fun connectOnCreate(){
        p("connectOnCreate")
        //Log.e("MMM", ": connectOnCreate" )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public fun connectOnResume() {
        p("connectOnResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public fun disConnectOnDestroy() {
        p("disConnectOnDestroy")
    }

    fun p(string: String) {
        callback.update(string)
    }

}

interface Callback{
    fun update(string: String)
}