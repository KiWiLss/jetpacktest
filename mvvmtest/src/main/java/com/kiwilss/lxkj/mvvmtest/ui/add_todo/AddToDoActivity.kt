/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: AddToDoActivity
 * Author:   kiwilss
 * Date:     2019-08-30 14:23
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvvmtest.ui.add_todo

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kiwilss.lxkj.ktx.core.click
import com.kiwilss.lxkj.ktx.core.toast
import com.kiwilss.lxkj.mvpretrofit.config.LOADING_HINT
import com.kiwilss.lxkj.mvvmtest.utils.HINT_PLEASE_INPUT_DETAIL
import com.kiwilss.lxkj.mvvmtest.utils.HINT_PLEASE_INPUT_TITLE
import com.kiwilss.lxkj.mvvmtest.utils.hintIsEmpty

import com.kiwilss.lxkj.mvvmtest.R
import com.kiwilss.lxkj.mvvmtest.base.BaseActivity
import com.kiwilss.lxkj.mvvmtest.config.KEY_ADD_TODO_ADD
import com.kiwilss.lxkj.mvvmtest.config.KEY_ADD_TODO_UPDATE
import com.kiwilss.lxkj.mvvmtest.ui.todo.fg.Data
import com.kiwilss.lxkj.mvvmtest.utils.DateUtils
import com.kiwilss.lxkj.mvvmtest.utils.toastS
import kotlinx.android.synthetic.main.activity_add_todo.*
import kotlinx.android.synthetic.main.currency_top.*

/**
 *@FileName: AddToDoActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-30
 * @desc   : {DESCRIPTION}
 */
class AddToDoActivity : BaseActivity<AddToDoViewModel>(){



    override fun startObserve() {
        mViewModel?.run {
            //新增结果
            mAddToDo.observe(this@AddToDoActivity, Observer {
                dismissLoadingDiloag()
                toastS("加入成功")
                //刷新上一个界面
               LiveEventBus.get().with(KEY_ADD_TODO_ADD).post(true)
                finish()

            })
            //更新结果
            mUpdateResult.observe(this@AddToDoActivity, Observer {
                dismissLoadingDiloag()
                toastS("更新成功")
                //刷新上一个界面,哪个 fragment 进来的,刷新哪个
                LiveEventBus.get().with(KEY_ADD_TODO_UPDATE + mStatus.toString()).post(true)
                //LiveDataBus.with<Boolean>(KEY_ADD_TODO_ADD_REFRESH + mStatus.toString()).setValue(true)
                finish()
            })
        }



    }

    override fun initData() {
    }

    override fun initOnClick() {
        //日期选择
        ll_add_todo_date.click {
            val dpd = android.app.DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    mChoiceData = "$year-${month + 1}-$dayOfMonth"
                    tv_add_todo_date.text = mChoiceData
                },
                DateUtils.getNowY2(),
                DateUtils.getNowMonth()-1,
                DateUtils.getNowDay())
            dpd.show()
        }
        //保存监听
        stv_add_todo_save.click {
            //先判断是否有标题和详情
            val title = et_add_todo_title.text.toString()
            if (title.hintIsEmpty(HINT_PLEASE_INPUT_TITLE)) {
                val content = et_add_todo_content.text.toString()
                if (content.hintIsEmpty(HINT_PLEASE_INPUT_DETAIL)) {
                    //获取优先级
                    if (rb0.isChecked) mPriority = 2
                    if (rb1.isChecked) mPriority = 1
                    showLoadingDiloag(LOADING_HINT)
                    val map = HashMap<String,Any>()
                    map["title"] = title
                    map["content"] = content
                    map["date"] = mChoiceData
                    map["type"] = mType
                    map["priority"] = mPriority
                    if (mFrom == "add"){
                        //新增
                        mViewModel?.addToDo(map)
                        //mPresenter.addOneToDo(map)
                    }else{
                        //更新
                        if (mData != null){
                            map["status"] = mData!!.status
                            mViewModel?.update(mData!!.id,map)
                            //mPresenter.updateOneToDo(mData!!.id,map)
                        }else{
                            dismissLoadingDiloag()
                            toast("出现错误,请重试")
                        }
                    }

                }
            }
        }
    }

    lateinit var mFrom: String
    var mType = 0
    lateinit var mChoiceData: String
    //priority 主要用于定义优先级，在app 中预定义几个优先级：重要（1），一般（2）等，查询的时候，传入priority 进行筛选；
    var mPriority = 0

    var mData: Data? = null
    var mStatus = 0

    override fun initInterface(savedInstanceState: Bundle?) {

        //获取传入的参数
        intent.run {
            mFrom = getStringExtra("from")
            mType = getIntExtra("type",0)
            mData = getSerializableExtra("data") as Data?
            mStatus = getIntExtra("status",0)
        }

        //日期默认设置今天
        val nowData = DateUtils.getNowString(DateUtils.PATTEN_YMD)
        tv_add_todo_date.text = nowData
        mChoiceData = nowData
        //mfrom == update,就是更新一条
        if (mFrom == "add"){
            tv_currency_top_title.text = resources.getString(R.string.add_todo)
        }else{
            tv_currency_top_title.text = resources.getString(R.string.edit)
            //初始化界面数据
            mData?.run {
                et_add_todo_title.setText(title)
                et_add_todo_content.setText(content)
                tv_add_todo_date.text = dateStr
                //重要（1），一般（2
                if (priority == 1) {
                    rb1.isChecked = true
                }else{
                    rb0.isChecked = true
                }
            }
        }



    }

    override fun getLayoutId(): Int = R.layout.activity_add_todo

    override fun providerVMClass(): Class<AddToDoViewModel>? = AddToDoViewModel::class.java

}