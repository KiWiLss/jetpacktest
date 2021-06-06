package com.kiwilss.lxkj.hotfit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kiwilss.lxkj.hotfit.Utils.getDarkModeStatus

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (getDarkModeStatus(this)) {
            setTheme(R.style.main_theme_dark)
        }else {
            setTheme(R.style.AppTheme)
        }

        setContentView(R.layout.activity_main)

        //SophixManager.getInstance().queryAndLoadNewPatch()


    }
}
