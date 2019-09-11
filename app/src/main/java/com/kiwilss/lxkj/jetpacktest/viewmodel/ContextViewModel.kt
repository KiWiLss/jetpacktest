/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: ContextViewModel
 * Author:   kiwilss
 * Date:     2019-08-15 16:39
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.jetpacktest.viewmodel

import android.app.AlertDialog
import android.app.Application
import androidx.lifecycle.AndroidViewModel

/**
 *@FileName: ContextViewModel
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-15
 * @desc   : {DESCRIPTION}
 */
class ContextViewModel(private val context: Application): AndroidViewModel(context) {

    var textName  = "hello"

    fun test(){
        val alertDialog = AlertDialog.Builder(context)
    }

}