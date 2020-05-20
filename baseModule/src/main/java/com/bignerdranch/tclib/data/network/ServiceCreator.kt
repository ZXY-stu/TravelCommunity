package com.bignerdranch.tclib.data.network


import android.content.Context
import com.bignerdranch.tclib.data.network.callAdapter.LiveDataCallAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

    private const val BASE_URL = "http://lyndon.fun:8080/"
    private  var context:Context? = null
    private lateinit var okHttpBuilder:OkHttpClient.Builder
    private lateinit var retrofit:Retrofit


    private fun okHttpBuilder():OkHttpClient.Builder =
        OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
        .cookieJar(CookieManger(context))
        .connectTimeout(60, TimeUnit.SECONDS)

/*
* OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addNetworkInterceptor(
                    new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .cookieJar(new CookieManger(context))
            .addInterceptor(loginInterceptor)
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)

* */


    fun buildRetrofit(context: Context){
        if(this.context==null) {
            this.context = context
            okHttpBuilder = okHttpBuilder()
            retrofit = retrofitBuild().build()
        }
    }


    private fun retrofitBuild():Retrofit.Builder =
         Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpBuilder.build())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())


    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

}