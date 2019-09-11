package com.kiwilss.lxkj.jetpacktest.scope

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiwilss.lxkj.jetpacktest.utils.BaseBean
import com.kiwilss.lxkj.jetpacktest.utils.LoginInfo
import com.kiwilss.lxkj.jetpacktest.utils.NETConnectUtils
import com.kiwilss.lxkj.jetpacktest.utils.RetrofitHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

open class BaseVM : ViewModel(){

    val error = MutableLiveData<Any>()


    override fun onCleared() {
        super.onCleared()
        //父类啥也不用做
    }

    //统一处理请求结果
    fun handlerResult(api: suspend CoroutineScope.() -> Unit){
        viewModelScope.launch {
            try {
                 api()
            }catch (e: Exception){//连接失败,判断网络
                Log.e("MMM", ": $e" )
//                if (NETConnectUtils.isConnected(context)){
//
//                }else{
//
//                }
            }
        }

    }

    suspend fun <T>executeResponse(response: BaseBean<T>,isBean: Boolean = false,
                                   errorBlock:  suspend CoroutineScope.() -> Unit
                                    = { error.value = response.errorMsg },
                                   beanBlock: suspend CoroutineScope.() -> Unit = {},
                                successBlock:  suspend CoroutineScope.() -> Unit
                                ) {
      coroutineScope {
          if (isBean){
              beanBlock()
          }else{
              if (response.errorCode == 0){
                  successBlock()
              }else{
                  errorBlock()
              }
          }
      }


    }

}


class User: BaseVM(){
    val result = MutableLiveData<Any>()
    //val error = MutableLiveData<Any>()
    private val repository by lazy { LoginRepository() }

    fun login(){
        viewModelScope.launch {
            val map = hashMapOf<String,String>()
            map["phone"] = "18657194104"
            map["password"] = "123456"
            //map.put("loginIp", Utils.getIPAddress(this));
            map["deviceType"] = "ANDROID"
            val login: Any? =  RetrofitHelper.apiService.login(map)
            Log.e("MMM", ": $login" )
          //result.value = login
        }
    }
     val beanResult = MutableLiveData<BaseBean<LoginInfo>>()

    fun login3(){
        handlerResult {
            val baseBean = repository.login("kiwilss", "123456")
            executeResponse(baseBean,true,beanBlock = {beanResult.value = baseBean }) {
                result.value = baseBean.data
            }
        }
    }

    fun exit(){
        handlerResult {
            val result = repository.exit()
            Log.e("MMM", ": ------$result");
        }
    }

    fun login2(context: Context){
        viewModelScope.launch {
            try {

                val baseBean = repository.login("kiwilss", "123456")
                //val baseBean = RetrofitHelper.apiService.login("kiwilss", "123456")
                Log.e("MMM", ": $baseBean")

                if (baseBean.errorCode == 0) {
                    result.value = baseBean
                }else{
                    error.value = baseBean.errorMsg
                }
            }catch (e: Exception){
                Log.e("MMM", "exception--: ${e.message}")
                if (NETConnectUtils.isConnected(context)){
                    error.value = "未知 error"
                }else{
                    error.value = "net error"
                }
            }


        }

    }

    @SuppressLint("SetTextI18n")
    public fun test(context: Context, tv: TextView){
        viewModelScope.launch {
            Toast.makeText(context,"test",Toast.LENGTH_SHORT).show()
            tv.text = "test hello"
        }
    }
}