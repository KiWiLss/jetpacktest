/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: WebviewActivity
 * Author:   kiwilss
 * Date:     2019-08-27 18:42
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvvmtest.ui.webview

import android.net.http.SslError
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.webkit.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import com.jeremyliao.liveeventbus.LiveEventBus
import com.just.agentweb.AgentWeb
import com.just.agentweb.NestedScrollAgentWebView
import com.kiwilss.lxkj.ktx.core.startActivity
import com.kiwilss.lxkj.mvpretrofit.config.Constant
import com.kiwilss.lxkj.mvpretrofit.config.LOADING_HINT
import com.kiwilss.lxkj.mvvmtest.R
import com.kiwilss.lxkj.mvvmtest.base.BaseActivity
import com.kiwilss.lxkj.mvvmtest.config.KEY_WEBVIEW_COLLECT_SUCCESS
import com.kiwilss.lxkj.mvvmtest.ui.login.LoginActivity
import com.kiwilss.lxkj.mvvmtest.utils.browse
import com.kiwilss.lxkj.mvvmtest.utils.share
import com.kiwilss.lxkj.mvvmtest.utils.toastS
import com.lxj.androidktx.core.sp
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 *@FileName: WebviewActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-27
 * @desc   : {DESCRIPTION}
 */
class WebviewActivity: BaseActivity<WebviewModel>() {
    override fun startObserve() {
        mViewModel?.run {
            collectResult.observe(this@WebviewActivity, Observer {
                dismissLoadingDiloag()
                toastS("收藏成功")
                //提示进入的界面收藏成功,修改界面
                LiveEventBus.get().with(KEY_WEBVIEW_COLLECT_SUCCESS)
                    .post(true)
            })
        }
    }

    override fun initData() {

    }

    override fun initOnClick() {


    }

    private lateinit var mAgentWeb: AgentWeb
    var mId: Int = 0
    lateinit var mTitle: String
    lateinit var murl: String

    private val mWebView: NestedScrollAgentWebView by lazy {
        NestedScrollAgentWebView(this)
    }

    override fun initInterface(savedInstanceState: Bundle?) {


        //获取传入的参数
        mId = intent.getIntExtra("id",0)
        mTitle = intent.getStringExtra("title")
        val url = intent.getStringExtra("url")
        murl = url


        val layoutParams = CoordinatorLayout.LayoutParams(-1, -1)
        layoutParams.behavior = AppBarLayout.ScrollingViewBehavior()

        mAgentWeb =  AgentWeb.with(this)
            .setAgentWebParent(cl_webview_cl , layoutParams)
            .useDefaultIndicator()
            .setWebView(mWebView)
            .setWebChromeClient(webChromeClient)
            .createAgentWeb()
            .ready()
            .go(url)

        //处理标题
        tb_webview_tl.run {
            title = "正在加载中..."
            setSupportActionBar(this)
            //设置可见返回图标
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            //设置返回图片可用
            //supportActionBar?.setHomeButtonEnabled(true)
            setNavigationOnClickListener {
                finish()
            }
        }


    }


    /**
     * webChromeClient
     */
    private val webChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
        }

        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            title.let {
                tb_webview_tl.title = it
            }
        }
    }

    /**
     * webViewClient
     */
    private val webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            super.onReceivedSslError(view, handler, error)
        }

//        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
//            // super.onReceivedSslError(view, handler, error)
//            handler?.proceed()
//        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.webview_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_share -> {
                share(murl,mTitle)
            }
            R.id.action_like -> {
                //判断是否登录
                val  isLogin = sp().getBoolean(Constant.SP_IS_LOGIN,false)
                if (isLogin){
                    //调用收藏,判断是否已经收藏
                    showLoadingDiloag(LOADING_HINT)
                    mViewModel?.collectArtcile(mId)
                }else{
                    startActivity<LoginActivity>()
                }
            }
            R.id.action_browser -> {
                browse(murl)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        //toast("hello")
        return if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()

    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    @ExperimentalCoroutinesApi
    override fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }

    override fun getLayoutId(): Int = R.layout.activity_webview

    override fun providerVMClass(): Class<WebviewModel>? = WebviewModel::class.java
}