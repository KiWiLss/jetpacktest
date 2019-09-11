package com.kiwilss.lxkj.zhihu

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class BaseVM : ViewModel(){
    override fun onCleared() {
        super.onCleared()
        //父类啥也不用做
    }
}


class User: BaseVM(){
    fun login(){

        viewModelScope.launch {

        }

    }

    @SuppressLint("SetTextI18n")
    public fun test(context: Context, tv: TextView){

    }
}