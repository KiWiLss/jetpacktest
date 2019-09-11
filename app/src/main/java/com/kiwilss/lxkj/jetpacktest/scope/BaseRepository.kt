package com.kiwilss.lxkj.jetpacktest.scope

import com.kiwilss.lxkj.jetpacktest.utils.BaseBean

open class BaseRepository {

    suspend fun <T : Any> apiCall(call: suspend () -> BaseBean<T>): BaseBean<T> {
        return call.invoke()
    }


}