/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: HomeAdapter
 * Author:   kiwilss
 * Date:     2019-08-27 17:25
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvvmtest.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 *@FileName: HomeAdapter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-27
 * @desc   : {DESCRIPTION}
 */
class HomeAdapter(val data: List<Fragment>,fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = data[position]

    override fun getCount(): Int = data.size
}