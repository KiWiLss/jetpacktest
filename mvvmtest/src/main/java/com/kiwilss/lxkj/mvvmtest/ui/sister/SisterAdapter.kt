/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: SisterAdapter
 * Author:   kiwilss
 * Date:     2019-09-05 15:07
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.sister

import android.view.View
import android.widget.ImageView
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kiwilss.lxkj.mvvmtest.R
import com.kiwilss.lxkj.mvvmtest.utils.load

/**
 *@FileName: SisterAdapter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-05
 * @desc   : {DESCRIPTION}
 */
class SisterAdapter (layoutId: Int = R.layout.item_tucong2): BaseQuickAdapter<Result,BaseViewHolder>(layoutId){
    override fun convert(helper: BaseViewHolder?, item: Result?) {

        helper?.run {
            setText(R.id.tv_item_cutong_text,item!!.desc)

            val ivIcon: ImageView = getView<View>(R.id.iv_item_tucong_icon) as ImageView
            ivIcon.load(item.url)

            //item.img = ivIcon
            val activity = mContext as SisterActivity
            //activity.sparseArray.put(layoutPosition,ivIcon)
            LogUtils.e("-----adapter------$layoutPosition")

            addOnClickListener(R.id.iv_item_tucong_icon)
        }

    }

}