/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: KnowledgeDetailActivity
 * Author:   kiwilss
 * Date:     2019-09-02 16:38
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.knowledge.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.blankj.utilcode.util.LogUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kiwilss.lxkj.mvvmtest.R
import com.kiwilss.lxkj.mvvmtest.base.BaseActivity
import com.kiwilss.lxkj.mvvmtest.config.KEY_KNOWLEDGE_DETAIL
import com.kiwilss.lxkj.mvvmtest.ui.knowledge.KnowledgeBean
import com.kiwilss.lxkj.mvvmtest.ui.knowledge.KnowledgeFgViewModel
import kotlinx.android.synthetic.main.activity_knowledge_detail.*
import java.util.*

/**
 *@FileName: KnowledgeDetailActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-02
 * @desc   : {DESCRIPTION}
 */
class KnowledgeDetailActivity: BaseActivity<KnowledgeFgViewModel>(){
    override fun startObserve() {

    }

    override fun initData() {


    }

    override fun initOnClick() {
        //悬浮框点击
        fab_knowledge_detail_float.setOnClickListener {
            val currentItem = vp_knowledgedetail_vp.currentItem
            LogUtils.e("$currentItem-----------------")
            //尽当前显示的 fagment 界面滚动到顶部,其他不变
            //LiveDataBus.with<Boolean>(KEY_KNOWLEDGE_List_TOP+currentItem).setValue(true)
            LiveEventBus.get().with(KEY_KNOWLEDGE_DETAIL + currentItem).post(true)
        }

    }

    lateinit var mData: KnowledgeBean
    val mFragmentList = ArrayList<Fragment>()

    override fun initInterface(savedInstanceState: Bundle?) {
        tb_knoledgedetail_tb.run {
            title = intent.getStringExtra("title")
            setSupportActionBar(this)
            //设置可见返回图标
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            //设置返回图片可用
            //supportActionBar?.setHomeButtonEnabled(true)
            setNavigationOnClickListener {
                finish()
            }
        }
        //获取数据
        mData = intent.getSerializableExtra("data") as KnowledgeBean

        //初始化 fragment
        initFragment()

    }

    private fun initFragment() {
        val titleList = ArrayList<String>()
        mData.children.forEach {
            titleList.add(it.name)
        }
        for (i in 0 until titleList.size) {
            val children = mData.children[i]
            mFragmentList.add(KnowledgeDetailFragment.newInstence(children.id,i))
        }


        vp_knowledgedetail_vp.offscreenPageLimit = mFragmentList.size
        vp_knowledgedetail_vp.adapter = KnowledgeListAdapter(supportFragmentManager,mFragmentList,titleList)
        tl_knowledgedetail_tb.setupWithViewPager(vp_knowledgedetail_vp)

    }

    override fun getLayoutId(): Int = R.layout.activity_knowledge_detail

    override fun providerVMClass(): Class<KnowledgeFgViewModel>? = KnowledgeFgViewModel::class.java

}

class KnowledgeListAdapter(fm: FragmentManager, val data: List<Fragment>, val title: List<String>)
    : FragmentPagerAdapter(fm) {
    override fun getCount(): Int = data.size

    override fun getItem(p0: Int): Fragment = data[p0]

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }
}