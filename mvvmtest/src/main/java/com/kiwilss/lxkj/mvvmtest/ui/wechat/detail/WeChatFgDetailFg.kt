/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: WeChatFgDetailFg
 * Author:   kiwilss
 * Date:     2019-09-03 13:59
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.wechat.detail

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
import com.kiwilss.lxkj.mvvmtest.config.KEY_HOME_SCROLL_TOP_WECHAT
import com.kiwilss.lxkj.mvvmtest.ui.login.LoginActivity
import com.kiwilss.lxkj.mvvmtest.ui.webview.WebviewActivity
import com.kiwilss.lxkj.mvvmtest.ui.wechat.WeChatViewModel
import com.kiwilss.lxkj.mvvmtest.utils.toastS
import com.lxj.androidktx.core.sp
import kotlinx.android.synthetic.main.fragment_wechat_detail_fg.*

/**
 *@FileName: WeChatFgDetailFg
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-03
 * @desc   : {DESCRIPTION}
 */
class WeChatFgDetailFg : PagerLazyFragment<WeChatViewModel>()
    ,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{
    override fun onRefresh() {
        mCurrentPage = 0
        mAdapter.setEnableLoadMore(false)
        mViewModel?.getWeChatDetail(mCurrentPage,mId)
    }

    override fun onLoadMoreRequested() {
        if (mCurrentPage < mPageCount){
            mCurrentPage++
            mViewModel?.getWeChatDetail(mCurrentPage,mId)
        }else{
            mAdapter.loadMoreEnd(true)
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_wechat_detail_fg

    companion object{
        fun newInstance(type: Int,id: Int): WeChatFgDetailFg{
            val fragment = WeChatFgDetailFg()
            val bundle = Bundle()
            bundle.putInt("id",id)
            bundle.putInt("type",type)
            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * 滚到顶
     */
    fun scrollToTop(){
        rv_fg_wechat_fg_list?.run {
            val layoutManager: LinearLayoutManager = this.layoutManager as LinearLayoutManager
            if (layoutManager.findFirstVisibleItemPosition() > 20){
                scrollToPosition(0)//直接滚动到顶
            }else{
                smoothScrollToPosition(0)//模拟滚动到顶
            }
        }
    }

    override fun startObserve() {
        //监听滚动
        LiveEventBus.get().with(KEY_HOME_SCROLL_TOP_WECHAT +mType,Boolean::class.java)
            .observe(this, Observer {
                if (it){
                    scrollToTop()
                }
            })
        mViewModel?.run {
            //数据列表
            mWeChatResult.observe(this@WeChatFgDetailFg, Observer {
                it?.run {
                    mPageCount = pageCount
                    if (mCurrentPage == 0){
                        mAdapter.replaceData(datas)
                    }else{
                        mAdapter.addData(datas)
                    }
                }
                dismissLoadingDiloag()
                mAdapter.loadMoreComplete()
                mAdapter.setEnableLoadMore(true)
                srl_fg_wechat_fg_refresh.isRefreshing = false
            })
            //加入收藏
            mAddResult.observe(this@WeChatFgDetailFg, Observer {
                dismissLoadingDiloag()
                toastS("已加入收藏")
                //处理界面
                val itemBean = mAdapter.data[mAddPosition]
                itemBean.collect = true
                mAdapter.setData(mAddPosition,itemBean)
            })
            //取消收藏
            mCancelResult.observe(this@WeChatFgDetailFg, Observer {
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
        showLoadingDiloag()
        mViewModel?.getWeChatDetail(mCurrentPage,mId)

    }

    var mId: Int = 0
    var mType: Int = -1
    var mCurrentPage = 0
    var mPageCount = 0
    val mAdapter by lazy { WeChatFgDetailFgAdapter() }
    var mAddPosition = 0
    var mCancelPosition = 0

    override fun initInterface() {
        arguments?.run {
            mId = getInt("id")
            mType = getInt("type")
        }

        //刷新图标设置
        srl_fg_wechat_fg_refresh.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorPrimary))
        //刷新监听
        srl_fg_wechat_fg_refresh.setOnRefreshListener(this)

        initAdapter()

    }

    private fun initAdapter() {
        rv_fg_wechat_fg_list.run {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
        mAdapter.setPreLoadNumber(5)
        mAdapter.setOnLoadMoreListener(this,rv_fg_wechat_fg_list)
        //点击事件监听
        mAdapter.setOnItemClickListener { adapter, view, position ->
            //点击进入网页
            startActivity<WebviewActivity>(bundle = arrayOf(
                "url" to mAdapter.data[position].link,
                "title" to mAdapter.data[position].title,
                "id" to mAdapter.data[position].id
            ))
        }
        //收藏点击监听
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

    override fun providerVMClass(): Class<WeChatViewModel>? = WeChatViewModel::class.java


}