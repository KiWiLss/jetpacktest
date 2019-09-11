package com.kiwilss.lxkj.jetpacktest.utils

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitHelper {

    val apiService by lazy {
        getService(ApiService::class.java, ApiService.BASE_URL)
    }


    private fun <S> getService(serviceClass: Class<S>, baseUrl: String): S {

        return Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            //.addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(baseUrl)
            .build().create(serviceClass)

    }

}