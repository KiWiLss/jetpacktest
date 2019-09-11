/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: WeChatFragment
 * Author:   kiwilss
 * Date:     2019-09-03 10:24
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.wechat

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kiwilss.lxkj.mvvmtest.R
import com.kiwilss.lxkj.mvvmtest.base.PagerLazyFragment
import com.kiwilss.lxkj.mvvmtest.config.KEY_HOME_SCROLL_TOP

import com.kiwilss.lxkj.mvvmtest.config.KEY_HOME_SCROLL_TOP_WECHAT
import com.kiwilss.lxkj.mvvmtest.ui.wechat.detail.WeChatFgDetailFg
import kotlinx.android.synthetic.main.fragment_wechat.*
import java.util.*

/**
 *@FileName: WeChatFragment
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-03
 * @desc   : {DESCRIPTION}
 */
class WeChatFragment : PagerLazyFragment<WeChatViewModel>() {


    override fun getLayoutResId(): Int = R.layout.fragment_wechat


    override fun startObserve() {
        mViewModel?.run {
            mTitleList.observe(this@WeChatFragment, Observer {
                dismissLoadingDiloag()
                it?.run {
                    initFragment(this)
                }
            })
        }
        //监听滚动到顶
        LiveEventBus.get().with(KEY_HOME_SCROLL_TOP + "2",Boolean::class.java)
            .observe(this, Observer {
                if (it){
                    LiveEventBus.get().with(KEY_HOME_SCROLL_TOP_WECHAT + vp_fg_wechat_vp.currentItem)
                        .post(true)
                }
            })
    }

    private fun initFragment(list: List<WeChatTitle>) {
        val dataFg = ArrayList<Fragment>()
        for (i in list.indices){
            val id = list[i].id
            val fg = WeChatFgDetailFg.newInstance(i, id)
            dataFg.add(fg)
        }
        vp_fg_wechat_vp.offscreenPageLimit = dataFg.size
        vp_fg_wechat_vp.adapter = fragmentManager?.let { WeChatFgAdapter(it,dataFg,list) }
        tl_fg_wechat_tb.setupWithViewPager(vp_fg_wechat_vp)
    }

    override fun initData() {
        showLoadingDiloag()
        mViewModel?.getTitleList()

    }

    override fun initInterface() {


    }

    override fun providerVMClass(): Class<WeChatViewModel>? = WeChatViewModel::class.java
}


class WeChatFgAdapter(fm: FragmentManager, val data: List<Fragment>, val title: List<WeChatTitle>):
    FragmentPagerAdapter(fm){
    override fun getItem(p0: Int): Fragment = data[p0]

    override fun getCount(): Int = data.size

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position].name
    }
}