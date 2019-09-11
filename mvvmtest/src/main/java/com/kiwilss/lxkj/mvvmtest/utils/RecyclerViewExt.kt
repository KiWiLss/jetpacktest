/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: RecyclerViewExt
 * Author:   kiwilss
 * Date:     2019-09-03 22:28
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 *@FileName: RecyclerViewExt
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-03
 * @desc   : {DESCRIPTION}
 */

/**
 * 滚动到顶部
 * @receiver RecyclerView
 */
fun RecyclerView.scrollToTop(){
    val layoutManager: LinearLayoutManager = this.layoutManager as LinearLayoutManager
    if (layoutManager.findFirstVisibleItemPosition() > 20){
        scrollToPosition(0)//直接滚动到顶
    }else{
        smoothScrollToPosition(0)//模拟滚动到顶
    }
}