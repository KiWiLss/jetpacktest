/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: NavigationViewModel
 * Author:   kiwilss
 * Date:     2019-09-03 14:55
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.navigation

import androidx.lifecycle.MutableLiveData
import com.kiwilss.lxkj.mvvmtest.base.BaseViewModel

/**
 *@FileName: NavigationViewModel
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-03
 * @desc   : {DESCRIPTION}
 */
class NavigationViewModel : BaseViewModel(){

        val mReponsitory  by lazy { NavigationReponsitory() }

    val mDataResult = MutableLiveData<List<NavigationBean>>()

    fun getData(){
        handlerResult {
            val response = mReponsitory.getData()
            executeResponse(response){
                mDataResult.value = response.data
            }
        }
    }

}