package com.bignerdranch.travelcommunity.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
     fun toLogin(account:String,password:String):LiveData<ApiResponse<User>>
      {
          LogUtil.e("toNet")
          return  userService.login(account,password)
      }
     suspend fun toRegister(register: User) = userService.register(register)
     suspend fun toRegister(account: String,password: String) = userService.register(account, password)
     suspend fun toLogout(account:String) = userService.logout(account)
     suspend fun toQueryFriend(userInfo:String) = userService.queryUser(userInfo)
     suspend fun toGetFriends(account: String) = userService.getFriends(account)
     suspend fun toDeleteFriend(friendAccount:String) = userService.deleteFriend(friendAccount)
     suspend fun  toAddUserWithBack(friendAccount:String, addWithBackArgs:Map<String,String>) = userService.addUserWithBack(friendAccount,addWithBackArgs)
     suspend fun toUpdateUser(account: String,content:Map<String, RequestBody>) =  userService.updateUser(account,content)

    //PersonDynamicService
    suspend fun toUploadDynamic(permissionArgs:Map<String,String>,contentsArgs:Map<String,RequestBody>) = personDynamicService.uploadDynamic(permissionArgs,contentsArgs)
    suspend fun toDeleteDynamic(dynamicId:Int) = personDynamicService.deleteDynamic(dynamicId)




    suspend fun toAddLike(likeArgs:Map<String,String>) = personDynamicService.addLike(likeArgs)
    suspend fun toCancelLike(likeId: Int) = personDynamicService.cancelLike(likeId)
    suspend fun toQueryLike(queryLikeArgs:Map<String,String>) = personDynamicService.queryLike(queryLikeArgs)
    suspend fun toComments(message:String,commentsArgs:Map<String,String>) = personDynamicService.comments(message,commentsArgs)
    suspend fun toDeleteComments(id:Int) = personDynamicService.deleteComments(id)
    suspend fun toQueryComments(queryCommentsArgs:Map<String,String>) = personDynamicService.queryComments(queryCommentsArgs)


    suspend fun toQueryDynamics(queryDynamicArgs:Map<String,String>) = personDynamicService.queryDynamics(queryDynamicArgs)



    companion object{
      @Volatile  private var network:Network? = null
      fun getInstance():Network{


          return network?: synchronized(this){
               network?: Network()
          }
      }

    }
}