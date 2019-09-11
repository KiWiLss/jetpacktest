/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: AddToDoRepository
 * Author:   kiwilss
 * Date:     2019-08-30 14:18
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.add_todo

import com.kiwilss.lxkj.mvvmtest.base.BaseRepository
import com.kiwilss.lxkj.mvvmtest.model.api.RetrofitHelper
import com.kiwilss.lxkj.mvvmtest.model.bean.BaseBean

/**
 *@FileName: AddToDoRepository
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-30
 * @desc   : {DESCRIPTION}
 */
class AddToDoRepository : BaseRepository(){

        suspend fun addOneToDo(map: HashMap<String,Any>): BaseBean<Any>{
            return apiCall { RetrofitHelper.apiService.addOneToDo(map) }
        }

    suspend fun updateToDo(id: Int, map: HashMap<String,Any>): BaseBean<Any>
            = apiCall { RetrofitHelper.apiService.updateOneToDo(id,map) }


}