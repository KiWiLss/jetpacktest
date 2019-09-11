/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: ToDoFragment
 * Author:   kiwilss
 * Date:     2019-08-29 16:15
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvvmtest.ui.todo.fg

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kiwilss.lxkj.ktx.core.startActivity
import com.kiwilss.lxkj.mvpretrofit.config.LOADING_HINT
import com.kiwilss.lxkj.mvvmtest.R
import com.kiwilss.lxkj.mvvmtest.base.PagerLazyFragment
import com.kiwilss.lxkj.mvvmtest.config.*
import com.kiwilss.lxkj.mvvmtest.ui.add_todo.AddToDoActivity
import com.kiwilss.lxkj.mvvmtest.ui.todo.ToDoActivity
import com.kiwilss.lxkj.mvvmtest.widget.SwipeItemLayout
import kotlinx.android.synthetic.main.fragment_todo.*

/**
 *@FileName: ToDoFragment
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-29
 * @desc   : {DESCRIPTION}
 */
class ToDoFragment: PagerLazyFragment<ToDoFgViewModel>()
,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{
    override fun onRefresh() {
        mCurrent = 1
        val map = mutableMapOf<String, Any>()
        val ac = activity as ToDoActivity
        map["status"] = mStatus
        map["type"] = ac.mType
        LogUtils.e("page=$mCurrent---$mStatus-----${ac.mType}")
        mViewModel?.getToDoList(mCurrent,map)
    }

    override fun onLoadMoreRequested() {
        if (mCurrent < mPageCount){
            mCurrent++
            val map = mutableMapOf<String, Any>()
            val ac = activity as ToDoActivity
            map["status"] = mStatus
            map["type"] = ac.mType
            mViewModel?.getToDoList(mCurrent,map)
        }else{
            mAdapter.loadMoreEnd(true)
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_todo


    companion object {
        fun newInstance(type: Int): ToDoFragment {
            val fg = ToDoFragment()
            val bundle = Bundle()
            bundle.putInt("status", type)
            fg.arguments = bundle
            return fg
        }
    }

    override fun startObserve() {
        //新增的 todo 只能是待办的
        if (mStatus == 0){//待办界面
            LiveEventBus.get().with(KEY_ADD_TODO_ADD,Boolean::class.java)
                .observe(this, Observer {
                    if (it){
                        onRefresh()
                    }
                })
        }
        //刷新 todo 的监听
        LiveEventBus.get().with(KEY_ADD_TODO_UPDATE + mStatus.toString(),Boolean::class.java)
            .observe(this, Observer {
                if (it){
                    onRefresh()
                }
            })
        //监听右上角切换刷新界面
        LiveEventBus.get().with(KEY_TODO_REFRESH,Boolean::class.java)
            .observe(this, Observer {
                LogUtils.e("key todo refresh--$it")
                if (it){
                    onRefresh()
                }
            })
        //监听是否刷新另一个 fragment
        LiveEventBus.get().with(KEY_TODO_REFRESH_ZERO,Boolean::class.java)
            .observe(this, Observer {
                onRefresh()
            })
        LiveEventBus.get().with(KEY_TODO_REFRESH_ONE,Boolean::class.java)
            .observe(this, Observer {
                onRefresh()
            })
        //获取列表数据
        mViewModel?.run {
            mListResult.observe(this@ToDoFragment, Observer {
                LogUtils.e(it)
                it?.run {
                    mPageCount = pageCount

                    if (mCurrent == 1) {
                        mData.clear()
                    }
                    var time = ""
                    for (i in 0 until datas.size) {
                        val data = datas[i]
                        //处理时间
                        if (time != data.dateStr) {
                            time = data.dateStr
                            val dataBean = TodoDataBean(true, time)
                            mData.add(dataBean)
                        }
                        val dataBean = TodoDataBean(data)
                        mData.add(dataBean)
                    }
                }
                dismissLoadingDiloag()
                mAdapter.notifyDataSetChanged()
                mAdapter.loadMoreComplete()
                srl_fg_todo_refresh.isRefreshing = false
                mAdapter.setEnableLoadMore(true)
            })
            //删除监听
            deleteResult.observe(this@ToDoFragment, Observer {
                dismissLoadingDiloag()
                mData.removeAt(mDeletePos)
                mAdapter.notifyItemRemoved(mDeletePos)
            })
            //更新状态监听
            updateResult.observe(this@ToDoFragment, Observer {
                //删除当前 fragment,通知另一个 fragment 新增
                dismissLoadingDiloag()
                //删掉这条数据,同时更新另一 fragment 数据
                mData.removeAt(mDonePos)
                mAdapter.notifyItemRemoved(mDonePos)
                if (mStatus == 0){//待办
                    //LiveDataBus.with<Boolean>(KEY_TODO_FG_REFRESH + "1").setValue(true)
                    LiveEventBus.get().with(KEY_TODO_REFRESH_ONE).post(true)
                }else if (mStatus == 1){//完成
                    //LiveDataBus.with<Boolean>(KEY_TODO_FG_REFRESH + "0").setValue(true)
                    LiveEventBus.get().with(KEY_TODO_REFRESH_ZERO).post(true)
                }
            })
        }
    }

    override fun initData() {
        LogUtils.e(mStatus)
        showLoadingDiloag()
        val map = mutableMapOf<String, Any>()
        val ac = activity as ToDoActivity
        map["status"] = mStatus
        map["type"] = ac.mType
        mViewModel?.getToDoList(mCurrent,map)
        //mPresenter.getToDoList(mCurrent, map, mStatus)
    }

    var mStatus = -1 //status: 0 // 0为未完成，1为完成
    var mCurrent = 1//当前页码
    var mPageCount = 0 //总的页码
    private val mData = mutableListOf<TodoDataBean>()

    val mAdapter by lazy { ToDoFgAdapter(R.layout.item_fg_todo, R.layout.item_fg_todo_header, mData) }

    var mDeletePos = 0
    var mDonePos = 0



    override fun initInterface() {

        arguments?.run {
            mStatus = getInt("status")
            //LogUtils.e(mStatus)
        }
        //刷新图标设置
        srl_fg_todo_refresh.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorPrimary))
        //刷新监听
        srl_fg_todo_refresh.setOnRefreshListener(this)

        initAdapter()

    }

    private fun initAdapter() {
        rv_fg_todo_list?.run {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            addOnItemTouchListener(SwipeItemLayout.OnSwipeItemTouchListener(activity))
        }
        //加空布局
        val emptyView = layoutInflater.inflate(R.layout.layout_empty_view, null)

        mAdapter.run {
            setEmptyView(emptyView)
            //mAdapter.emptyView = emptyView
            setPreLoadNumber(5)
            openLoadAnimation()
            //点击监听
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.btn_item_fg_todo_delete -> {//删除
                        showLoadingDiloag(LOADING_HINT)
                        mDeletePos = position
                        mViewModel?.deleteOneToDo(mAdapter.data[position].t.id)
                        //mPresenter.deleteOneToDo(mAdapter.data[position].t.id,mStatus)
                    }
                    R.id.btn_item_fg_todo_done -> {//复原或完成
                        val t = mAdapter.data[position].t
                        val status = t.status//状态， 1-完成；0未完成; 默认全部展示
                        showLoadingDiloag(LOADING_HINT)
                        mDonePos = position
                        var ss = 0
                        if (status == 1){//复原
                            ss = 0
                        }else if (status == 0){//点击变为完成
                            ss = 1
                        }
                        mViewModel?.upageOneToDo(t.id,ss)
                        //mPresenter.updateOneToDo(t.id,ss,mStatus)
                    }
                    R.id.rl_item_fg_todo_content -> {
                        //进入编辑或是新增 todo 界面
                        val ac = activity as ToDoActivity
                        startActivity<AddToDoActivity>(bundle = arrayOf(
                            "from" to "update",
                            "type" to ac.mType,
                            "status" to mStatus,
                            "data" to mAdapter.data[position].t
                        ))
                    }
                }
            }
        }
    }

    override fun providerVMClass(): Class<ToDoFgViewModel>? = ToDoFgViewModel::class.java
}