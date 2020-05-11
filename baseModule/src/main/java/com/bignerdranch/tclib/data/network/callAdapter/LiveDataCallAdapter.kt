package com.bignerdranch.tclib.data.network.callAdapter

import androidx.lifecycle.LiveData
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.data.network.model.ApiResponse

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter<T>(private val responseType: Type) : CallAdapter<T, LiveData<T>> {
    override fun adapt(call: Call<T>): LiveData<T> {
        return object : LiveData<T>() {
            private val started = AtomicBoolean(false)

            override fun onActive() {
                super.onActive()
                println("收到个 ")
                if (started.compareAndSet(false, true)) {//确保执行一次
                    call.enqueue(object : Callback<T> {
                        override fun onFailure(call: Call<T>, t: Throwable) {

                         LogUtil.e("收到111  ${t.message}")
                          val value = ApiResponse<T>(
                               null,
                               -1,
                               t.message ?: ""
                           ) as T
                           postValue(value)
                        }

                        override fun onResponse(call: Call<T>, response: Response<T>) {
                            println(""+responseType + " 132  " + response.body())
                            postValue(response.body())

                        }
                    })
                }
            }
        }
    }

    override fun responseType() = responseType
}