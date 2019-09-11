/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: AboutActivity
 * Author:   kiwilss
 * Date:     2019-09-06 12:01
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.about

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.gyf.barlibrary.ImmersionBar
import com.kiwilss.lxkj.ktx.core.click
import com.kiwilss.lxkj.mvvmtest.R
import com.kiwilss.lxkj.mvvmtest.base.BaseActivity
import com.kiwilss.lxkj.mvvmtest.ui.home.HomeModel
import com.kiwilss.lxkj.mvvmtest.utils.browse
import kotlinx.android.synthetic.main.activity_about.*

/**
 *@FileName: AboutActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-06
 * @desc   : {DESCRIPTION}
 */
class AboutActivity : BaseActivity<HomeModel>() {
    override fun startObserve() {

    }

    override fun setStatusBar() {
        super.setStatusBar()
        ImmersionBar.with(this)
            .fullScreen(true)
            .statusBarColor(R.color.blue)
            .statusBarDarkFont(false, 0f)
            .init()

//                ImmersionBar.with(this)
//            .transparentBar()
//            .statusBarColor(R.color.blue)
//            .statusBarDarkFont(false, 0f)
//            .init()
    }

    override fun onDestroy() {
        ImmersionBar.with(this).destroy()
        super.onDestroy()
    }

    override fun initData() {
    }

    override fun initOnClick() {
        tvMyGithub.click {
            browse(getString(R.string.github_my))
        }
        tvdownload.click {
            browse("https://www.pgyer.com/b3S1")
        }
        tvGank.click {
            browse("http://gank.io/")
        }
        tvOpenFrame.click {
            browse("https://github.com/KiWiLss/WanAndroid")
        }
    }

    override fun initInterface(savedInstanceState: Bundle?) {

        //初始化 toolbar
        tb_about_tb.run {
            title = R.string.about_us.toString()
                setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                finish()
            }
        }
        //设置CollapsingToolbarLayout扩张时的标题颜色
//        collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.translate))
//        //设置CollapsingToolbarLayout收缩时的标题颜色
//        collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.gank_text1_color_night))
        ctl_about_ctl.run {
            setExpandedTitleColor(resources.getColor(R.color.translate))
            setCollapsedTitleTextColor(ContextCompat.getColor(context,R.color.white))
        }

    }

    override fun getLayoutId(): Int = R.layout.activity_about

    override fun providerVMClass(): Class<HomeModel>? = HomeModel::class.java
}