package com.kiwilss.lxkj.jetpacktest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kiwilss.lxkj.jetpacktest.ch.CHActivity
import com.kiwilss.lxkj.jetpacktest.dagger.DaggerActivity
import com.kiwilss.lxkj.jetpacktest.ff.FFActivity
import com.kiwilss.lxkj.jetpacktest.lifecycler.LifecyclerActivity
import com.kiwilss.lxkj.jetpacktest.qmui.QmUiActivity
import com.kiwilss.lxkj.jetpacktest.qq.QQActivity
import com.kiwilss.lxkj.jetpacktest.qq.SDActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        "123".e()
//        loge("111")
        btn_main_jetpack.setOnClickListener {
            startActivity(Intent(this,LifecyclerActivity::class.java))
        }
        btn_main_dagger.setOnClickListener {
            startActivity(Intent(this,DaggerActivity::class.java))
        }
        btn_main_qq.setOnClickListener {
            startActivity(Intent(this,QQActivity::class.java))
        }
        btn_main_ff.setOnClickListener {
            startActivity(Intent(this,FFActivity::class.java))
        }
        btn_main_sd.setOnClickListener {
            startActivity(Intent(this,SDActivity::class.java))
        }
        btn_main_ch.setOnClickListener {
            startActivity(Intent(this,CHActivity::class.java))
        }
        btn_main_qm.setOnClickListener {
            startActivity(Intent(this,QmUiActivity::class.java))
        }
    }
}
