/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: WebviewModel
 * Author:   kiwilss
 * Date:     2019-08-27 18:41
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvvmtest.ui.webview

import androidx.lifecycle.MutableLiveData
import com.kiwilss.lxkj.mvvmtest.base.BaseViewModel

/**
 *@FileName: WebviewModel
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-27
 * @desc   : {DESCRIPTION}
 */
class WebviewModel: BaseViewModel() {

    val repository = WebviewRepository()

    val collectResult = MutableLiveData<Any>()
    fun collectArtcile(id: Int){
        handlerResult {
            val result = repository.collectArticle(id)
            executeResponse(result){
                collectResult.value = result.data
            }
        }
    }

//    val cancelCollectResult = MutableLiveData<Any>()
//    fun cancelCollect(id: Int){
//        handlerResult {
//            val result = repository.cancelCollect(id)
//            executeResponse(result){
//                cancelCollectResult.value = result.data
//            }
//        }
//    }
}