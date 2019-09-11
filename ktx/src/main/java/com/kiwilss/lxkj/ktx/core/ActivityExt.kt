package com.kiwilss.lxkj.ktx.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders


/**
 * Description: Activity相关
 * Create by lxj, at 2018/12/7
 */

inline fun <reified T> Fragment.startActivity(flag: Int = -1, bundle: Array<out Pair<String, Any?>>? = null) {
    activity?.startActivity<T>(flag, bundle)
}

inline fun <reified T> Fragment.startActivityForResult(flag: Int = -1, bundle: Array<out Pair<String, Any?>>? = null, requestCode: Int = -1) {
    activity?.startActivityForResult<T>(flag, bundle, requestCode)
}

inline fun <reified T> Context.startActivity(flag: Int = -1, bundle: Array<out Pair<String, Any?>>? = null, requestCode: Int = -1) {
    val intent = Intent(this, T::class.java).apply {
        if (flag != -1) {
            this.addFlags(flag)
        } else if (this !is Activity) {
            this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (bundle != null) bundle.toBundle()?.let { putExtras(it) }
    }
    startActivity(intent)
}

inline fun <reified T> View.startActivity(flag: Int = -1, bundle: Array<out Pair<String, Any?>>? = null) {
    context.startActivity<T>(flag, bundle)
}

inline fun <reified T> View.startActivityForResult(flag: Int = -1, bundle: Array<out Pair<String, Any?>>? = null, requestCode: Int = -1) {
    (context as Activity).startActivityForResult<T>(flag, bundle, requestCode)
}

inline fun <reified T> Activity.startActivityForResult(flag: Int = -1, bundle: Array<out Pair<String, Any?>>? = null, requestCode: Int = -1) {
    val intent = Intent(this, T::class.java).apply {
        if (flag != -1) {
            this.addFlags(flag)
        }
        if (bundle != null) bundle.toBundle()?.let { putExtras(it) }
    }
    startActivityForResult(intent, requestCode)
}

//fun AppCompatActivity?.finishDelay(delay: Long = 1) {
//    this?.run {
//        LifecycleHandler(this).postDelayed({ finish() }, delay)
//    }
//}
//
////post, postDelay
//fun AppCompatActivity?.post(action: ()->Unit){
//    LifecycleHandler(this).post { action() }
//}
//
//fun AppCompatActivity?.postDelay(delay:Long = 0, action: ()->Unit){
//    LifecycleHandler(this).postDelayed({ action() }, delay)
//}

//view model
fun <T: ViewModel> AppCompatActivity.getVM(clazz: Class<T>) = ViewModelProviders.of(this).get(clazz)