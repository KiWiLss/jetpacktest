package com.kiwilss.lxkj.mvvmtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gyf.barlibrary.ImmersionBar
import com.kiwilss.lxkj.ktx.core.startActivity
import com.kiwilss.lxkj.mvvmtest.ui.HomeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //透明状态栏
        //透明状态栏
        ImmersionBar.with(this)
            .transparentStatusBar()
            //.transparentBar()
            //.statusBarColor(R.color.colorAccent)
            //.fitsSystemWindows(true)
            .statusBarDarkFont(true, 0f)
            //.navigationBarAlpha(1)
            .init()

//        postDelay (1000){
//            //toast("world")
//            startActivity<HomeActivity>()
//            //SkinManager.getInstance().changeSkin("black")
//            finish()
//        }
        window.decorView.postDelayed({
            startActivity<HomeActivity>()
            finish()
        }, 1000)



    }




    override fun onDestroy() {
        ImmersionBar.with(this).destroy()
        super.onDestroy()
    }
}


