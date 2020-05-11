package com.bignerdranch.tclib.data.network.callAdapter

import androidx.lifecycle.LiveData
import com.bignerdranch.tclib.data.network.model.ApiResponse
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author zhongxinyu
 * @date 2020/4/4
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData::class.java) return null
        //获取第一个泛型类型
        val observableType =
            getParameterUpperBound(
                0,
                returnType as ParameterizedType
            )
        val rawType = getRawType(observableType)
        /*if (rawType != ApiResponse::class.java) {
            throw IllegalArgumentException("type must be ApiResponse")
        }*/
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }
        return LiveDataCallAdapter<Any>(
            observableType
        )
    }
}