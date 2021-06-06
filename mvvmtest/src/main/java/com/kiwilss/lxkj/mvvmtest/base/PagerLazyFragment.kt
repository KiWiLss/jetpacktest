/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: PagerLazyFragment
 * Author:   kiwilss
 * Date:     2019-08-23 11:39
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
import com.blankj.utilcode.util.LogUtils
import com.coder.zzq.smartshow.toast.SmartToast
import com.kiwilss.lxkj.mvvmtest.dialog.loading.LoadingPopupTwo
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView

/**
 *@FileName: PagerLazyFragment
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-23
 * @desc   : {DESCRIPTION}
 */
abstract class PagerLazyFragment<VM: BaseViewModel> : Fragment() {

    protected  var mViewModel: VM? = null
    var isInit = false

    var mRootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null){
            mRootView = inflater.inflate(getLayoutResId(), container, false)
        }
        return mRootView
    }

    abstract fun getLayoutResId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initVM()
        lazyInit()
        LogUtils.e("fragment--------$isVisible-------$userVisibleHint--------$isHidden")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        lazyInit()
        LogUtils.e(javaClass.simpleName + "setuser----fragment--$isVisibleToUser------$isVisible-------$userVisibleHint-----$isHidden")
    }


    private fun lazyInit() {
        if (mRootView != null && userVisibleHint && !isInit ){
            isInit = true
            initInterface()
            startObserve()
            //处理各种网络请求失败的结果
            handlerError()
            initData()

        }
    }

    abstract fun startObserve()

    open fun handlerError() {
        mViewModel?.run {
            error.observe(this@PagerLazyFragment, Observer {
                LogUtils.e("fragment$it-------${this@PagerLazyFragment.javaClass.name}")
                dismissLoadingDiloag()
                it?.run {
                    //toast(it)
                    SmartToast.error(it)
                    //PostUtil.postCallbackDelayed(mLoadSir, ErrorCallback::class.java)
                }
            })
        }
    }

    abstract fun initData()

    abstract fun initInterface()

    private fun initVM() {
        providerVMClass()?.let {
            mViewModel = ViewModelProviders.of(this).get(it)
            mViewModel?.run {
                lifecycle.addObserver(this)
            }
        }
    }

    abstract fun providerVMClass(): Class<VM>?

    override fun onDestroy() {
        mViewModel?.run {
            lifecycle.removeObserver(this)
        }
        dismissLoadingDiloag()
        super.onDestroy()
    }

    var mLoadingMessage = "加载中..."

    private var mBasePopup: BasePopupView? = null

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
           // .popupAnimation(PopupAnimation.NoAnimation)
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

}