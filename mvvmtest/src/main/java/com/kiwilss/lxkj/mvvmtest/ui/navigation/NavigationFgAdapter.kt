/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: NavigationFgAdapter
 * Author:   kiwilss
 * Date:     2019-09-03 15:10
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.navigation

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kiwilss.lxkj.mvvmtest.R
import com.kiwilss.lxkj.mvvmtest.ui.webview.WebviewActivity
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

/**
 *@FileName: NavigationFgAdapter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-03
 * @desc   : {DESCRIPTION}
 */
class NavigationFgAdapter (layoutId: Int = R.layout.item_fg_navigation):
    BaseQuickAdapter<NavigationBean,BaseViewHolder>(layoutId){
    override fun convert(helper: BaseViewHolder?, item: NavigationBean?) {

        helper?.setText(R.id.tv_item_fg_navigation_title,item!!.name)


        val tagFlowLayout = helper?.getView<TagFlowLayout>(R.id.tfl_item_fg_navigation_flow)

        val artData = item?.articles
        if (artData.isNullOrEmpty()) return

        tagFlowLayout!!.adapter = NavigationTagAdapter(mContext,artData)

        //点击监听
            tagFlowLayout.setOnTagClickListener { view, position, parent ->
                Intent(mContext, WebviewActivity::class.java).run {
                    putExtra("url",artData[position].link)
                    putExtra("title",artData[position].title)
                    putExtra("id",artData[position].id)
                    mContext.startActivity(this)
                }
                true
            }


    }
}

class NavigationTagAdapter(val context: Context, val data: List<Article>):
    TagAdapter<Article>(data){
    override fun getView(parent: FlowLayout?, position: Int, t: Article?): View {

        val view = LayoutInflater.from(context).inflate(R.layout.item_fg_navigation_flow, null)

        val tv = view as TextView

        tv.text = t!!.title

        return view

    }

}