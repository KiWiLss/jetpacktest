/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: ToDoFgViewModel
 * Author:   kiwilss
 * Date:     2019-08-29 16:16
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvvmtest.ui.todo.fg

import androidx.lifecycle.MutableLiveData
import com.kiwilss.lxkj.mvvmtest.base.BaseViewModel

/**
 *@FileName: ToDoFgViewModel
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-29
 * @desc   : {DESCRIPTION}
 */
class ToDoFgViewModel: BaseViewModel() {

    val mResponsitory = ToDoFgRepository()

    val mListResult = MutableLiveData<ToDoFgBean>()
    fun getToDoList(page: Int,map: MutableMap<String, Any>){
        handlerResult {
            val response = mResponsitory.getListData(page,map)
            executeResponse(response){
                mListResult.value = response.data
            }
        }
    }

    val deleteResult = MutableLiveData<Any>()
    fun deleteOneToDo(id: Int){
        handlerResult {
            val response = mResponsitory.deleteOneToDo(id)
            executeResponse(response){
                deleteResult.value = response.data
            }
        }
    }

    val updateResult = MutableLiveData<Any>()
    fun upageOneToDo(id: Int,states: Int){
        handlerResult {
            val response = mResponsitory.updateOne(id,states)
            executeResponse(response){
                updateResult.value = response.data
            }
        }
    }

}