package com.kiwilss.lxkj.mvvmtest.ui.collect

import com.squareup.moshi.Json

data class CollectData(
    @Json(name = "curPage") val curPage: Int,
    @Json(name = "datas") val datas: List<CollectBean>,
    @Json(name = "offset") val offset: Int,
    @Json(name = "over") val over: Boolean,
    @Json(name = "pageCount") val pageCount: Int,
    @Json(name = "size") val size: Int,
    @Json(name = "total") val total: Int
)


data class CollectBean(
    @Json(name = "author")
    val author: String,
    @Json(name = "test")
    var testMs: String,
    @Json(name = "chapterId")
    val chapterId: Int,
    @Json(name = "chapterName")
    val chapterName: String,
    @Json(name = "courseId")
    val courseId: Int,
    @Json(name = "desc")
    val desc: String,
    @Json(name = "envelopePic")
    val envelopePic: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "link")
    val link: String,
    @Json(name = "niceDate")
    val niceDate: String,
    @Json(name = "origin")
    val origin: String,
    @Json(name = "originId")
    val originId: Int,
    @Json(name = "publishTime")
    val publishTime: Long,
    @Json(name = "title")
    val title: String,
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "visible")
    val visible: Int,
    @Json(name = "zan")
    val zan: Int
)