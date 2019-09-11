/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: CollectActivity
 * Author:   kiwilss
 * Date:     2019-08-28 18:23
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvvmtest.ui.collect

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kiwilss.lxkj.ktx.core.startActivity
import com.kiwilss.lxkj.mvpretrofit.config.LOADING_HINT
import com.kiwilss.lxkj.mvvmtest.R
import com.kiwilss.lxkj.mvvmtest.base.BaseActivity
import com.kiwilss.lxkj.mvvmtest.ui.webview.WebviewActivity
import com.kiwilss.lxkj.mvvmtest.utils.recycerview.MyLinearLayout
import kotlinx.android.synthetic.main.activity_collect_list.*

/**
 *@FileName: CollectActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-28
 * @desc   : {DESCRIPTION}
 */
class CollectActivity: BaseActivity<CollectViewModel>(),
SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{

    override fun onRefresh() {
        mCurrentPage = 0
        mViewModel?.getCollectData(mCurrentPage)
        mLayoutManager.setCanScroll(false)
    }

    override fun onLoadMoreRequested() {
        if (mCurrentPage < mPages){
            mCurrentPage++
            mViewModel?.getCollectData(mCurrentPage)
        }else{
            mAdapter.loadMoreEnd(true)
        }
    }

    override fun startObserve() {
        mViewModel?.run {
            dataResult.observe(this@CollectActivity, Observer {
                dismissLoadingDiloag()
                srl_collect_list_refresh.isRefreshing = false
                mLayoutManager.setCanScroll(true)
                mAdapter.loadMoreComplete()
                it?.run {
                    mPages = pageCount
                    if (mCurrentPage == 0){
                        mAdapter.replaceData(datas)
                    }else{
                        mAdapter.addData(datas)
                    }
                }
            })
            //取消结果
            cancelResult.observe(this@CollectActivity, Observer {
                dismissLoadingDiloag()
                mAdapter.data.removeAt(mCancelPos)
                mAdapter.notifyItemRemoved(mCancelPos)
            })
        }
    }

    override fun initData() {
        showLoadingDiloag()
        mViewModel?.getCollectData(mCurrentPage)
    }

    override fun initOnClick() {


    }

    val mAdapter by lazy { CollectAdapter() }

    var mCurrentPage = 0
    var mPages = 0

    val mLayoutManager by lazy { MyLinearLayout(this) }

    var mCancelPos = 0

    override fun initInterface(savedInstanceState: Bundle?) {

        rv_collect_list_list.run {
            layoutManager = mLayoutManager
            adapter = mAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        mAdapter.run {
            setPreLoadNumber(5)
            openLoadAnimation()
            setOnLoadMoreListener(this@CollectActivity,rv_collect_list_list)
            setOnItemClickListener { adapter, view, position ->
                val data = mAdapter.data[position]
                startActivity<WebviewActivity>(bundle = arrayOf(
                    "url" to data.link,
                    "title" to data.title,
                    "id" to data.id
                ))
            }
            setOnItemChildClickListener { adapter, view, position ->
                //点击取消收藏
                mCancelPos = position
                val collectBean = mAdapter.data[position]
                showLoadingDiloag(LOADING_HINT)
                mViewModel?.cancelCollect(collectBean.id,collectBean.originId)
                //mPresenter.cancelCollect(collectBean.id,collectBean.originId)
            }
        }

        //刷新图标设置
        srl_collect_list_refresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))
        //刷新监听
        srl_collect_list_refresh.setOnRefreshListener(this)


    }

    override fun getLayoutId(): Int = R.layout.activity_collect_list

    override fun providerVMClass(): Class<CollectViewModel>? = CollectViewModel::class.java
}