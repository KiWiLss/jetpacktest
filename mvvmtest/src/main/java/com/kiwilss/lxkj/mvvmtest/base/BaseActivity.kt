/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: BaseActivity
 * Author:   kiwilss
 * Date:     2019-08-22 18:11
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvvmtest.base

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.LogUtils
import com.coder.zzq.smartshow.toast.SmartToast
import com.gyf.barlibrary.ImmersionBar
import com.kiwilss.lxkj.ktx.livedata.LifecycleHandler
import com.kiwilss.lxkj.mvvmtest.dialog.loading.LoadingPopupTwo
import com.kiwilss.lxkj.mvvmtest.manager.ActivityCollector
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import kotlinx.android.synthetic.main.currency_top.*
import java.lang.ref.WeakReference

/**
 *@FileName: BaseActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-22
 * @desc   : {DESCRIPTION}
 */
abstract class BaseActivity<VM : BaseViewModel>: AppCompatActivity() {

    protected  var mViewModel: VM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置布局前操作
        doBeforeSetContentView()
        //设置布局
        setContentView(getLayoutId())
        //换肤注册
        //SkinManager.getInstance().register(this)
        //设置状态栏
        setStatusBar()
        initVM()
        //当前活动加入活动管理器
        ActivityCollector.instance().addActivity(this)
        //初始化主界面
        initInterface(savedInstanceState)
        //初始化点击事件
        initOnClick()
        //处理各种网络请求失败的结果
        handlerError()
        //处理成功返回结果
        startObserve()
        //初始化数据
        initData()
        //初始化标题
        initToolbarTitle()
        //设置返回监听
        setBackListener()

    }

    /**
     * 设置状态栏
     */
    //lateinit var mImmersionBar: ImmersionBar
    open fun setStatusBar() {
//        mImmersionBar = ImmersionBar.with(this)
//        mImmersionBar.statusBarDarkFont(true,0f)
//            .fitsSystemWindows(true)
//            .init()
        //设置默认样式
//        ImmersionBar.with(this)
//            //.transparentBar()
//            //.statusBarColor(R.color.white)
//            .fitsSystemWindows(true)
//            .statusBarDarkFont(true, 0f)
//            //.navigationBarAlpha(1)
//            .init()
    }


    var mLoadingMessage = "加载中..."

    var mBasePopup: BasePopupView? = null

    val weakReference by lazy { WeakReference(this) }

    fun showLoadingDiloag(){
        showLoadingDiloag(mLoadingMessage)
    }

    fun showLoadingDiloag(message: String){
        mBasePopup?.apply {
            dismiss()
        }
        //XPopup.setAnimationDuration(200)
        //val contextweakReference.get()
        mBasePopup = XPopup.Builder(this)
            //.popupAnimation(PopupAnimation.NoAnimation)
            .hasShadowBg(false)
            .dismissOnBackPressed(false)
            .dismissOnTouchOutside(false)
            .asCustom( LoadingPopupTwo(this).setTitle(message) )
            .show()

    }

    fun dismissLoadingDiloag(){
        mBasePopup?.apply {
            dismiss()
        }
        //mBasePopup = null
    }

    abstract fun startObserve()

    open fun handlerError() {
        mViewModel?.run {
            error.observe(this@BaseActivity, Observer {
                LogUtils.e("baseactivity----$it")
                dismissLoadingDiloag()
                it?.run {
                    //toast(it)
                    SmartToast.error(it)
                    //PostUtil.postCallbackDelayed(mLoadSir, ErrorCallback::class.java)
                }
            })
            //发生异常时的处理
            //mException.observe()
        }
    }
    open fun setBackListener() {
        //设置返回点击
        iv_currency_top_back?.let { it ->
            it.setOnClickListener { onBackPressed() }
        }
    }

    open fun initToolbarTitle() {
        //设置标题
        tv_currency_top_title?.let {
            it.text = title
        }
    }

    override fun getResources(): Resources {
        return AdaptScreenUtils.adaptWidth(super.getResources(), 375)
    }

    abstract fun initData()

    abstract fun initOnClick()

    abstract fun initInterface(savedInstanceState: Bundle?)

    abstract fun getLayoutId(): Int

    open fun doBeforeSetContentView(){}

    abstract fun providerVMClass(): Class<VM>?

    private fun initVM() {
        providerVMClass()?.let {
            mViewModel = ViewModelProviders.of(this).get(it)
            mViewModel?.let(lifecycle::addObserver)
        }
    }

    override fun onDestroy() {
        mViewModel?.let {
            lifecycle.removeObserver(it)
        }
        //SkinManager.getInstance().unregister(this)
        //mImmersionBar.destroy()
        ImmersionBar.with(this).destroy()
        dismissLoadingDiloag()
        ActivityCollector.instance().removeActivity2(this)
        super.onDestroy()
    }

}

fun AppCompatActivity?.finishDelay(delay: Long = 1) {
    this?.run {
        LifecycleHandler(this).postDelayed({ finish() }, delay)
    }
}

fun AppCompatActivity?.post(action: ()->Unit){
    LifecycleHandler(this).post { action() }
}

fun AppCompatActivity?.postDelay(delay:Long = 0, action: ()->Unit){
    LifecycleHandler(this).postDelayed({ action() }, delay)
}