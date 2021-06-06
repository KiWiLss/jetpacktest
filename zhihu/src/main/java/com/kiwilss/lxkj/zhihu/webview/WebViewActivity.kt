/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: WebViewActivity
 * Author:   kiwilss
 * Date:     2019-12-25 19:21
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.zhihu.webview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kiwilss.lxkj.zhihu.R
import kotlinx.android.synthetic.main.activity_webview.*

/**
 *@FileName: WebViewActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-12-25
 * @desc   : {DESCRIPTION}
 */
class WebViewActivity : AppCompatActivity(){


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)


        wv.settings.javaScriptEnabled = true

        wv.loadUrl("https://www.baidu.com")



    }

}