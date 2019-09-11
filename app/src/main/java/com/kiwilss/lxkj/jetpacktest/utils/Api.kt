/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: Api
 * Author:   kiwilss
 * Date:     2019-08-16 11:35
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.jetpacktest.utils

import retrofit2.http.*

/**
 *@FileName: Api
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-08-16
 * @desc   : {DESCRIPTION}
 */
interface ApiService{
    companion object{
        const val BASE_URL = "https://www.wanandroid.com/"

        const val HOST_TEST = "https://lepaytest.weemang.com/"
    }

    @GET("vm.open/mch/login")
    suspend fun login(@QueryMap map: Map<String, String>): Any?

    /**
     * 登录
     * http://www.wanandroid.com/user/login
     * @param username
     * @param password
     */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username")username: String,
                      @Field("password")password: String): BaseBean<LoginInfo>


    @GET("user/logout/json")
    suspend fun exit(): BaseBean<Any>
}