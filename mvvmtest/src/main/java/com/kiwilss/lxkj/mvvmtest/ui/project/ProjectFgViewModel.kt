/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: ProjectFgViewModel
 * Author:   kiwilss
 * Date:     2019-09-03 16:06
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.project

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.kiwilss.lxkj.mvvmtest.base.BaseViewModel
import com.kiwilss.lxkj.mvvmtest.ui.project.detail.ProjectListInfo

/**
 *@FileName: ProjectFgViewModel
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-03
 * @desc   : {DESCRIPTION}
 */
class ProjectFgViewModel : BaseViewModel(){

        val mReponsitory by lazy { ProjectFgReponsitory() }

    val mTitleList = MutableLiveData<List<ProjectFgBean>>()
    fun getTitleList(){
        handlerResult {
            val response = mReponsitory.getTitleData()
            LogUtils.e(response)
            executeResponse(response){
                mTitleList.value = response.data
            }
        }
    }


    val mDetailResult = MutableLiveData<ProjectListInfo>()
    fun getDetailList(page: Int, cid: Int){
        handlerResult {
            val response = mReponsitory.getProjectList(page, cid)
            LogUtils.e(response)
            executeResponse(response){
                mDetailResult.value = response.data
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