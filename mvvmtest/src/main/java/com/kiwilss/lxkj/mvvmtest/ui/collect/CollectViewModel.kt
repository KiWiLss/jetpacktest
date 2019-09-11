/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: CollectViewModel
 * Author:   kiwilss
 * Date:     2019-08-28 18:22
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.collect

import androidx.lifecycle.MutableLiveData
import com.kiwilss.lxkj.mvvmtest.base.BaseViewModel

/**
 *@FileName: CollectViewModel
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-28
 * @desc   : {DESCRIPTION}
 */
class CollectViewModel : BaseViewModel(){

    val mRepository = CollectRepository()

    val dataResult = MutableLiveData<CollectData>()
    fun getCollectData(page: Int){
        handlerResult {
            val result = mRepository.getData(page)
            executeResponse(result){
                dataResult.value = result.data
            }
        }
    }

    val cancelResult = MutableLiveData<Any>()
    fun cancelCollect(id: Int, originId: Int){
        handlerResult {
            val result = mRepository.cancelCollect(id, originId)
            executeResponse(result){
                cancelResult.value = result.data
            }
        }
    }

}