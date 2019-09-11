/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: LoginViewModel
 * Author:   kiwilss
 * Date:     2019-08-28 11:46
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvvmtest.ui.login

import androidx.lifecycle.MutableLiveData
import com.kiwilss.lxkj.mvvmtest.base.BaseViewModel

/**
 *@FileName: LoginViewModel
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-28
 * @desc   : {DESCRIPTION}
 */
class LoginViewModel: BaseViewModel() {

    val loginRepository by lazy { LoginRepository() }

    val loginResult = MutableLiveData<LoginInfo>()
    val registerResult = MutableLiveData<LoginInfo>()

    fun login(username: String, password: String){
        handlerResult {
            val login = loginRepository.login(username, password)
            executeResponse(login){
                loginResult.value = login.data
            }
        }
    }

    fun register(username: String, password: String){
        handlerResult {
            val register = loginRepository.register(username, password)
            executeResponse(register){
                registerResult.value = register.data
            }
        }
    }

}