/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: ToDoAdapter
 * Author:   kiwilss
 * Date:     2019-08-29 16:34
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvvmtest.ui.todo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 *@FileName: ToDoAdapter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-29
 * @desc   : {DESCRIPTION}
 */
class ToDoAdapter(fm: FragmentManager,val data: List<Fragment>): FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment = data[position]

    override fun getCount(): Int = data.size
}