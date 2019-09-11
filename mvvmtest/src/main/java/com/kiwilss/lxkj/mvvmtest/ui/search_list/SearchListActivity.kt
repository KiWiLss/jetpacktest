/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: SearchListActivity
 * Author:   kiwilss
 * Date:     2019-08-30 17:06
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.search_list

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kiwilss.lxkj.ktx.core.click
import com.kiwilss.lxkj.ktx.core.startActivity
import com.kiwilss.lxkj.mvpretrofit.config.Constant
import com.kiwilss.lxkj.mvpretrofit.config.LOADING_HINT
import com.kiwilss.lxkj.mvvmtest.R
import com.kiwilss.lxkj.mvvmtest.base.BaseActivity
import com.kiwilss.lxkj.mvvmtest.ui.login.LoginActivity
import com.kiwilss.lxkj.mvvmtest.ui.webview.WebviewActivity
import com.kiwilss.lxkj.mvvmtest.utils.toastS
import com.lxj.androidktx.core.sp
import kotlinx.android.synthetic.main.activity_search_list.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 *@FileName: SearchListActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-30
 * @desc   : {DESCRIPTION}
 */
class SearchListActivity : BaseActivity<SearchListViewModel>()
,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{
    override fun onRefresh() {
        mCurrentPage = 0
        mViewModel?.getData(mCurrentPage,mTitle)
    }

    override fun onLoadMoreRequested() {
        if (mCurrentPage < mPageCount){
            mCurrentPage++
            mViewModel?.getData(mCurrentPage,mTitle)
        }else{
            mAdapter.loadMoreEnd(true)
        }
    }

    override fun startObserve() {
            mViewModel?.run {
                //列表结果
                mListResult.observe(this@SearchListActivity, Observer {
                    dismissLoadingDiloag()
                    LogUtils.e(it)
                    it?.run {
                        mPageCount = pageCount
                        if (mCurrentPage == 0){
                            mAdapter.replaceData(datas)
                        }else{
                            mAdapter.addData(datas)
                        }
                    }
                    mAdapter.loadMoreComplete()
                    mAdapter.setEnableLoadMore(true)
                    srl_search_list_refresh.isRefreshing = false
                })
                //加入收藏
                mAddResult.observe(this@SearchListActivity, Observer {
                    dismissLoadingDiloag()
                    LogUtils.e("cancel--$mAddPosition")
//            toast("已加入收藏")
                    toastS("已加入收藏")
                    //处理界面
                    val itemBean = mAdapter.data[mAddPosition]
                    itemBean.collect = true
                    mAdapter.setData(mAddPosition,itemBean)
                })
                //取消收藏
                mCancelResult.observe(this@SearchListActivity, Observer {
                    dismissLoadingDiloag()
                    LogUtils.e("cancel--$mCancelPosition")
                    //toast("已取消收藏")
                    toastS("已取消收藏")
                    //处理界面
                    val itemBean = mAdapter.data[mCancelPosition]
                    itemBean.collect = false
                    mAdapter.setData(mCancelPosition,itemBean)
                })
            }
    }

    override fun initData() {
        showLoadingDiloag()
       // mPresenter.getList(mCurrentPage,mTitle)
        mViewModel?.getData(mCurrentPage,mTitle)
    }

    override fun initOnClick() {

        //滚动到顶点击监听
        fab_search_list_scroll.click {
            scrollToTop()
        }
    }

    private fun scrollToTop() {
        rv_search_list_list.run {
            val layoutManager: LinearLayoutManager = this.layoutManager as LinearLayoutManager
            if (layoutManager.findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }

    lateinit var mTitle: String
    var mCurrentPage = 0
    var mPageCount = 0
    var mAddPosition = 0
    var mCancelPosition = 0
    val mAdapter by lazy { SearchListAdapter() }

    override fun initInterface(savedInstanceState: Bundle?) {

        //获取传入的搜索关键词
        mTitle = intent.getStringExtra("title")

        //刷新图标设置
        srl_search_list_refresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))
        //刷新监听
        srl_search_list_refresh.setOnRefreshListener(this)

        toolbar.run {
            title = mTitle
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                finish()
            }
        }

        initAdapter()

    }

    private fun initAdapter() {
        rv_search_list_list.run {
            layoutManager = LinearLayoutManager(this@SearchListActivity)
            adapter = mAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
        mAdapter.run {
            setPreLoadNumber(5)
            openLoadAnimation()
            setOnLoadMoreListener(this@SearchListActivity,rv_search_list_list)
            setOnItemClickListener { adapter, view, position ->
                //点击进入网页
                startActivity<WebviewActivity>(bundle = arrayOf(
                    "url" to mAdapter.data[position].link,
                    "title" to mAdapter.data[position].title,
                    "id" to mAdapter.data[position].id
                ))
            }
            //收藏点击
            setOnItemChildClickListener { adapter, view, position ->
                val isLogin = sp().getBoolean(Constant.SP_IS_LOGIN,false)
                if (isLogin){
                    //调用收藏,判断是否已经收藏
                    val homeData = mAdapter.data[position]
                    val id = homeData.id
                    showLoadingDiloag(LOADING_HINT)
                    if (homeData.collect) {//已经收藏
                        //调用取消收藏
                        mCancelPosition = position
                        mViewModel?.cancelCollect(id)
                        //mPresenter.cancelCollect(id)
                    }else{//没有收藏
                        mAddPosition = position
                        mViewModel?.addCollect(id)
                        //mPresenter.collectArticle(id)
                    }
                }else{
                    startActivity<LoginActivity>()
                }
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_search_list

    override fun providerVMClass(): Class<SearchListViewModel>? = SearchListViewModel::class.java


}