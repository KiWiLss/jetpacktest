/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: BaseFragment
 * Author:   kiwilss
 * Date:     2019-08-23 11:11
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvvmtest.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.coder.zzq.smartshow.toast.SmartToast
import com.kiwilss.lxkj.ktx.livedata.LifecycleHandler
import com.kiwilss.lxkj.mvvmtest.dialog.loading.LoadingPopupTwo
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView

/**
 *@FileName: BaseFragment
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-23
 * @desc   : {DESCRIPTION}
 */
abstract class BaseFragment<VM: BaseViewModel>: Fragment() {

    protected  var mViewModel: VM? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    abstract fun getLayoutResId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initVM()
        initInterface(view,savedInstanceState)
        startObserve()
        //处理各种网络请求失败的结果
        handlerError()
        initData()
        initOnClick()

        super.onViewCreated(view, savedInstanceState)
    }

    open fun handlerError() {
        mViewModel?.run {
            error.observe(this@BaseFragment, Observer {
                dismissLoadingDiloag()
                it?.run {
                    //toast(it)
                    SmartToast.error(it)
                    //PostUtil.postCallbackDelayed(mLoadSir, ErrorCallback::class.java)
                }
            })
            //异常的处理,在 error 没有处理时处理
//            mException.observe(this@BaseFragment, Observer {
//
//            })
        }
    }

    abstract fun startObserve()

    abstract fun initOnClick()

    abstract fun initData()

    abstract fun initInterface(view: View, savedInstanceState: Bundle?)

    private fun initVM() {
        providerVMClass()?.let {
            mViewModel = ViewModelProviders.of(this).get(it)
            mViewModel?.run {
                lifecycle.addObserver(this)
            }
        }
    }

    abstract fun providerVMClass(): Class<VM>?


    var mLoadingMessage = "加载中..."

    var mBasePopup: BasePopupView? = null

    fun showLoadingDiloag(){
        showLoadingDiloag(mLoadingMessage)
    }

    fun showLoadingDiloag(message: String){
        mBasePopup?.apply {
            if (isShow) {
                dismiss()
            }
        }
        mBasePopup = XPopup.Builder(context)
            .hasShadowBg(false)
            //.popupAnimation(PopupAnimation.NoAnimation)
            .dismissOnBackPressed(false)
            .dismissOnTouchOutside(false)
            .asCustom(LoadingPopupTwo(context!!).setTitle(message))
            .show()
    }

    fun dismissLoadingDiloag(){
        mBasePopup?.apply {
            dismiss()
        }
    }

    override fun onDestroy() {
        mViewModel?.run {
            lifecycle.removeObserver(this)
        }
        dismissLoadingDiloag()
        super.onDestroy()
    }
}


//post, postDelay
fun Fragment.post(action: ()->Unit){
    LifecycleHandler(this).post { action() }
}
//
fun Fragment.postDelay(delay:Long = 0, action: ()->Unit){
    LifecycleHandler(this).postDelayed({ action() }, delay)
}
