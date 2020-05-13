package com.bignerdranch.tclib.data.network

import androidx.lifecycle.LiveData
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.data.db.entity.CommentsMsg
import com.bignerdranch.tclib.data.db.entity.User
import com.bignerdranch.tclib.data.network.api.PersonDynamicService
import com.bignerdranch.tclib.data.network.api.UserService
import com.bignerdranch.tclib.data.network.model.ApiResponse
import com.google.gson.Gson
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
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
      fun toLogin(account:String,password:String):LiveData<ApiResponse<User>> {
          println("asdadasdada")
        return   userService.login(account, password)
      }

        fun toRegister(register: User) = userService.register(register)
        fun toRegister(account: String,password: String) = userService.register(account, password)
        fun toLogout(account:String) = userService.logout(account)
        fun toQueryFriend(userInfo:String) = userService.queryUsers(userInfo)
        fun toQueryFriend(userId:Int) = userService.queryUser(userId)
        fun toQueryFriendList(userId: Int) = userService.getFriendList(userId)
        fun toDeleteFriend(userId: Int) = userService.deleteFriend(userId)
        fun  toAddUserWithBack(requestArgs: HashMap<String, Any>) = userService.addUserWithBack(requestArgs)
        fun toUpdateUser(user:User,content:HashMap<String, RequestBody>) =  userService.updateUser(user,content)


          //PersonDynamicService
          suspend fun toAddDynamic(permissionArgs:HashMap<String,Any>,contentsArgs:HashMap<String,RequestBody>) = personDynamicService.addDynamic(contentsArgs)
          fun toDeleteDynamic(dynamicId: Int) = personDynamicService.deleteDynamic(dynamicId)
           fun toQueryDynamics(queryDynamicArgs:HashMap<String,Any>) = personDynamicService.queryDynamics(queryDynamicArgs)
          fun toAddLike(likeArgs:HashMap<String,Any>) = personDynamicService.addLike(likeArgs)
          fun toDeleteLike(likeArgs:HashMap<String,Any>) = personDynamicService.deleteLike(likeArgs)
          fun toQueryLike(queryLikeArgs:HashMap<String,Any>) = personDynamicService.queryLike(queryLikeArgs)
          fun toAddComments(commentsMsg: CommentsMsg) = personDynamicService.addComments(commentsMsg)
          fun toDeleteComments(dynamicId:Int) = personDynamicService.deleteComments(dynamicId)
          fun toQueryComments(queryCommentsArgs:HashMap<String,Any>) = personDynamicService.queryComments(queryCommentsArgs)
          fun toDeleteComment(commentsId:Int) = personDynamicService.deleteComment(commentsId)


    fun  <T> fromToJson( json:String,listType:Type):T?{
        val gson =  Gson()
        var t:T? = null
        t = gson.fromJson(json,listType)
        return t
    }

          private suspend fun <T> Call<T>.await(): T {
              return suspendCoroutine { continuation ->

                  enqueue(object : Callback<T> {
                      override fun onFailure(call: Call<T>, t: Throwable) {
                          continuation.resumeWithException(t)
                          LogUtil.e("error ${t.message}")
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