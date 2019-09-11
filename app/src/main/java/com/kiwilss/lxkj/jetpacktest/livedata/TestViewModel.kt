/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: TestViewModel
 * Author:   kiwilss
 * Date:     2019-08-15 16:53
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.jetpacktest.livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *@FileName: TestViewModel
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-15
 * @desc   : {DESCRIPTION}
 */
class TestViewModel: ViewModel() {
    var mCurrent: MutableLiveData<String>? = null
     get() {
        if (field == null){
            field = MutableLiveData()
        }
        return field
    }

    fun test(){

    }

}