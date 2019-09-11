/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: SisterBean
 * Author:   kiwilss
 * Date:     2019-09-05 14:56
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.sister
import android.widget.ImageView
import com.squareup.moshi.Json


/**
 *@FileName: SisterBean
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-05
 * @desc   : {DESCRIPTION}
 */


data class SisterBean(
    @Json(name = "error")
    val error: Boolean,
    @Json(name = "results")
    val results: List<Result>
)

data class Result(
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "desc")
    val desc: String,
    @Json(name = "_id")
    val id: String,
    @Json(name = "publishedAt")
    val publishedAt: String,
    @Json(name = "source")
    val source: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "used")
    val used: Boolean,
    @Json(name = "who")
    val who: String
//    @Json(name = "img")
//    var img: ImageView
)
data class Result2(
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "desc")
    val desc: String,
    @Json(name = "_id")
    val id: String,
    @Json(name = "publishedAt")
    val publishedAt: String,
    @Json(name = "source")
    val source: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "used")
    val used: Boolean,
    @Json(name = "who")
    val who: String,
    @Json(name = "img")
    var img: ImageView
)