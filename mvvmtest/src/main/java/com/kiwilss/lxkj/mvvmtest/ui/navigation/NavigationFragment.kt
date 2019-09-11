/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: NavigationFragment
 * Author:   kiwilss
 * Date:     2019-09-03 14:52
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.navigation

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kiwilss.lxkj.mvvmtest.R
import com.kiwilss.lxkj.mvvmtest.base.PagerLazyFragment
import com.kiwilss.lxkj.mvvmtest.config.KEY_HOME_SCROLL_TOP
import com.kiwilss.lxkj.mvvmtest.utils.scrollToTop
import kotlinx.android.synthetic.main.fragment_navigation.*

/**
 *@FileName: NavigationFragment
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-03
 * @desc   : {DESCRIPTION}
 */
class NavigationFragment : PagerLazyFragment<NavigationViewModel>(){

    override fun getLayoutResId(): Int = R.layout.fragment_navigation

    override fun startObserve() {
        mViewModel?.run {
            mDataResult.observe(this@NavigationFragment, Observer {
                dismissLoadingDiloag()
                it?.run {
                    mAdapter.replaceData(this)
                }
            })
        }
        //监听滚动到顶
        LiveEventBus.get().with(KEY_HOME_SCROLL_TOP + "3",Boolean::class.java)
            .observe(this, Observer {
                if (it){
                    scrollToTop()
                }
            })

    }

    private fun scrollToTop() {
        rv_fg_navigation_list.scrollToTop()
    }

    override fun initData() {
        showLoadingDiloag()
        mViewModel?.getData()
    }

    val mAdapter by lazy { NavigationFgAdapter() }

    override fun initInterface() {

        initAdapter()


    }

    private fun initAdapter() {
        rv_fg_navigation_list.run {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        mAdapter.run {
            openLoadAnimation()
        }
    }

    override fun providerVMClass(): Class<NavigationViewModel>? = NavigationViewModel::class.java


}