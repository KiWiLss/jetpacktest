/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: SearchListAdapter
 * Author:   kiwilss
 * Date:     2019-06-30 19:06
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvvmtest.ui.search_list

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.coorchice.library.SuperTextView
import com.kiwilss.lxkj.ktx.core.gone
import com.kiwilss.lxkj.mvvmtest.R

import com.squareup.moshi.Json

/**
 *@FileName: SearchListAdapter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-30
 * @desc   : {DESCRIPTION}
 */
class SearchListAdapter (layoutId: Int = R.layout.item_fg_home)
    : BaseQuickAdapter<Article,BaseViewHolder>(layoutId){
    override fun convert(helper: BaseViewHolder?, item: Article?) {
        helper?.getView<SuperTextView>(R.id.stv_item_fg_home_tag)?.gone()

        //来源

        item?.let {
            //收藏图片显示
            helper?.setText(R.id.tv_item_fg_home_auth, item.author)
                ?.setText(R.id.tv_item_fg_home_title, item.title)
                ?.setText(R.id.tv_item_fg_home_memo, item.chapterName)
                ?.setText(R.id.tv_item_fg_home_date, item.niceDate)
                ?.setImageResource(R.id.iv_item_fg_home_collect,
                    if(item.collect)  (R.drawable.ic_like) else (R.drawable.ic_like_not) )
                ?.addOnClickListener(R.id.iv_item_fg_home_collect)
        }

    }
}



//文章
data class SearchListBean(
    @Json(name = "curPage") val curPage: Int,
    @Json(name = "datas") var datas: MutableList<Article>,
    @Json(name = "offset") val offset: Int,
    @Json(name = "over") val over: Boolean,
    @Json(name = "pageCount") val pageCount: Int,
    @Json(name = "size") val size: Int,
    @Json(name = "total") val total: Int
)

data class Article(
    @Json(name = "apkLink")
    val apkLink: String,
    @Json(name = "author")
    val author: String,
    @Json(name = "chapterId")
    val chapterId: Int,
    @Json(name = "chapterName")
    val chapterName: String,
    @Json(name = "collect")
    var collect: Boolean,
    @Json(name = "courseId")
    val courseId: Int,
    @Json(name = "desc")
    val desc: String,
    @Json(name = "envelopePic")
    val envelopePic: String,
    @Json(name = "fresh")
    val fresh: Boolean,
    @Json(name = "id")
    val id: Int,
    @Json(name = "link")
    val link: String,
    @Json(name = "niceDate")
    val niceDate: String,
    @Json(name = "origin")
    val origin: String,
    @Json(name = "prefix")
    val prefix: String,
    @Json(name = "projectLink")
    val projectLink: String,
    @Json(name = "publishTime")
    val publishTime: Long,
    @Json(name = "superChapterId")
    val superChapterId: Int,
    @Json(name = "superChapterName")
    val superChapterName: String,
    @Json(name = "tags")
    val tags: List<Any>,
    @Json(name = "title")
    val title: String,
    @Json(name = "type")
    val type: Int,
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "visible")
    val visible: Int,
    @Json(name = "zan")
    val zan: Int
)