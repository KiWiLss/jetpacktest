/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: CollectRepository
 * Author:   kiwilss
 * Date:     2019-08-28 18:22
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.collect

import com.kiwilss.lxkj.mvvmtest.base.BaseRepository
import com.kiwilss.lxkj.mvvmtest.model.api.RetrofitHelper
import com.kiwilss.lxkj.mvvmtest.model.bean.BaseBean

/**
 *@FileName: CollectRepository
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-28
 * @desc   : {DESCRIPTION}
 */
class CollectRepository : BaseRepository(){

    suspend fun getData(page: Int): BaseBean<CollectData> =
        apiCall { RetrofitHelper.apiService.getCollectList(page) }

    suspend fun cancelCollect(id: Int, originId: Int): BaseBean<Any> =
        apiCall { RetrofitHelper.apiService.cancelCollect(id, originId) }

}