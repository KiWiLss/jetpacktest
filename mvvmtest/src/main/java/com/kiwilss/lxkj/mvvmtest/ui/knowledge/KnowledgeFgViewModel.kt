/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: KnowledgeFgViewModel
 * Author:   kiwilss
 * Date:     2019-09-02 15:31
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.knowledge

import androidx.lifecycle.MutableLiveData
import com.kiwilss.lxkj.mvvmtest.base.BaseViewModel
import com.kiwilss.lxkj.mvvmtest.ui.knowledge.detail.KnowledgeListFgBean

/**
 *@FileName: KnowledgeFgViewModel
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-02
 * @desc   : {DESCRIPTION}
 */
class KnowledgeFgViewModel : BaseViewModel(){

        val mReponsitory by lazy { KnowledgeFgReponsitory() }

    //knowledgeFragment
    val mKnowledgeResult = MutableLiveData<List<KnowledgeBean>>()
        fun getKnowledge(){
            handlerResult {
                val response = mReponsitory.getKnowledge()
                executeResponse(response){
                    mKnowledgeResult.value = response.data
                }
            }
        }

    //knowledgeDetailActivity

    //knowledgeDetailFragment
    val mKnowledgeListResult = MutableLiveData<KnowledgeListFgBean>()
    fun getKnowledgeList(page: Int, cid: Int){
        handlerResult {
            val response = mReponsitory.getKnowledgeList(page,cid)
            executeResponse(response){
                mKnowledgeListResult.value = response.data
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