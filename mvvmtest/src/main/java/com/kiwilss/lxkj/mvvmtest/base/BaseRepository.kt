package com.kiwilss.lxkj.mvvmtest.base

import com.kiwilss.lxkj.mvvmtest.model.bean.BaseBean

/**
 * Created by luyao
 * on 2019/4/10 9:41
 */
open class BaseRepository {

    suspend fun <T : Any> apiCall(call: suspend () -> BaseBean<T>): BaseBean<T> {
        return call.invoke()
    }


}