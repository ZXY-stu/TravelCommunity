package com.bignerdranch.travelcommunity.data.network

import com.bignerdranch.travelcommunity.data.db.entity.User
import com.bignerdranch.travelcommunity.data.network.api.UserService
import com.bignerdranch.travelcommunity.data.network.model.Model
import com.bignerdranch.travelcommunity.util.LogUtil
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

class Network private constructor(){

     private val loginService =  ServiceCreator.create(UserService::class.java)

     suspend fun toLogin(account:String,password:String): User {
         LogUtil.w("在网络")
         return loginService.login(account,password).await()
     }

    suspend fun toRegister(register: User) = loginService.register(register).await()

    suspend fun toLogout(account:String):Model.BackInfo = loginService.logout(account).await()

    suspend fun toUpdateInfo(account: String,content:Map<String, RequestBody>) : Model.BackInfo{

        /*
        * 进行RequestBody对象相关处理
        * */
         return loginService.updateUser(account,content).await()
    }


    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    //如果出错，抛出错误
                    LogUtil.w("在错误")
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        continuation.resume(body)
                        LogUtil.w("正确")
                    }
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }

    companion object{
      @Volatile  private var network:Network? = null

      fun getInstance():Network{
          return network?: synchronized(this){
               network?: Network()
          }
      }

    }
}