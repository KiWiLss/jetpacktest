/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: SearchListViewModel
 * Author:   kiwilss
 * Date:     2019-08-30 17:06
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.search_list

import androidx.lifecycle.MutableLiveData
import com.kiwilss.lxkj.mvvmtest.base.BaseViewModel

/**
 *@FileName: SearchListViewModel
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-30
 * @desc   : {DESCRIPTION}
 */
class SearchListViewModel : BaseViewModel(){

    val mReponsitory by lazy { SearchListReponsitory() }

    val mListResult = MutableLiveData<SearchListBean>()
    fun getData(page: Int, k: String){
        handlerResult {
            val response = mReponsitory.getData(page,k)
            executeResponse(response){
                mListResult.value = response.data
            }
        }
    }

    val mAddResult = MutableLiveData<Any>()
    fun addCollect(id: Int){
        handlerResult {
            val response = mReponsitory.addCollect(id)
            executeResponse(response){
                mAddResult.value = response.data
            }
        }
    }

    val mCancelResult = MutableLiveData<Any>()
    fun cancelCollect(id: Int){
        handlerResult {
            val response = mReponsitory.cancelCollect(id)
            executeResponse(response){
                mCancelResult.value = response.data
            }
        }
    }

}