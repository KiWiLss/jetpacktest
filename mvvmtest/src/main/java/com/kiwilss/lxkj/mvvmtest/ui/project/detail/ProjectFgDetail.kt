/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: ProjectFgDetail
 * Author:   kiwilss
 * Date:     2019-09-03 17:09
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.project.detail

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kiwilss.lxkj.ktx.core.startActivity
import com.kiwilss.lxkj.mvpretrofit.config.Constant
import com.kiwilss.lxkj.mvpretrofit.config.LOADING_HINT
import com.kiwilss.lxkj.mvvmtest.R
import com.kiwilss.lxkj.mvvmtest.base.PagerLazyFragment
import com.kiwilss.lxkj.mvvmtest.config.KEY_HOME_SCROLL_TOP_PROJECT
import com.kiwilss.lxkj.mvvmtest.ui.login.LoginActivity
import com.kiwilss.lxkj.mvvmtest.ui.project.ProjectFgViewModel
import com.kiwilss.lxkj.mvvmtest.ui.webview.WebviewActivity
import com.kiwilss.lxkj.mvvmtest.utils.scrollToTop
import com.kiwilss.lxkj.mvvmtest.utils.toastS
import com.lxj.androidktx.core.sp
import kotlinx.android.synthetic.main.fragment_project_detail.*

/**
 *@FileName: ProjectFgDetail
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-03
 * @desc   : {DESCRIPTION}
 */
class ProjectFgDetail : PagerLazyFragment<ProjectFgViewModel>()
    ,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{



    override fun onRefresh() {
        mCurrentPage = 0
        mViewModel?.getDetailList(mCurrentPage,mId)
    }

    override fun onLoadMoreRequested() {
        if (mCurrentPage < mPageCount){
            mCurrentPage++
            mViewModel?.getDetailList(mCurrentPage,mId)
        }else{
            mAdapter.loadMoreEnd(true)
        }
    }


    companion object{
        fun newInstance(type: Int,id: Int): ProjectFgDetail {
            val fragment = ProjectFgDetail()
            val bundle = Bundle()
            LogUtils.e("id=$id----type=$type")
            bundle.putInt("id",id)
            bundle.putInt("type",type)
            fragment.arguments = bundle
            return fragment
        }
    }



    override fun getLayoutResId(): Int = R.layout.fragment_project_detail

    override fun startObserve() {

        //监听滚动
        LiveEventBus.get().with(KEY_HOME_SCROLL_TOP_PROJECT +mType,Boolean::class.java)
            .observe(this, Observer {
                if (it){
                    rv_fg_project_detail_list.scrollToTop()
                }
            })

        mViewModel?.run {
            mDetailResult.observe(this@ProjectFgDetail, Observer {
               LogUtils.e(it)
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
                srl_fg_project_detail_refresh.isRefreshing = false
            })
            //add
            mAddResult.observe(this@ProjectFgDetail, Observer {
                dismissLoadingDiloag()
                toastS("已加入收藏")
                //处理界面
                val itemBean = mAdapter.data[mAddPosition]
                itemBean.collect = true
                mAdapter.setData(mAddPosition,itemBean)
            })
            //cancel
            mCancelResult.observe(this@ProjectFgDetail, Observer {
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
        mViewModel?.getDetailList(mCurrentPage,mId)
    }
    var mType = -1
    var mId: Int = 0

    var mCurrentPage = 0
    var mPageCount = 0
    val mAdapter by lazy { ProjectFgDetailAdapter() }
    var mAddPosition = 0
    var mCancelPosition = 0


    override fun initInterface() {

        arguments?.run {
            mId = getInt("id")
            mType = getInt("type")
        }

        LogUtils.e(mId)
//
        //刷新图标设置
        srl_fg_project_detail_refresh.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorPrimary))
        //刷新监听
        srl_fg_project_detail_refresh.setOnRefreshListener(this)

        initAdapter()

    }

    private fun initAdapter() {
        rv_fg_project_detail_list.run {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))
        }
        mAdapter.setPreLoadNumber(5)
        mAdapter.setOnLoadMoreListener(this,rv_fg_project_detail_list)
        //点击事件监听
        mAdapter.setOnItemClickListener { adapter, view, position ->
            //点击进入网页
            startActivity<WebviewActivity>(bundle = arrayOf(
                "url" to mAdapter.data[position].link,
                "title" to mAdapter.data[position].title,
                "id" to mAdapter.data[position].id
            ))
        }
        mAdapter.setOnLoadMoreListener(this,rv_fg_project_detail_list)
//        //收藏点击监听
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

    override fun providerVMClass(): Class<ProjectFgViewModel>? = ProjectFgViewModel::class.java
}