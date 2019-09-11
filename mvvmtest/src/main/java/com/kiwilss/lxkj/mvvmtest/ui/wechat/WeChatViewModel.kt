/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: WeChatViewModel
 * Author:   kiwilss
 * Date:     2019-09-03 10:26
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.wechat

import androidx.lifecycle.MutableLiveData
import com.kiwilss.lxkj.mvvmtest.base.BaseViewModel
import com.kiwilss.lxkj.mvvmtest.ui.wechat.detail.WeChatFgDetailBean

/**
 *@FileName: WeChatViewModel
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-03
 * @desc   : {DESCRIPTION}
 */
class WeChatViewModel : BaseViewModel(){

        val mReponsitory by lazy { WeChatReponsitory() }

        val mTitleList = MutableLiveData<List<WeChatTitle>>()
        fun getTitleList(){
            handlerResult {
                val response = mReponsitory.getTitleList()
                executeResponse(response){
                    mTitleList.value = response.data
                }
            }
        }

    val mWeChatResult = MutableLiveData<WeChatFgDetailBean>()
        fun getWeChatDetail(page: Int, id: Int){
            handlerResult {
                val response = mReponsitory.getWeChatDetail(page,id)
                executeResponse(response){
                    mWeChatResult.value = response.data
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