package com.kiwilss.lxkj.zhihu


import android.animation.Animator
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.gyf.immersionbar.ImmersionBar
import com.kiwilss.lxkj.okhttp.okhttp.get
import com.kiwilss.lxkj.okhttp.okhttp.http
import com.kiwilss.lxkj.zhihu.base.BaseActivity
import com.kiwilss.lxkj.zhihu.liuhai.FullScreenActivity
import com.kiwilss.lxkj.zhihu.liuhai.LiuHaiActivity
import com.kiwilss.lxkj.zhihu.theme.ColorUiUtil
import com.kiwilss.lxkj.zhihu.theme.Theme
import com.kiwilss.lxkj.zhihu.ui.ShapeActivity
import com.kiwilss.lxkj.zhihu.utils.PreUtils
import com.kiwilss.lxkj.zhihu.webview.WebViewActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        alert("Hi, I'm Roy", "Have you tried turning it off and on again?") {
//            yesButton { toast("Oh…") }
//            noButton {}
//        }.show()
//
//        progressDialog("") {
//
//        }


        //SkinManager.getInstance().register(this)

        val immersionBar = ImmersionBar.with(this)

        btn_main_change.setOnClickListener {
            //SkinManager.getInstance().changeSkin("green")
            //immersionBar.statusBarColor(R.color.item_text_color_green).init()
            startActivity(Intent(this,WebViewActivity::class.java))
        }
        btn_main_change2.setOnClickListener {
            //SkinManager.getInstance().changeSkin("red")
            //immersionBar.statusBarColor(R.color.item_text_color_red).init()
        }
        btn_main_change3.setOnClickListener {
            startActivity(Intent(this,TwoActivity::class.java))
//            SkinManager.getInstance().changeSkin("red")
//            immersionBar.statusBarColor(R.color.item_text_color_red).init()
        }
        btn_main_full.setOnClickListener {
            startActivity(Intent(this,FullScreenActivity::class.java))
        }
        btn_main_trans.setOnClickListener {
            startActivity(Intent(this,LiuHaiActivity::class.java))
        }


        ctv_main_change.setOnClickListener {
            setTheme(R.style.BlueTheme)
            PreUtils.setCurrentTheme(this, Theme.Blue)
            change()
        }
        ctv_main_next.setOnClickListener {
            setTheme(R.style.BrownTheme)
            PreUtils.setCurrentTheme(this, Theme.Brown)
            change()
        }

        btn_main_shape.setOnClickListener {
            startActivity(Intent(this,ShapeActivity::class.java))
        }
        btn_main_super.setOnClickListener {
            test1()
        }
    }

    private fun test1() {
        GlobalScope.launch {
            //https://www.wanandroid.com/banner/json
            val baseBean = "https://www.wanandroid.com/banner/json".http()
                .get<String>().await()
            Log.e("MMM",baseBean.toString())
            //LogUtils.e(baseBean?.data)
        }

    }

    fun change() {
        val rootView = window.decorView
        rootView.isDrawingCacheEnabled = true
        rootView.buildDrawingCache(true)

        val localBitmap = Bitmap.createBitmap(rootView.drawingCache)
        rootView.isDrawingCacheEnabled = false
        if (null != localBitmap && rootView is ViewGroup) {
            val tmpView = View(applicationContext)
            tmpView.setBackgroundDrawable(BitmapDrawable(resources, localBitmap))
            val params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            rootView.addView(tmpView, params)
            tmpView.animate().alpha(0f).setDuration(400)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {
                        ColorUiUtil.changeTheme(rootView, theme)
                        System.gc()
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        rootView.removeView(tmpView)
                        localBitmap.recycle()
                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }
                }).start()
        }
    }

    override fun onDestroy() {
        //SkinManager.getInstance().unregister(this)
        super.onDestroy()
    }
}
