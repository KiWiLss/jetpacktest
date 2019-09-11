/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: ShapeActivity
 * Author:   kiwilss
 * Date:     2019-09-10 14:50
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.zhihu.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kiwilss.lxkj.zhihu.R
import com.kiwilss.lxkj.zhihu.ui.shape.CheckActivity
import com.kiwilss.lxkj.zhihu.ui.shape.EditActivity
import com.kiwilss.lxkj.zhihu.ui.shape.RTextViewActivity
import com.kiwilss.lxkj.zhihu.ui.shape.RipperActivity
import kotlinx.android.synthetic.main.activity_shape.*

/**
 *@FileName: ShapeActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-10
 * @desc   : {DESCRIPTION}
 */
class ShapeActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shape)

        btn_shape_text.setOnClickListener{
            startActivity(Intent(this,RTextViewActivity::class.java))
        }
        btn_shape_riper.setOnClickListener{
            startActivity(Intent(this,RipperActivity::class.java))
        }
        btn_shape_check.setOnClickListener{
            startActivity(Intent(this,CheckActivity::class.java))
        }
        btn_shape_edit.setOnClickListener{
            startActivity(Intent(this,EditActivity::class.java))
        }
    }
}