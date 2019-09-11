/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: KnowledgeFragment
 * Author:   kiwilss
 * Date:     2019-09-02 15:29
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.knowledge

import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kiwilss.lxkj.mvvmtest.R
import com.kiwilss.lxkj.mvvmtest.base.PagerLazyFragment
import com.kiwilss.lxkj.mvvmtest.config.KEY_HOME_SCROLL_TOP
import com.kiwilss.lxkj.mvvmtest.ui.knowledge.detail.KnowledgeDetailActivity
import kotlinx.android.synthetic.main.fragment_knowledge.*

/**
 *@FileName: KnowledgeFragment
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-02
 * @desc   : {DESCRIPTION}
 */
class KnowledgeFragment: PagerLazyFragment<KnowledgeFgViewModel>()
    ,SwipeRefreshLayout.OnRefreshListener{


    override fun onRefresh() {
        mViewModel?.getKnowledge()
    }


    override fun getLayoutResId(): Int = R.layout.fragment_knowledge

    override fun startObserve() {
        mViewModel?.run {
            mKnowledgeResult.observe(this@KnowledgeFragment, Observer {
                dismissLoadingDiloag()
                srl_fg_knowledge_refresh.isRefreshing = false
                it?.run {
                    mAdapter.replaceData(this)
                }
            })
        }
        //监听滚动到顶
        LiveEventBus.get().with(KEY_HOME_SCROLL_TOP + "1",Boolean::class.java)
            .observe(this, Observer {
                if (it){
                    scrollToTop()
                }
            })
    }

    /**
     * 滚到顶
     */
    private fun scrollToTop(){
        rv_fg_knowledge_list?.run {
            val layoutManager: LinearLayoutManager = this.layoutManager as LinearLayoutManager
            if (layoutManager.findFirstVisibleItemPosition() > 20){
                scrollToPosition(0)//直接滚动到顶
            }else{
                smoothScrollToPosition(0)//模拟滚动到顶
            }
        }
    }

    override fun initData() {
        showLoadingDiloag()
        //mPresenter.getKnowledgeData()
        mViewModel?.getKnowledge()
    }

    private val mAdapter by lazy { KnowledgeFgAdapter() }

    override fun initInterface() {
        //刷新图标设置
        srl_fg_knowledge_refresh.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorPrimary))
        //刷新监听
        srl_fg_knowledge_refresh.setOnRefreshListener(this)

        //srl_collect_list_refresh.isEnabled = false
        initAdapter()

    }

    private fun initAdapter() {
        rv_fg_knowledge_list.run {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val knowledgeBean = mAdapter.data[position]
//            startActivity<KnowledgeListActivity>(bundle = arrayOf(
//                "title" to knowledgeBean.name
//            , "data" to knowledgeBean
//            ))
            Intent(context,KnowledgeDetailActivity::class.java).run {
                putExtra("title",knowledgeBean.name)
                putExtra("data",knowledgeBean)
                startActivity(this)
            }

        }
    }

    override fun providerVMClass(): Class<KnowledgeFgViewModel>? = KnowledgeFgViewModel::class.java


}