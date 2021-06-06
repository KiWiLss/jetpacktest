/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: FullScreenActivity
 * Author:   kiwilss
 * Date:     2019-09-04 10:55
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.zhihu.liuhai

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import com.kiwilss.lxkj.zhihu.R

/**
 *@FileName: FullScreenActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-04
 * @desc   : {DESCRIPTION}
 */
class FullScreenActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen)

        //透明状态栏,依然显示状态栏
        ImmersionBar.with(this)
            .fullScreen(true)
            .fitsSystemWindows(true)
            .init()

    }

    override fun onDestroy() {
        super.onDestroy()
    }

}