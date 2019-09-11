/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: KnowledgeFgReponsitory
 * Author:   kiwilss
 * Date:     2019-09-02 15:31
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.knowledge

import com.kiwilss.lxkj.mvvmtest.base.BaseRepository
import com.kiwilss.lxkj.mvvmtest.model.api.RetrofitHelper
import com.kiwilss.lxkj.mvvmtest.model.bean.BaseBean
import com.kiwilss.lxkj.mvvmtest.ui.knowledge.detail.KnowledgeListFgBean

/**
 *@FileName: KnowledgeFgReponsitory
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-02
 * @desc   : {DESCRIPTION}
 */
class KnowledgeFgReponsitory : BaseRepository(){

    //knoledgeFragment
        suspend fun getKnowledge(): BaseBean<List<KnowledgeBean>> =
             apiCall { RetrofitHelper.apiService.getKnowledge() }

    //knowledgeDetailActivity

    //knowledgedetailfragment
    suspend fun getKnowledgeList(page: Int, cid: Int): BaseBean<KnowledgeListFgBean>
    = apiCall { RetrofitHelper.apiService.getKnowledgeList(page,cid) }


    suspend fun addCollect(id: Int): BaseBean<Any> =
        apiCall { RetrofitHelper.apiService.addCollect(id) }

    suspend fun cancelCollect(id: Int): BaseBean<Any> =
        apiCall { RetrofitHelper.apiService.cancelCollect(id) }

}