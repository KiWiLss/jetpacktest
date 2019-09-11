/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: SearchRepository
 * Author:   kiwilss
 * Date:     2019-08-30 15:33
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.search

import com.kiwilss.lxkj.mvvmtest.base.BaseRepository
import com.kiwilss.lxkj.mvvmtest.model.api.RetrofitHelper
import com.kiwilss.lxkj.mvvmtest.model.bean.BaseBean

/**
 *@FileName: SearchRepository
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-30
 * @desc   : {DESCRIPTION}
 */
class SearchRepository : BaseRepository(){

    suspend fun getHotSearch(): BaseBean<List<SearchHotBean>>{
        return apiCall { RetrofitHelper.apiService.getSearchHot() }
    }


}