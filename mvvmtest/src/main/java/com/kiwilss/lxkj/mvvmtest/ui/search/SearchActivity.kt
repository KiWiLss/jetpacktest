/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: SearchActivity
 * Author:   kiwilss
 * Date:     2019-08-30 15:34
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.search

import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.SearchView

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSON
import com.blankj.utilcode.util.LogUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kiwilss.lxkj.ktx.core.click
import com.kiwilss.lxkj.ktx.core.startActivity
import com.kiwilss.lxkj.mvpretrofit.config.Constant
import com.kiwilss.lxkj.mvvmtest.R
import com.kiwilss.lxkj.mvvmtest.base.BaseActivity
import com.kiwilss.lxkj.mvvmtest.config.KEY_SEARCH_HISTORY
import com.kiwilss.lxkj.mvvmtest.ui.search_list.SearchListActivity
import com.lxj.androidktx.core.putString
import com.lxj.androidktx.core.sp
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

/**
 *@FileName: SearchActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-30
 * @desc   : {DESCRIPTION}
 */
class SearchActivity : BaseActivity<SearchViewModel>(){
    override fun startObserve() {
        //监听获取搜索热词
        getSearchHotListener()

        //获取搜索历史
        getSearchHistoryListener()
    }

    private fun getSearchHistoryListener() {
        LiveEventBus.get().with(KEY_SEARCH_HISTORY, String::class.java)
            .observeSticky(this,androidx.lifecycle.Observer {
                dismissLoadingDiloag()
                LogUtils.e(it)
                it?.run {
                    LogUtils.e(this)
                    val list: List<String> = JSON.parseArray(this,String::class.java)
                    //list.reversed()
                    LogUtils.e(list)
                    mAdapter.replaceData(list)
                }
            })


    }

    private fun getSearchHotListener() {
        mViewModel?.run {
            mHotResult.observe(this@SearchActivity,androidx.lifecycle.Observer {
                dismissLoadingDiloag()
                //设置搜索热词
                it?.run {
                    mSearchHotList.addAll(this)
                    tfl_search_hot.adapter = SearchHotAdapter(this)
                }
            })
        }

    }


    override fun initData() {

        //获取搜索热词
        showLoadingDiloag()
        mViewModel?.getHotData()

        mViewModel?.getHistroy()
//        window.decorView.postDelayed({
//
//        },500)

        //mPresenter.getSearchHot()
        //获取历史记录
        //mPresenter.getSearchHistory(this)


    }

    override fun initOnClick() {


    }

    val mSearchHotList = ArrayList<SearchHotBean>()
    val mAdapter by lazy { SearchHistoryAdapter() }

    override fun initInterface(savedInstanceState: Bundle?) {

        toolbar.run {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                finish()
            }
        }
        //热门搜索点击事件
        tfl_search_hot.setOnTagClickListener { view, position, parent ->
            if (mSearchHotList.isNotEmpty()){
                val searchHotBean = mSearchHotList[position]
                goToSearchList(searchHotBean.name)
            }
            true
        }

        initAdapter()

        //清空点击
        tv_search_clear.click {
            mAdapter.data.clear()
            mAdapter.notifyDataSetChanged()
            sp().putString(Constant.SP_SEARCH_HISTORY,"")
        }
    }

    private fun initAdapter() {
        rv_search_search.run {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        mAdapter.run {
            bindToRecyclerView(rv_search_search)
            setEmptyView(R.layout.search_empty_view)
            setOnItemChildClickListener { adapter, view, position ->
                when(view.id){
                    R.id.iv_item_search_history_clear -> {
                        //清楚当前的搜索历史数据,更新数据库
                        mAdapter.data.removeAt(position)
                        sp().putString(Constant.SP_SEARCH_HISTORY, JSON.toJSONString(mAdapter.data))
                        mAdapter.notifyItemRemoved(position)
                    }
                    R.id.tv_item_search_history_key -> {
                        goToSearchList(mAdapter.data[position])
                    }
                }
            }
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as android.widget.SearchView
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.onActionViewExpanded()
        searchView.queryHint = getString(R.string.search_tint)
        searchView.setOnQueryTextListener(queryTextListener)
        searchView.isSubmitButtonEnabled = true
        try {
            val field = searchView.javaClass.getDeclaredField("mGoButton")
            field.isAccessible = true
            val mGoButton = field.get(searchView) as ImageView
            mGoButton.setImageResource(R.drawable.ic_search_white_24dp)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return super.onCreateOptionsMenu(menu)
    }

    /**
     * OnQueryTextListener
     */
    private val queryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            goToSearchList(query.toString())
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    }

    private fun goToSearchList(key: String) {
        //记录搜索词,存入数据库
        val list = mAdapter.data
        if (!list.contains(key)){
            list.add(0,key)
        }
        if (list.size > 20){
            list.removeAt(list.size - 1)
        }
        sp().putString(Constant.SP_SEARCH_HISTORY,JSON.toJSONString(list))
        //进入搜索列表界面
        startActivity<SearchListActivity>(bundle = arrayOf(
            "title" to key
        ))
        mAdapter.replaceData(list)
    }

    override fun getLayoutId(): Int = R.layout.activity_search

    override fun providerVMClass(): Class<SearchViewModel>? = SearchViewModel::class.java


}