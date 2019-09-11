/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: HomeActivity
 * Author:   kiwilss
 * Date:     2019-08-25 21:04
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvvmtest.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.coder.zzq.smartshow.toast.SmartToast
import com.gyf.barlibrary.ImmersionBar
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kiwilss.lxkj.ktx.core.click
import com.kiwilss.lxkj.ktx.core.startActivity
import com.kiwilss.lxkj.mvpretrofit.config.Constant
import com.kiwilss.lxkj.mvpretrofit.config.LOADING_HINT
import com.kiwilss.lxkj.mvvmtest.R
import com.kiwilss.lxkj.mvvmtest.base.BaseActivity
import com.kiwilss.lxkj.mvvmtest.config.KEY_HOME_LOGIN_SUCCESS
import com.kiwilss.lxkj.mvvmtest.config.KEY_HOME_SCROLL_TOP
import com.kiwilss.lxkj.mvvmtest.manager.ActivityCollector
import com.kiwilss.lxkj.mvvmtest.ui.about.AboutActivity
import com.kiwilss.lxkj.mvvmtest.ui.collect.CollectActivity
import com.kiwilss.lxkj.mvvmtest.ui.home.HomeAdapter
import com.kiwilss.lxkj.mvvmtest.ui.home.HomeModel
import com.kiwilss.lxkj.mvvmtest.ui.home.fg.HomeFragment
import com.kiwilss.lxkj.mvvmtest.ui.knowledge.KnowledgeFragment
import com.kiwilss.lxkj.mvvmtest.ui.login.LoginActivity
import com.kiwilss.lxkj.mvvmtest.ui.navigation.NavigationFragment
import com.kiwilss.lxkj.mvvmtest.ui.project.ProjectFragment
import com.kiwilss.lxkj.mvvmtest.ui.search.SearchActivity
import com.kiwilss.lxkj.mvvmtest.ui.setting.SettingActivity
import com.kiwilss.lxkj.mvvmtest.ui.todo.ToDoActivity
import com.kiwilss.lxkj.mvvmtest.ui.wechat.WeChatFragment
import com.lxj.androidktx.core.clear
import com.lxj.androidktx.core.putBoolean
import com.lxj.androidktx.core.sp
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.activity_home.*

/**
 *@FileName: HomeActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-25
 * @desc   : {DESCRIPTION}
 */
class HomeActivity : BaseActivity<HomeModel>() {
    override fun startObserve() {
        //退出登录结果
        mViewModel?.run {
            //退出的结果
            exitResult.observe(this@HomeActivity, Observer {
                LogUtils.e("exit result-- $it")
                dismissLoadingDiloag()
                val dayNight = sp().getBoolean(Constant.SP_DAY_NIGHT,false)
                sp().clear()//清除前保存日夜间模式
                sp().putBoolean(Constant.SP_DAY_NIGHT,dayNight)
                //刷新所有带收藏的界面
                refreshAllFragment()
                //刷新左侧抽屉界面
                initDrawerHeader()
            })

        }
        //登录成功监听
        LiveEventBus.get()
            .with(KEY_HOME_LOGIN_SUCCESS,Boolean::class.java)
            .observe(this, Observer {
                if (it){//登录成功,修改界面
                    sp().putBoolean(Constant.SP_IS_LOGIN,true)
                    //刷新所有带收藏的界面
                    refreshAllFragment()
                    //刷新左侧抽屉界面
                    initDrawerHeader()
                }
            })


    }
    /**
     * 所有 fragment 界面,有登录相关刷新
     */
    private fun refreshAllFragment() {
        //LiveDataBus.with<Boolean>(KEY_HOME_IS_REFRESH).setValue(true)
    }
    override fun providerVMClass(): Class<HomeModel>? = HomeModel::class.java

    override fun initData() {

    }

    override fun initOnClick() {
        fab_home_up.click {
            //toast("hello")
            //点击,对应的列表滚动到顶部
            LiveEventBus.get().with(KEY_HOME_SCROLL_TOP + vp_home_vp.currentItem)
                .post(true)
        }
    }

    private val mFragmentList = arrayListOf<Fragment>()
    private var isLogin = false

    val mTitle = arrayListOf<String>("首页", "知识体系", "公众号", "导航", "项目")

    override fun initInterface(savedInstanceState: Bundle?) {

        bnv_home_navigation.setOnNavigationItemSelectedListener {
            //切换界面,切换标题
            //切换界面
            when (it.itemId) {
                R.id.main_home -> {
                    vp_home_vp.currentItem = 0
                    //切换标题
                    tb_home_tb?.run {
                        title = mTitle[0]
                    }
                }
                R.id.main_knowledge -> {
                    vp_home_vp.currentItem = 1
                    //切换标题
                    tb_home_tb?.run {
                        title = mTitle[1]
                    }
                }
                R.id.main_wechat -> {
                    vp_home_vp.currentItem = 2
                    //切换标题
                    tb_home_tb?.run {
                        title = mTitle[2]
                    }
                }
                R.id.main_navigation -> {
                    vp_home_vp.currentItem = 3
                    //切换标题
                    tb_home_tb?.run {
                        title = mTitle[3]
                    }
                }

                R.id.main_project -> {
                    vp_home_vp.currentItem = 4
                    //切换标题
                    tb_home_tb?.run {
                        title = mTitle[4]
                    }
                }
            }
            true
        }

        //viewpager 处理
        initToolbarAndDrawer()
        initFragment()
        initViewPager()

        //抽屉内点击事件
        initDrawerClick()
        initDrawerHeader()


    }

    private fun hintExitPw() {
        XPopup.Builder(this).asConfirm("退出", "是否确认退出?") {
            //调用退出接口
            showLoadingDiloag(LOADING_HINT)
            mViewModel?.exit()
        }.show()
    }

    private fun initDrawerHeader() {
        isLogin = sp().getBoolean(Constant.SP_IS_LOGIN, false)
        val tvHomeName = nv_home_drawer.getHeaderView(0).findViewById<TextView>(R.id.tv_home_name)
        //如果登录状态,显示姓名,显示退出按钮
        if (isLogin) {
            val username = sp().getString(Constant.SP_USERNAME, "")
            tvHomeName.text = username
        } else {
            tvHomeName.text = resources.getString(R.string.login)
        }
        tvHomeName.click {
            if (!isLogin) {
                startActivity<LoginActivity>()
            }
        }
        nv_home_drawer.menu.findItem(R.id.main_logout).isVisible = isLogin
    }

    @SuppressLint("ResourceType")
    private fun initDrawerClick() {
        isLogin = sp().getBoolean(Constant.SP_IS_LOGIN, false)

        //抽屉内容设置
        nv_home_drawer?.run {
            //设置每个文字和图片颜色变化
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                itemTextColor = resources.getColorStateList(R.drawable.nav_menu_text_color, null)
                itemIconTintList = resources.getColorStateList(R.drawable.nav_menu_text_color, null)
            }
            //设置某个是选中状态
            //setCheckedItem(R.id.main_collect)
            //各点击事件
            setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.main_collect -> {
                        if (isLogin) {
                            startActivity<CollectActivity>()
                        } else {
                            startActivity<LoginActivity>()
                        }
                    }
                    R.id.main_todo -> {
                        if (isLogin) {
                            startActivity<ToDoActivity>()
                        } else {
                            startActivity<LoginActivity>()
                        }
                    }
                    //日间,夜间模式切换
                    R.id.main_mode -> {
                        //直接切换当前模式
                        //获取当前的主题
                        val mode =
                            resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                        if (mode == Configuration.UI_MODE_NIGHT_YES) {//夜间,改白天
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        } else if (mode == Configuration.UI_MODE_NIGHT_NO) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        }
//                        window.setWindowAnimations(R.style.WindowAnimationFadeInOut)
//                        recreate()
                        startActivity<HomeActivity>()
                        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
                        finish()
                    }
                    R.id.main_setting -> {
                        startActivity<SettingActivity>()
                    }
                    //about us
                    R.id.main_about -> {
                        //startActivity<AboutActivity>()
                        startActivity<AboutActivity>()
                    }
                    //exit
                    R.id.main_logout -> {
                        //toast("exit")
                        //提示退出对话框
                        hintExitPw()
                    }
                }

                true
            }

        }

    }

    private fun initViewPager() {
        vp_home_vp.offscreenPageLimit = mFragmentList.size
        vp_home_vp.adapter = HomeAdapter(mFragmentList, supportFragmentManager)
    }

    private fun initFragment() {
        mFragmentList.add(HomeFragment())
        mFragmentList.add(KnowledgeFragment())
        mFragmentList.add(WeChatFragment())
        mFragmentList.add(NavigationFragment())
        mFragmentList.add(ProjectFragment())
    }

    @SuppressLint("ResourceAsColor")
    private fun initToolbarAndDrawer() {
        //设置 toolbar
        tb_home_tb.run {
            title = getString(R.string.home)
            //setTitleTextAppearance(context,R.style.Toolbar_TitleText)
            //titleColor = R.color.all_bg_white
            setSupportActionBar(this)
        }
        //和抽屉联动
        dl_home_drawer.run {
            val actionBarDrawerToggle = ActionBarDrawerToggle(
                this@HomeActivity, dl_home_drawer, tb_home_tb
                , R.string.navigation_drawer_open, R.string.navigation_drawer_close
            )
            addDrawerListener(actionBarDrawerToggle)
            actionBarDrawerToggle.syncState()
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_home

    override fun setStatusBar() {
        super.setStatusBar()
        ImmersionBar.with(this)
            .fullScreen(true)
            //.transparentBar()
            //.statusBarColor(R.color.blueD)
            //.fitsSystemWindows(true)
            .statusBarDarkFont(false, 0f)
            //.navigationBarAlpha(1)
            .init()
    }
    private var mExitTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                ActivityCollector.instance().finishAll()
            } else {
                mExitTime = System.currentTimeMillis()
                //toast(R.string.exit_tip)
                SmartToast.info(R.string.exit_tip)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_search -> {
                startActivity<SearchActivity>()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}