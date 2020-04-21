package com.bignerdranch.travelcommunity.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.travelcommunity.data.db.entity.AddDynamicArgs
import com.bignerdranch.travelcommunity.data.db.entity.CommentsMsg
import com.bignerdranch.travelcommunity.data.db.entity.PersonDynamic
import com.bignerdranch.travelcommunity.data.db.entity.User
import com.bignerdranch.travelcommunity.data.network.api.PersonDynamicService
import com.bignerdranch.travelcommunity.data.network.api.UserService
import com.bignerdranch.travelcommunity.data.network.model.ApiResponse
import com.bignerdranch.travelcommunity.util.LogUtil
import okhttp3.RequestBody
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


     //initial Services
     private val userService =  ServiceCreator.create(UserService::class.java)
     private val personDynamicService = ServiceCreator.create(PersonDynamicService::class.java)

      //userService
      fun toLogin(account:String,password:String) = userService.login(account,password)
      fun toRegister(register: User) = userService.register(register)
      fun toRegister(account: String,password: String) = userService.register(account, password)
      fun toLogout(account:String) = userService.logout(account)
      fun toQueryFriend(userInfo:String) = userService.queryUsers(userInfo)
      fun toQueryFriendList(account: String) = userService.getFriendList(account)
      fun toDeleteFriend(friendAccount:String) = userService.deleteFriend(friendAccount)
      fun  toAddUserWithBack(friendAccount:String, addWithBackArgs:Map<String,String>) = userService.addUserWithBack(friendAccount,addWithBackArgs)
      fun toUpdateUser(account: String,content:Map<String, RequestBody>) =  userService.updateUser(account,content)


    //PersonDynamicService
    suspend fun toAddDynamic(contentsArgs:HashMap<String,RequestBody>) = personDynamicService.addDynamic(contentsArgs).await()
     fun toDeleteDynamic(dynamicId:Int) = personDynamicService.deleteDynamic(dynamicId)
     fun toQueryDynamics(queryDynamicArgs:Map<String,String>) = personDynamicService.queryDynamics(queryDynamicArgs)
     fun toAddLike(likeArgs:Map<String,String>) = personDynamicService.addLike(likeArgs)
     fun toDeleteLike(likeId: Int) = personDynamicService.deleteLike(likeId)
     fun toQueryLike(queryLikeArgs:Map<String,String>) = personDynamicService.queryLike(queryLikeArgs)
     fun toAddComments(commentsMsg: CommentsMsg) = personDynamicService.addComments(commentsMsg)
     fun toDeleteComments(id:Int) = personDynamicService.deleteComments(id)
     fun toQueryComments(queryCommentsArgs:Map<String,String>) = personDynamicService.queryComments(queryCommentsArgs)

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
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