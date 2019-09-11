/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: SettingActivity
 * Author:   kiwilss
 * Date:     2019-09-06 11:08
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.setting

import android.os.Bundle
import com.kiwilss.lxkj.ktx.core.click
import com.kiwilss.lxkj.ktx.core.startActivity
import com.kiwilss.lxkj.mvvmtest.R
import com.kiwilss.lxkj.mvvmtest.base.BaseActivity
import com.kiwilss.lxkj.mvvmtest.ui.home.HomeModel
import com.kiwilss.lxkj.mvvmtest.ui.sister.SisterActivity
import com.kiwilss.lxkj.mvvmtest.ui.tucong.TuCongActivity
import kotlinx.android.synthetic.main.activity_setting.*

/**
 *@FileName: SettingActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-09-06
 * @desc   : {DESCRIPTION}
 */
class SettingActivity : BaseActivity<HomeModel>() {
    override fun startObserve() {

    }

    override fun initData() {
    }

    override fun initOnClick() {

        rl_setting_tucong.click {
            startActivity<TuCongActivity>()
        }

        rl_setting_sister.click {
            startActivity<SisterActivity>()
        }



    }

    override fun initInterface(savedInstanceState: Bundle?) {
    }

    override fun getLayoutId(): Int = R.layout.activity_setting

    override fun providerVMClass(): Class<HomeModel>? = HomeModel::class.java
}