package com.bignerdranch.tclib.data.network


import com.bignerdranch.tclib.data.network.callAdapter.LiveDataCallAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/



object ServiceCreator {
    //http://lyndon.fun:8080/api/download?filePath=group1/M00/00/00/L2ZgWV6L2--EN0TQAAAAAIzB8-8381.jpg
    private const val BASE_URL = "http://lyndon.fun:8080/"

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)


    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient.build())
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())

    private val retrofit = builder.build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

}