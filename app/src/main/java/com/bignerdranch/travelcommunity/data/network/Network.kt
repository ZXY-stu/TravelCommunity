package com.bignerdranch.travelcommunity.data.network

import com.bignerdranch.travelcommunity.data.db.entity.User
import com.bignerdranch.travelcommunity.data.network.api.CommentsService
import com.bignerdranch.travelcommunity.data.network.api.LikeService
import com.bignerdranch.travelcommunity.data.network.api.PersonDynamicService
import com.bignerdranch.travelcommunity.data.network.api.UserService
import com.bignerdranch.travelcommunity.data.network.model.Model
import com.bignerdranch.travelcommunity.util.LogUtil
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.QueryMap
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
     private val commentsService = ServiceCreator.create(CommentsService::class.java)
     private val likeService = ServiceCreator.create(LikeService::class.java)
     private val personDynamicService = ServiceCreator.create(PersonDynamicService::class.java)

      //userService
     suspend fun toLogin(account:String,password:String) = userService.login(account,password).await()
     suspend fun toRegister(register: User) = userService.register(register).await()
     suspend fun toLogout(account:String) = userService.logout(account).await()
     suspend fun toQueryFriend(userInfo:String) = userService.queryUser(userInfo).await()
     suspend fun toGetFriends(account: String) = userService.getFriends(account).await()
     suspend fun toDeleteFriend(friendAccount:String) = userService.deleteFriend(friendAccount).await()
     suspend fun  toAddUserWithBack(friendAccount:String, addWithBackArgs:Map<String,String>) = userService.addUserWithBack(friendAccount,addWithBackArgs).await()
     suspend fun toUpdateInfo(account: String,content:Map<String, RequestBody>) =  userService.updateUser(account,content).await()

    //PersonDynamicService

    suspend fun toAddDynamic(permissionArgs:Map<String,String>,contentsArgs:Map<String,RequestBody>) = personDynamicService.uploadDynamic(permissionArgs,contentsArgs).await()
    suspend fun toDeleteDynamic(dynamicId:Int) = personDynamicService.deleteDynamic(dynamicId).await()
    suspend fun toQueryDynamics(queryDynamicArgs:Map<String,String>) = personDynamicService.queryDynamics(queryDynamicArgs).await()

   //LikeService
    suspend fun toAddLike(likeArgs:Map<String,String>) = likeService.addLike(likeArgs).await()
    suspend fun toCancelLike(dynamicId: Int) = likeService.cancelLike(dynamicId).await()
    suspend fun toQueryLike(queryLikeArgs:Map<String,String>) = likeService.queryLike(queryLikeArgs).await()

   //CommentsService

    suspend fun toComments(message:String,commentsArgs:Map<String,String>) = commentsService.comments(message,commentsArgs).await()
    suspend fun toDeleteComments(dynamicId:Int) = commentsService.deleteComments(dynamicId).await()
    suspend fun toQueryComments(queryCommentsArgs:Map<String,String>) = commentsService.queryComments(queryCommentsArgs).await()

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