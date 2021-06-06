/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: CollectAdapter
 * Author:   kiwilss
 * Date:     2019-08-28 18:26
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvvmtest.ui.collect

import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kiwilss.lxkj.ktx.core.gone
import com.kiwilss.lxkj.mvvmtest.R

/**
 *@FileName: CollectAdapter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-28
 * @desc   : {DESCRIPTION}
 */
class CollectAdapter(layoutId: Int = R.layout.item_fg_home):
    BaseQuickAdapter<CollectBean,BaseViewHolder>(layoutId) {
    override fun convert(helper: BaseViewHolder?, item: CollectBean?) {

        helper?.getView<View>(R.id.stv_item_fg_home_tag)?.gone()
        LogUtils.e(item!!.testMs)
        if (item!!.testMs == "0"){
            LogUtils.e("0000000")
        }else{
            LogUtils.e("111111")
        }
        //来源
        item?.let {
            //收藏图片显示
            helper?.setText(R.id.tv_item_fg_home_auth, item.author)
                ?.setText(R.id.tv_item_fg_home_title, item.title)
                ?.setText(R.id.tv_item_fg_home_memo, item.chapterName)
                ?.setText(R.id.tv_item_fg_home_date, item.niceDate)
                ?.setImageResource(R.id.iv_item_fg_home_collect,
                    R.drawable.ic_like )
                ?.addOnClickListener(R.id.iv_item_fg_home_collect)
        }

    }
}