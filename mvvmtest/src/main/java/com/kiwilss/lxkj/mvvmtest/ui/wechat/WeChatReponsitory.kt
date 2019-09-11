/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: WeChatReponsitory
 * Author:   kiwilss
 * Date:     2019-09-03 10:25
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.wechat

import com.kiwilss.lxkj.mvvmtest.base.BaseRepository
import com.kiwilss.lxkj.mvvmtest.model.api.RetrofitHelper
import com.kiwilss.lxkj.mvvmtest.model.bean.BaseBean
import com.kiwilss.lxkj.mvvmtest.ui.wechat.detail.WeChatFgDetailBean

/**
 *@FileName: WeChatReponsitory
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-03
 * @desc   : {DESCRIPTION}
 */
class WeChatReponsitory : BaseRepository() {

    suspend fun getTitleList(): BaseBean<List<WeChatTitle>> {
        return apiCall { RetrofitHelper.apiService.getWeChatTitle() }
    }

    suspend fun getWeChatDetail(page: Int, id: Int): BaseBean<WeChatFgDetailBean> =
        apiCall { RetrofitHelper.apiService.getWeChatDetail(id, page) }


    suspend fun addCollect(id: Int): BaseBean<Any> =
        apiCall { RetrofitHelper.apiService.addCollect(id) }

    suspend fun cancelCollect(id: Int): BaseBean<Any> =
        apiCall { RetrofitHelper.apiService.cancelCollect(id) }


}