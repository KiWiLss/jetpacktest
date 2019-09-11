package com.kiwilss.lxkj.jetpacktest.lifecycler

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.kiwilss.lxkj.jetpacktest.R
import com.kiwilss.lxkj.jetpacktest.livedata.TestViewModel
import com.kiwilss.lxkj.jetpacktest.scope.User
import com.kiwilss.lxkj.jetpacktest.viewmodel.ContextViewModel
import com.kiwilss.lxkj.jetpacktest.work.TestWorker
import kotlinx.android.synthetic.main.activity_lifecycler.*


/**
 * @author : Lss kiwilss
 * @FileName: LifecyclerActivity
 * @e-mail : kiwilss@163.com
 * @time : 2019-08-15
 * @desc : {DESCRIPTION}
 */
class LifecyclerActivity : AppCompatActivity() {

//     var lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)
//    override fun getLifecycle(): Lifecycle {
//        return lifecycleRegistry
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycler)

        //lifecycler 测试
        var myObserver = MyObserver(lifecycle,object: Callback{
            override fun update(string: String) {
                Log.e("MMM", ": $string" );
            }
        })
        lifecycle.addObserver(myObserver)

        //lifecycle.removeObserver(myObserver)
        //viewmodel 测试
//        val model = ViewModelProviders.of(this)[Model::class.java]
//
//        tv_lifecycler_model.text = model.textName
//
//        btn_lifecycler_change.setOnClickListener {
//            model.textName = "Change = 22222"
//            tv_lifecycler_model.text = model.textName
//        }
        //viewmodel 有 context 参数
        val contextViewModel = ViewModelProviders.of(this)[ContextViewModel::class.java]
        tv_lifecycler_model.text = contextViewModel.textName
        btn_lifecycler_change.setOnClickListener {
            contextViewModel.textName = "Change = 22222"
            tv_lifecycler_model.text = contextViewModel.textName
        }
    //livedata 测试
    //1,创建观察者对象
    val observer = Observer<String>{
        tv_lifecycler_model.text = it
    }
    val testViewModel = ViewModelProviders.of(this)[TestViewModel::class.java]
    //订阅观察
    testViewModel.mCurrent!!.observe(this,observer)
//    testViewModel.mCurrent!!.observe(this, Observer {
//
//    })



        val vm = User()

    btn_lifecycler_livedata.setOnClickListener {
        //testViewModel.mCurrent!!.value = "aaaaaaa"
        vm.test(this,tv_lifecycler_model)
    }

    //workmanager
    val build = OneTimeWorkRequest.Builder(TestWorker::class.java).build()


    btn_lifecycler_work.setOnClickListener {
        WorkManager.getInstance(this).enqueue(build)
    }
    //网络测试


    btn_lifecycler_net.setOnClickListener {

        //vm.login3()

        vm.exit()

    }


    


    //结果获取
    vm.result.observe(this, Observer {
        Log.e("MMM", "activity : $it" )
        it?.run {
            tv_lifecycler_model.text = toString()
        }
    })

    vm.error.observe(this, Observer {
        Log.e("MMM", "activity : $it" )
    })

    vm.beanResult.observe(this, Observer {
        Log.e("MMM", "--------:------$it ")
    })

    btn_lifecycler_next.setOnClickListener {
        startActivity(Intent(this,LifecyclerActivity::class.java))
    }
}
}
