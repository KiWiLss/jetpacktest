/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: KnowledgeDetailFragment
 * Author:   kiwilss
 * Date:     2019-09-02 17:05
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.knowledge.detail

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kiwilss.lxkj.ktx.core.startActivity
import com.kiwilss.lxkj.mvpretrofit.config.Constant
import com.kiwilss.lxkj.mvpretrofit.config.LOADING_HINT
import com.kiwilss.lxkj.mvvmtest.R
import com.kiwilss.lxkj.mvvmtest.base.PagerLazyFragment
import com.kiwilss.lxkj.mvvmtest.config.KEY_KNOWLEDGE_DETAIL
import com.kiwilss.lxkj.mvvmtest.ui.knowledge.KnowledgeFgViewModel
import com.kiwilss.lxkj.mvvmtest.ui.login.LoginActivity
import com.kiwilss.lxkj.mvvmtest.ui.webview.WebviewActivity
import com.kiwilss.lxkj.mvvmtest.utils.toastS
import com.lxj.androidktx.core.sp
import kotlinx.android.synthetic.main.fragment_knowledge_detail.*

/**
 *@FileName: KnowledgeDetailFragment
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-02
 * @desc   : {DESCRIPTION}
 */
class KnowledgeDetailFragment : PagerLazyFragment<KnowledgeFgViewModel>()
    ,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{

    companion object{

        fun newInstence(cid: Int,type: Int): KnowledgeDetailFragment{
            val fragment = KnowledgeDetailFragment()
            val bundle = Bundle()
            bundle.putInt("cid",cid)
            bundle.putInt("type",type)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onRefresh() {
        mCurrentPage = 0
        mAdapter.setEnableLoadMore(false)
        if (mCid == 0) return
        mViewModel?.getKnowledgeList(mCurrentPage,mCid)
    }

    override fun onLoadMoreRequested() {
        if (mCurrentPage < mPageCount){
            if (mCid == 0) return
            mCurrentPage ++
            mViewModel?.getKnowledgeList(mCurrentPage,mCid)
            //mPresenter.getKnowledgeListData(mCurrentPage,mCid,mType)
            //getKnowledgeListData()
        }else{
            mAdapter.loadMoreEnd(true)
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_knowledge_detail


    /**
     * 滚到顶
     */
    fun scrollToTop(){
        rv_fg_knowledge_fg_list?.run {
            val layoutManager: LinearLayoutManager = this.layoutManager as LinearLayoutManager
            if (layoutManager.findFirstVisibleItemPosition() > 20){
                scrollToPosition(0)//直接滚动到顶
            }else{
                smoothScrollToPosition(0)//模拟滚动到顶
            }
        }
    }

    override fun startObserve() {
        //滚动到顶部点击监听
        LiveEventBus.get().with(KEY_KNOWLEDGE_DETAIL + mType,Boolean::class.java)
            .observe(this, Observer {
                if (it){
                    scrollToTop()
                }
            })

        mViewModel?.run {
            mKnowledgeListResult.observe(this@KnowledgeDetailFragment, Observer {
                it?.run {
                    mPageCount = pageCount
                    if (mCurrentPage == 0){
                        mAdapter.replaceData(datas)
                    }else{
                        mAdapter.addData(datas)
                    }
                }
                dismissLoadingDiloag()
                srl_fg_knowledge_fg_refresh.isRefreshing = false
                mAdapter.setEnableLoadMore(true)
                mAdapter.loadMoreComplete()
            })
            //加入收藏
            mAddResult.observe(this@KnowledgeDetailFragment, Observer {
                dismissLoadingDiloag()
                toastS("已加入收藏")
                //处理界面
                val itemBean = mAdapter.data[mAddPosition]
                itemBean.collect = true
                mAdapter.setData(mAddPosition,itemBean)
            })
            //取消收藏
            mCancelResult.observe(this@KnowledgeDetailFragment, Observer {
                dismissLoadingDiloag()
                toastS("已取消收藏")
                //处理界面
                val itemBean = mAdapter.data[mAddPosition]
                itemBean.collect = false
                mAdapter.setData(mCancelPosition,itemBean)
            })

        }


    }

    override fun initData() {
        if (mCid == 0) return
        showLoadingDiloag()
        //mPresenter.getKnowledgeListData(mCurrentPage,mCid,mType)
        mViewModel?.getKnowledgeList(mCurrentPage,mCid)
    }

    var mType = -1
    var mCid = 0
    var mCurrentPage = 0
    var mPageCount = 0
    val mAdapter by lazy { KnowledgeListFgAdapter() }
    var mAddPosition = 0
    var mCancelPosition = 0

    override fun initInterface() {

        arguments?.run {
            mCid = getInt("cid")
            mType = getInt("type")
        }
        //刷新图标设置
        srl_fg_knowledge_fg_refresh.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorPrimary))
        //刷新监听
        srl_fg_knowledge_fg_refresh.setOnRefreshListener(this)

        initAdapter()

    }

    private fun initAdapter() {
        rv_fg_knowledge_fg_list.run {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = mAdapter
        }
        mAdapter.setPreLoadNumber(5)
        mAdapter.setOnLoadMoreListener(this,rv_fg_knowledge_fg_list)

        //点击监听
        mAdapter.setOnItemClickListener { adapter, view, position ->
            //点击进入网页
            startActivity<WebviewActivity>(bundle = arrayOf(
                "url" to mAdapter.data[position].link,
                "title" to mAdapter.data[position].title,
                "id" to mAdapter.data[position].id
            ))
        }
        //收藏监听
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            val isLogin = context!!.sp().getBoolean(Constant.SP_IS_LOGIN,false)
            if (isLogin){
                //调用收藏,判断是否已经收藏
                val homeData = mAdapter.data[position]
                val id = homeData.id
                showLoadingDiloag(LOADING_HINT)
                if (homeData.collect) {//已经收藏
                    //调用取消收藏
                    mCancelPosition = position
                    mViewModel?.cancelCollect(id)
                    //mPresenter.cancelCollect(id, mType)
                }else{//没有收藏
                    mAddPosition = position
                    mViewModel?.addCollect(id)
                    //mPresenter.collectArticle(id , mType)
                }
            }else{
                startActivity<LoginActivity>()
            }
        }
    }



    override fun providerVMClass(): Class<KnowledgeFgViewModel>? = KnowledgeFgViewModel::class.java

}