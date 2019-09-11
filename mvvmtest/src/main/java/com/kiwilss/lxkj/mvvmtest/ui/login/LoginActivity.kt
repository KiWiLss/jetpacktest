/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: LoginActivity
 * Author:   kiwilss
 * Date:     2019-08-28 11:52
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvvmtest.ui.login

import android.os.Bundle
import androidx.lifecycle.Observer
import com.alibaba.fastjson.JSON
import com.blankj.utilcode.util.LogUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kiwilss.lxkj.ktx.core.click
import com.kiwilss.lxkj.mvpretrofit.config.Constant
import com.kiwilss.lxkj.mvpretrofit.config.LOADING_HINT
import com.kiwilss.lxkj.mvvmtest.R
import com.kiwilss.lxkj.mvvmtest.base.BaseActivity
import com.kiwilss.lxkj.mvvmtest.config.KEY_HOME_LOGIN_SUCCESS
import com.lxj.androidktx.core.edit
import com.lxj.androidktx.core.sp
import kotlinx.android.synthetic.main.activity_login.*

/**
 *@FileName: LoginActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-28
 * @desc   : {DESCRIPTION}
 */
class LoginActivity: BaseActivity<LoginViewModel>() {
    override fun startObserve() {
        mViewModel?.run {
            loginResult.observe(this@LoginActivity, Observer {
                handlerResult(it)
            })

            registerResult.observe(this@LoginActivity, Observer {
                handlerResult(it)
            })
        }
    }

    private fun handlerResult(it: LoginInfo?) {
        //success change home
        LogUtils.e(JSON.toJSONString(it))
        dismissLoadingDiloag()
        sp().edit {
            putBoolean(Constant.SP_IS_LOGIN,true)
            putString(Constant.SP_USERNAME,it?.username)
        }
        LiveEventBus.get().with(KEY_HOME_LOGIN_SUCCESS).post(true)
        //LiveDataBus.with<Boolean>(KEY_HOME_IS_LOGIN).setValue(true)
        finish()
    }

    override fun initData() {

    }

    override fun initOnClick() {
        //login listener
        stv_login_login.click {
            if (checkInput()) {
                showLoadingDiloag(LOADING_HINT)
               mViewModel?.login(mAccount,mPwd)
            }
        }
        //register listener
        stv_login_register.click {
            if (checkInput()){
                showLoadingDiloag(LOADING_HINT)
                mViewModel?.register(mAccount,mPwd)
            }
        }
    }

    private fun checkInput() : Boolean{
        mAccount = til_login_account.editText?.text.toString()
        if (mAccount.isEmpty()){
            til_login_account.error = getString(R.string.please_input_account)
            return false
        }
        mPwd = til_login_pwd.editText?.text.toString()
        if (mPwd.isEmpty()){
            til_login_pwd.error = getString(R.string.please_input_pwd)
            return false
        }
        return true
    }

    lateinit var mAccount: String
    lateinit var mPwd: String

    override fun initInterface(savedInstanceState: Bundle?) {



    }

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun providerVMClass(): Class<LoginViewModel>? = LoginViewModel::class.java
}