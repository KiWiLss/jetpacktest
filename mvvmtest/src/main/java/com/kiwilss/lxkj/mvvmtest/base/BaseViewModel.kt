/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: BaseViewModel
 * Author:   kiwilss
 * Date:     2019-08-22 18:13
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvvmtest.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.NetworkUtils
import com.coder.zzq.smartshow.toast.SmartToast
import com.just.agentweb.LogUtils
import com.kiwilss.lxkj.mvvmtest.model.bean.BaseBean
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 *@FileName: BaseViewModel
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-22
 * @desc   : {DESCRIPTION}
 */
abstract class BaseViewModel: ViewModel(),LifecycleObserver {

    val mException: MutableLiveData<Exception> = MutableLiveData()

    val error = MutableLiveData<String>()


    //统一处理请求结果
    fun handlerResult(api: suspend CoroutineScope.() -> Unit){
        viewModelScope.launch {
            try {
                api()
            }catch (e: Exception){//连接失败,判断网络
                LogUtils.e("MMM", ": $e")
                mException.value = e
                if (NetworkUtils.isAvailableByPing()) {//网络可以用
                    error.value = "未知异常"
                }else{//网络不可用
                    error.value = "网络异常"
                }
            }
        }

    }

    suspend fun <T>executeResponse(response: BaseBean<T>, isBean: Boolean = false,
                                   errorBlock:  suspend CoroutineScope.() -> Unit
                                   = { error.value = response.errorMsg },
                                   beanBlock: suspend CoroutineScope.() -> Unit = {},
                                   successBlock:  suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            if (isBean){
                beanBlock()
            }else{
                if (response.errorCode == 0){
                    successBlock()
                }else{
                    errorBlock()
                }
            }
        }
    }


}