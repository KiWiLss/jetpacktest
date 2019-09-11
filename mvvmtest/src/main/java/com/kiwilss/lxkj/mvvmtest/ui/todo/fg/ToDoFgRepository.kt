/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: ToDoFgRepository
 * Author:   kiwilss
 * Date:     2019-08-29 18:07
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvvmtest.ui.todo.fg

import com.kiwilss.lxkj.mvvmtest.base.BaseRepository
import com.kiwilss.lxkj.mvvmtest.model.api.RetrofitHelper
import com.kiwilss.lxkj.mvvmtest.model.bean.BaseBean

/**
 *@FileName: ToDoFgRepository
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-29
 * @desc   : {DESCRIPTION}
 */
class ToDoFgRepository: BaseRepository() {


    suspend fun getListData(page: Int,map: MutableMap<String, Any>): BaseBean<ToDoFgBean>
        = apiCall { RetrofitHelper.apiService.getTodoList(page,map) }


    suspend fun addOneToDo(map: HashMap<String, Any>): BaseBean<Any>
            = apiCall { RetrofitHelper.apiService.addOneToDo(map) }


    suspend fun deleteOneToDo(id: Int): BaseBean<Any> = apiCall { RetrofitHelper.apiService.deleteOneToDo(id) }


    suspend fun updateOne(id: Int,status: Int): BaseBean<Any>
            = apiCall { RetrofitHelper.apiService.updateItemToDo(id, status) }



}