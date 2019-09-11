/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: SearchListReponsitory
 * Author:   kiwilss
 * Date:     2019-08-30 17:05
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.search_list

import com.kiwilss.lxkj.mvvmtest.base.BaseRepository
import com.kiwilss.lxkj.mvvmtest.model.api.RetrofitHelper
import com.kiwilss.lxkj.mvvmtest.model.bean.BaseBean

/**
 *@FileName: SearchListReponsitory
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-30
 * @desc   : {DESCRIPTION}
 */
class SearchListReponsitory : BaseRepository() {

    suspend fun getData(page: Int, k: String): BaseBean<SearchListBean> {
        return apiCall { RetrofitHelper.apiService.getSearchList(page, k) }
    }


    suspend fun addCollect(id: Int): BaseBean<Any> =
        apiCall { RetrofitHelper.apiService.addCollect(id) }

    suspend fun cancelCollect(id: Int): BaseBean<Any> =
        apiCall { RetrofitHelper.apiService.cancelCollect(id) }

}