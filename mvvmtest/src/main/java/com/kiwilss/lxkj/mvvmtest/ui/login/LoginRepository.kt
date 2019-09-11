/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: LoginRepository
 * Author:   kiwilss
 * Date:     2019-08-28 11:52
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvvmtest.ui.login

import com.kiwilss.lxkj.mvvmtest.base.BaseRepository
import com.kiwilss.lxkj.mvvmtest.model.api.RetrofitHelper
import com.kiwilss.lxkj.mvvmtest.model.bean.BaseBean

/**
 *@FileName: LoginRepository
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-28
 * @desc   : {DESCRIPTION}
 */
class LoginRepository: BaseRepository() {

    suspend fun login(username: String, password: String): BaseBean<LoginInfo>{
        return apiCall { RetrofitHelper.apiService.login(username, password) }
    }

    suspend fun register(username: String, password: String): BaseBean<LoginInfo> =
        apiCall { RetrofitHelper.apiService.register(username,password,password) }
}