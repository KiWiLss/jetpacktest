/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: TuCongAdapter
 * Author:   kiwilss
 * Date:     2019-09-04 16:59
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.tucong

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kiwilss.lxkj.mvvmtest.utils.load


/**
 *@FileName: TuCongAdapter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-04
 * @desc   : {DESCRIPTION}
 */
class TuCongAdapter (layoutId: Int = com.kiwilss.lxkj.mvvmtest.R.layout.item_tucong):
    BaseQuickAdapter<Feed,BaseViewHolder>(layoutId) {
    override fun convert(helper: BaseViewHolder?, item: Feed?) {

        val urllist = arrayListOf<Any>()

        val entry = item?.entry
        var title: String? = null
        if (entry?.title.isNullOrEmpty()){
            title = "无标题"
        }else{
            title = entry?.title
        }
        helper?.setText(com.kiwilss.lxkj.mvvmtest.R.id.tv_item_cutong_text,title)

        val ivIcon: ImageView = helper?.getView<View>(com.kiwilss.lxkj.mvvmtest.R.id.iv_item_tucong_icon) as ImageView

        helper?.addOnClickListener(com.kiwilss.lxkj.mvvmtest.R.id.iv_item_tucong_icon)

        val imgList = entry?.images

        imgList?.let {
            if (!imgList.isEmpty()){
                val imagesBean = imgList[0]
                val url = "https://photo.tuchong.com/" + imagesBean.user_id + "/f/" + imagesBean.img_id + ".jpg"
              ivIcon.load(url)
            }
            for ((index,element) in it.withIndex()){
                val url = "https://photo.tuchong.com/" + element.user_id + "/f/" + element.img_id + ".jpg"
                urllist.add(url)
            }
        }


//        ivIcon.click {
//
//            XPopup.Builder(mContext)
//                .asImageViewer(ivIcon,helper?.adapterPosition,urllist,
//                    object : OnSrcViewUpdateListener{
//                        override fun onSrcViewUpdate(
//                            popupView: ImageViewerPopupView,
//                            position: Int
//                        ) {
//                            val rv = helper.itemView.parent as RecyclerView
//                            popupView.updateSrcView(rv.getChildAt(position)
//                                .findViewById(R.id.iv_item_tucong_icon) as ImageView)
//                        }
//
//                    },ImageLoader()).show()
//        }

    }
}