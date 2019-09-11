/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: WebviewRepository
 * Author:   kiwilss
 * Date:     2019-08-27 18:41
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvvmtest.ui.webview

import com.kiwilss.lxkj.mvvmtest.base.BaseRepository
import com.kiwilss.lxkj.mvvmtest.model.api.RetrofitHelper
import com.kiwilss.lxkj.mvvmtest.model.bean.BaseBean

/**
 *@FileName: WebviewRepository
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-27
 * @desc   : {DESCRIPTION}
 */
class WebviewRepository: BaseRepository() {


    suspend fun collectArticle(id: Int): BaseBean<Any> = apiCall { RetrofitHelper.apiService.addCollect(id) }

    suspend fun cancelCollect(id: Int): BaseBean<Any> = apiCall { RetrofitHelper.apiService.cancelCollect(id) }


}