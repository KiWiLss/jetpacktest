package com.kiwilss.lxkj.jetpacktest.scope

import com.kiwilss.lxkj.jetpacktest.utils.BaseBean
import com.kiwilss.lxkj.jetpacktest.utils.LoginInfo
import com.kiwilss.lxkj.jetpacktest.utils.RetrofitHelper

class LoginRepository : BaseRepository() {

    suspend fun login(userName: String, passWord: String): BaseBean<LoginInfo> {
        return apiCall { RetrofitHelper.apiService.login(userName, passWord) }
    }

    suspend fun exit(): BaseBean<Any> = apiCall { RetrofitHelper.apiService.exit() }

}