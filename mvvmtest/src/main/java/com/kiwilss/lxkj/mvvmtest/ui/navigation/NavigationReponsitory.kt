/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: NavigationReponsitory
 * Author:   kiwilss
 * Date:     2019-09-03 14:54
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.navigation

import com.kiwilss.lxkj.mvvmtest.base.BaseRepository
import com.kiwilss.lxkj.mvvmtest.model.api.RetrofitHelper
import com.kiwilss.lxkj.mvvmtest.model.bean.BaseBean

/**
 *@FileName: NavigationReponsitory
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-03
 * @desc   : {DESCRIPTION}
 */
class NavigationReponsitory : BaseRepository(){

        suspend fun getData(): BaseBean<List<NavigationBean>>
            = apiCall { RetrofitHelper.apiService.getNaviList() }



}