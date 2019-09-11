/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: ProjectFgReponsitory
 * Author:   kiwilss
 * Date:     2019-09-03 16:05
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.project

import com.kiwilss.lxkj.mvvmtest.base.BaseRepository
import com.kiwilss.lxkj.mvvmtest.model.api.RetrofitHelper
import com.kiwilss.lxkj.mvvmtest.model.bean.BaseBean
import com.kiwilss.lxkj.mvvmtest.ui.project.detail.ProjectListInfo

/**
 *@FileName: ProjectFgReponsitory
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-03
 * @desc   : {DESCRIPTION}
 */
class ProjectFgReponsitory : BaseRepository(){

        suspend fun getTitleData(): BaseBean<List<ProjectFgBean>>
    = apiCall { RetrofitHelper.apiService.getProjectData() }


    suspend fun getProjectList(page: Int,cid: Int): BaseBean<ProjectListInfo>
            = apiCall { RetrofitHelper.apiService.getProjectList(page,cid) }


    suspend fun addCollect(id: Int): BaseBean<Any> =
        apiCall { RetrofitHelper.apiService.addCollect(id) }

    suspend fun cancelCollect(id: Int): BaseBean<Any> =
        apiCall { RetrofitHelper.apiService.cancelCollect(id) }

}