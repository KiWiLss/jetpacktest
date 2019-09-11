/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: SearchViewModel
 * Author:   kiwilss
 * Date:     2019-08-30 15:32
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.search

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kiwilss.lxkj.mvpretrofit.config.Constant
import com.kiwilss.lxkj.mvvmtest.base.BaseViewModel
import com.kiwilss.lxkj.mvvmtest.config.KEY_SEARCH_HISTORY
import com.lxj.androidktx.core.sp

/**
 *@FileName: SearchViewModel
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-30
 * @desc   : {DESCRIPTION}
 */
class SearchViewModel : BaseViewModel(){

    val mRepository by lazy { SearchRepository() }

    val mHotResult = MutableLiveData<List<SearchHotBean>>()
        fun getHotData(){
            handlerResult {
                val response = mRepository.getHotSearch()
                executeResponse(response){
                    mHotResult.value = response.data
                }
            }
        }

    fun getHistroy(){
            val history = sp().getString(Constant.SP_SEARCH_HISTORY,null)
        LogUtils.e(history)
            history?.run {
                LogUtils.e("--1111---")
                if (history.isNotEmpty()){
                    LogUtils.e("--222---")
                    LiveEventBus.get().with(KEY_SEARCH_HISTORY).post(history)
                    //LiveDataBus.with<String>(KEY_SEARCH_HISTORY).postValue(history)
                }
            }
    }
}