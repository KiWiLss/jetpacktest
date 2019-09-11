/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: HomeFragment
 * Author:   kiwilss
 * Date:     2019-08-26 15:07
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvvmtest.ui.home.fg

import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kiwilss.lxkj.ktx.core.startActivity
import com.kiwilss.lxkj.mvpretrofit.config.Constant
import com.kiwilss.lxkj.mvpretrofit.config.LOADING_HINT
import com.kiwilss.lxkj.mvvmtest.R
import com.kiwilss.lxkj.mvvmtest.base.PagerLazyFragment
import com.kiwilss.lxkj.mvvmtest.config.KEY_HOME_SCROLL_TOP
import com.kiwilss.lxkj.mvvmtest.config.KEY_WEBVIEW_COLLECT_SUCCESS
import com.kiwilss.lxkj.mvvmtest.ui.home.HomeModel
import com.kiwilss.lxkj.mvvmtest.ui.login.LoginActivity
import com.kiwilss.lxkj.mvvmtest.ui.webview.WebviewActivity
import com.kiwilss.lxkj.mvvmtest.utils.GlideImageLoader
import com.kiwilss.lxkj.mvvmtest.utils.toastS
import com.lxj.androidktx.core.sp
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_home.*

/**
 *@FileName: HomeFragment
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-26
 * @desc   : {DESCRIPTION}
 */
class HomeFragment: PagerLazyFragment<HomeModel>(),SwipeRefreshLayout.OnRefreshListener
,BaseQuickAdapter.RequestLoadMoreListener{
    var isFirstBanner = true
    override fun onRefresh() {
        mCurrentPage = 0
        //获取轮播图数据
        //mViewModel?.getHomeBanner()
        //获取列表数据
        mViewModel?.getHomeArticle(mCurrentPage)
    }

    override fun onLoadMoreRequested() {
        if (mCurrentPage < mPageCount){
            mCurrentPage++
            mViewModel?.getHomeArticle(mCurrentPage)
        }else{
            mAdapter.loadMoreEnd()
        }

    }

    override fun providerVMClass(): Class<HomeModel>? = HomeModel::class.java

    override fun getLayoutResId(): Int = R.layout.fragment_home

    override fun startObserve() {

        mViewModel?.run {
            //轮播图数据获取
            bannerResult.observe(this@HomeFragment, Observer {
                dismissLoadingDiloag()
                mTitleList.clear()
                mImaglList.clear()
                mUrlList.clear()
                it?.run {
                    forEach {
                        mTitleList.add(it.title)
                        mImaglList.add(it.imagePath)
                        mUrlList.add(it.url)
                    }
                    banner!!.setImageLoader(GlideImageLoader())
                        .setImages(mImaglList)
                        .setBannerTitles(mTitleList)
                        .setIndicatorGravity(BannerConfig.RIGHT)
                        .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                        .setDelayTime(3000).setOnBannerListener {
                            //点击进入网页
                            startActivity<WebviewActivity>(bundle = arrayOf(
                                "url" to mUrlList[it],
                                "title" to mTitleList[it],
                                "id" to this[it].id
                            ))
                        }
                    if (isFirstBanner){
                        isFirstBanner = false
                        banner!!.start()
                    }

                }
            })
            //列表数据获取
            homeArticle.observe(this@HomeFragment, Observer {
                dismissLoadingDiloag()
               com.blankj.utilcode.util.LogUtils.e(it)
                it?.run {
                    mPageCount = pageCount
                    if (mCurrentPage == 0){
                        mAdapter.replaceData(datas)
                    }else{
                        mAdapter.addData(datas)
                    }
                }
                mAdapter.loadMoreComplete()
                srl_fg_home_refresh.isRefreshing = false
                mAdapter.setEnableLoadMore(true)
                //srl_fg_home_refresh.isEnabled = true
            })
            //加入收藏的结果
            collectResult.observe(this@HomeFragment, Observer {
                dismissLoadingDiloag()
                toastS("收藏成功")
                //修改列表界面
                val itemBean = mAdapter.data[mAddPosition]
                itemBean.collect = true
                mAdapter.setData(mAddPosition,itemBean)
            })
            //取消收藏的结果
            cancelCollectResult.observe(this@HomeFragment, Observer {
                dismissLoadingDiloag()
                toastS("取消成功")
                //修改列表界面
                val itemBean = mAdapter.data[mCancelPosition]
                itemBean.collect = false
                mAdapter.setData(mCancelPosition,itemBean)
            })
        }
        //监听在 webview中加入收藏
        LiveEventBus.get()
            .with(KEY_WEBVIEW_COLLECT_SUCCESS,Boolean::class.java)
            .observe(this, Observer {
                if (it){
                    //修改列表界面
                    val itemBean = mAdapter.data[mWebviewPosition]
                    itemBean.collect = true
                    mAdapter.setData(mWebviewPosition,itemBean)
                }
            })
        //监听滚动到顶
        LiveEventBus.get().with(KEY_HOME_SCROLL_TOP + "0",Boolean::class.java)
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
        rv_fg_home_list?.run {
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
        //获取轮播图数据
        mViewModel?.getHomeBanner()
        //获取列表数据
        mViewModel?.getHomeArticle(mCurrentPage)
    }

    val mAdapter by lazy { HomeFgAdapter() }
    var banner: Banner? = null
        val mTitleList  = mutableListOf<String>()
         val mUrlList = mutableListOf<String>()
         val mImaglList = mutableListOf<String>()

    var mCurrentPage = 0
    var mPageCount = 0

    var mAddPosition = 0
    var mCancelPosition = 0
    var mWebviewPosition = 0


    override fun initInterface() {
        initAdapter()
        //刷新图标设置
        srl_fg_home_refresh.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorAccent))
        //刷新监听
        srl_fg_home_refresh.setOnRefreshListener(this)

    }

    private fun initAdapter() {
        rv_fg_home_list.layoutManager = LinearLayoutManager(context)
        rv_fg_home_list?.run {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))
        }
        //头布局
        val headerView = layoutInflater.inflate(R.layout.header_fg_home, null)
        banner = headerView.findViewById<View>(R.id.banner_header_fg_home_banner) as Banner
        mAdapter.addHeaderView(headerView)
        mAdapter.run {
            setPreLoadNumber(5)
            setOnLoadMoreListener(this@HomeFragment,rv_fg_home_list)
            //点击进入网页
            setOnItemClickListener { adapter, view, position ->
                val data = mAdapter.data[position]
                mWebviewPosition = position
                startActivity<WebviewActivity>(bundle = arrayOf(
                    "url" to data.link,
                    "title" to data.title,
                    "id" to data.id
                ))
            }
            //收藏点击
            val function: (BaseQuickAdapter<Any, BaseViewHolder>, View, Int) -> Unit =
                { adapter, view, position ->
                   val isLogin =  sp().getBoolean(Constant.SP_IS_LOGIN, false)
                    if (isLogin){
                        //调用收藏,判断是否已经收藏
                        val homeData = mAdapter.data[position]
                        val id = homeData.id
                        showLoadingDiloag(LOADING_HINT)
                        if (homeData.collect){
                            //取消收藏
                            mCancelPosition = position
                            mViewModel?.cancelCollect(id)
                        }else{
                            //收藏
                            mAddPosition = position
                            mViewModel?.collectArtcile(id)
                        }
                    }else{
                        startActivity<LoginActivity>()
                    }
                }
            setOnItemChildClickListener(function)
        }
        banner!!.setImageLoader(GlideImageLoader())
    }

    override fun onStart() {
        super.onStart()
        banner?.run {
            startAutoPlay()
        }
    }

    override fun onStop() {
        super.onStop()
        banner?.run {
            stopAutoPlay()
        }
    }
}