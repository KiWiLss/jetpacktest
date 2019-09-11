/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: HomeModel
 * Author:   kiwilss
 * Date:     2019-08-25 21:04
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvvmtest.ui.home

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.kiwilss.lxkj.mvvmtest.base.BaseViewModel
import com.kiwilss.lxkj.mvvmtest.ui.home.fg.HomeBanner
import com.kiwilss.lxkj.mvvmtest.ui.home.fg.HomeBean

/**
 *@FileName: HomeModel
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-25
 * @desc   : {DESCRIPTION}
 */
class HomeModel: BaseViewModel() {

     private val bannerRepository by lazy { HomeRepository() }

    //----------------activity------------------

    val exitResult = MutableLiveData<Any>()
    fun exit(){
        handlerResult {
            val exit = bannerRepository.exit()
            LogUtils.e("exit---$exit")
            executeResponse(exit){
                exitResult.value = exit.data
            }
        }
    }

    val collectResult = MutableLiveData<Any>()
    fun collectArtcile(id: Int){
        handlerResult {
            val result = bannerRepository.collectArticle(id)
            executeResponse(result){
                collectResult.value = result.data
            }
        }
    }

    val cancelCollectResult = MutableLiveData<Any>()
    fun cancelCollect(id: Int){
        handlerResult {
            val result = bannerRepository.cancelCollect(id)
            executeResponse(result){
                cancelCollectResult.value = result.data
            }
        }
    }

    //----------------fragment------------------

    val bannerResult = MutableLiveData<List<HomeBanner>>()
    fun getHomeBanner(){
        handlerResult {
            val homeFgBanner = bannerRepository.getHomeFgBanner()
            executeResponse(homeFgBanner){
                bannerResult.value = homeFgBanner.data
            }
        }
    }

    val homeArticle = MutableLiveData<HomeBean>()
    fun getHomeArticle(page: Int){
        handlerResult {
            val homeArticleBean = bannerRepository.getHomeArticle(page)
            executeResponse(homeArticleBean){
                homeArticle.value = homeArticleBean.data
            }
        }
    }



}