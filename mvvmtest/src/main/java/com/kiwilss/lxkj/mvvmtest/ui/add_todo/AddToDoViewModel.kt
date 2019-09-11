/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: AddToDoViewModel
 * Author:   kiwilss
 * Date:     2019-08-30 14:19
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.add_todo

import androidx.lifecycle.MutableLiveData
import com.kiwilss.lxkj.mvvmtest.base.BaseViewModel

/**
 *@FileName: AddToDoViewModel
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-30
 * @desc   : {DESCRIPTION}
 */
class AddToDoViewModel : BaseViewModel(){

    val mRepository = AddToDoRepository()

    val mAddToDo = MutableLiveData<Any>()
    fun addToDo(map: HashMap<String,Any>){
        handlerResult {
            val response = mRepository.addOneToDo(map)
            executeResponse(response){
                mAddToDo.value = response.data
            }
        }
    }

    val mUpdateResult  = MutableLiveData<Any>()
    fun update(id: Int, map: HashMap<String,Any>){
        handlerResult {
            val response = mRepository.updateToDo(id,map)
            executeResponse(response){
                mUpdateResult.value = response.data
            }
        }
    }

}