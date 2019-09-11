/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: ProjectFragment
 * Author:   kiwilss
 * Date:     2019-09-03 16:09
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.project

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kiwilss.lxkj.mvvmtest.R
import com.kiwilss.lxkj.mvvmtest.base.PagerLazyFragment
import com.kiwilss.lxkj.mvvmtest.config.KEY_HOME_SCROLL_TOP
import com.kiwilss.lxkj.mvvmtest.config.KEY_HOME_SCROLL_TOP_PROJECT
import com.kiwilss.lxkj.mvvmtest.ui.project.detail.ProjectFgDetail
import kotlinx.android.synthetic.main.fragment_project.*
import java.util.*

/**
 *@FileName: ProjectFragment
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-03
 * @desc   : {DESCRIPTION}
 */
class ProjectFragment : PagerLazyFragment<ProjectFgViewModel>() {


    override fun getLayoutResId(): Int = R.layout.fragment_project

    override fun startObserve() {
        mViewModel?.run {
            mTitleList.observe(this@ProjectFragment, Observer {
                dismissLoadingDiloag()
                LogUtils.e(it)
                it?.run {
                    initFragment(this)
                }
            })
        }
        //监听滚动到顶
        LiveEventBus.get().with(KEY_HOME_SCROLL_TOP + "4",Boolean::class.java)
            .observe(this, Observer {
                if (it){
                    LiveEventBus.get().with(
                        KEY_HOME_SCROLL_TOP_PROJECT +
                            vp_fg_project_vp.currentItem)
                        .post(true)
                }
            })
    }

    private fun initFragment(list: List<ProjectFgBean>) {
        val dataFg = ArrayList<Fragment>()
        for (i in list.indices){
            val id = list[i].id
            val fg = ProjectFgDetail.newInstance(i, id)
            dataFg.add(fg)
        }
        LogUtils.e(dataFg.size)
        vp_fg_project_vp.offscreenPageLimit = dataFg.size

        //vp_fg_project_vp.adapter = ProjectFgAdapter(childFragmentManager,dataFg,list)
        vp_fg_project_vp.adapter = fragmentManager?.let { ProjectFgAdapter(it,dataFg,list) }
        tl_fg_project_tb.setupWithViewPager(vp_fg_project_vp)
    }

    override fun initData() {
        showLoadingDiloag()
        mViewModel?.getTitleList()

    }

    override fun initInterface() {



    }

    override fun providerVMClass(): Class<ProjectFgViewModel>? = ProjectFgViewModel::class.java
}

class ProjectFgAdapter(fm: FragmentManager, val data: List<Fragment>, val title: List<ProjectFgBean>):
    FragmentPagerAdapter(fm){
    override fun getItem(p0: Int): Fragment = data[p0]

    override fun getCount(): Int = data.size

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position].name
    }
}