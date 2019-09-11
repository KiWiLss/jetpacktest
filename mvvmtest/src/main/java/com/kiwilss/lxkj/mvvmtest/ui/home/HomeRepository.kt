/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: HomeRepository
 * Author:   kiwilss
 * Date:     2019-08-27 11:27
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvvmtest.ui.home

import com.kiwilss.lxkj.mvvmtest.base.BaseRepository
import com.kiwilss.lxkj.mvvmtest.model.api.RetrofitHelper
import com.kiwilss.lxkj.mvvmtest.model.bean.BaseBean
import com.kiwilss.lxkj.mvvmtest.ui.home.fg.HomeBanner
import com.kiwilss.lxkj.mvvmtest.ui.home.fg.HomeBean

/**
 *@FileName: HomeRepository
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-27
 * @desc   : {DESCRIPTION}
 */
class HomeRepository: BaseRepository() {

    suspend fun getHomeFgBanner(): BaseBean<List<HomeBanner>>{
        return apiCall { RetrofitHelper.apiService.getBanner() }
    }

    suspend fun getHomeArticle(page: Int): BaseBean<HomeBean>
            = apiCall { RetrofitHelper.apiService.getHomeArticles(page) }

    suspend fun exit(): BaseBean<Any> = apiCall { RetrofitHelper.apiService.exit() }


    suspend fun collectArticle(id: Int): BaseBean<Any> = apiCall { RetrofitHelper.apiService.addCollect(id) }

    suspend fun cancelCollect(id: Int): BaseBean<Any> = apiCall { RetrofitHelper.apiService.cancelCollect(id) }


}