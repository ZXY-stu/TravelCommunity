package com.bignerdranch.travelcommunity.data.network

import com.bignerdranch.travelcommunity.data.network.api.UserService
import com.bignerdranch.travelcommunity.data.network.model.Model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class Network {

     private val loginService =  ServiceCreator.create(UserService::class.java)

     suspend fun toLogin(login: Model.UserLogin):Model.UserInfo{
         val loginInfo = HashMap<String,String>()
         loginInfo["account"] = login.account
         loginInfo["password"] = login.password
         return loginService.login(loginInfo).await()
     }

    suspend fun toRegister(register: Model.UserRegister):Model.UserLogin = loginService.register(register).await()

    suspend fun toLogout(account:String):Model.Info = loginService.logout(account).await()




    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    //如果出错，抛出错误
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
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