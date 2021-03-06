package com.bignerdranch.tclib.data.network

import android.content.Context
import androidx.lifecycle.LiveData
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.data.db.entity.CommentsMsg
import com.bignerdranch.tclib.data.db.entity.User
import com.bignerdranch.tclib.data.network.api.PersonDynamicService
import com.bignerdranch.tclib.data.network.api.UserService
import com.bignerdranch.tclib.data.network.model.ApiResponse
import com.bignerdranch.tclib.utils.StringUtils
import com.google.gson.Gson
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.HashMap
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

class Network private constructor(context: Context){


    //initial Retrofit
     init {
       ServiceCreator.buildRetrofit(context)
     }
    //initial Services
     private val userService =  ServiceCreator.create(UserService::class.java)
     private val personDynamicService = ServiceCreator.create(PersonDynamicService::class.java)

      //userService
       fun toLogin(account:String,password:String) = userService.login(account, password)


        fun toRegister(account: String,password: String):LiveData<ApiResponse<User>>{
            val map = HashMap<String,Any>()
            map["account"] = account
            map["nickName"] = ""
            map["phoneNumber"] = ""
            map["password"] = password
            map["lastLoginTime"] = StringUtils.getDateTime()
           return  userService.register(map)
        }
        fun toLogout(account:String) = userService.logout(account)
        fun toQueryFriend(userInfo:String) = userService.queryUsers(userInfo)
        fun toQueryFriend(userId:Int) = userService.queryUser(userId)
        fun toQueryFriendList(userId: Int) = userService.getFriendList(userId)
        fun toDeleteFriend(userId: Int) = userService.deleteFriend(userId)
        fun  toAddUserWithBack(requestArgs: HashMap<String, Any>) = userService.addUserWithBack(requestArgs)
        fun toUpdateUser(contentsArgs:HashMap<String, RequestBody>) =  userService.updateUser(contentsArgs)


          //PersonDynamicService
          suspend fun toAddDynamic(permissionArgs:HashMap<String,Any>,contentsArgs:HashMap<String,RequestBody>) =
              personDynamicService.addDynamic(permissionArgs,contentsArgs)

          fun toDeleteDynamic(dynamicId: Int) = personDynamicService.deleteDynamic(dynamicId)


         suspend  fun toQueryDynamics(queryDynamicArgs:HashMap<String,Any>) = personDynamicService.queryDynamics(queryDynamicArgs)
          fun toAddLike(likeArgs:HashMap<String,Any>) = personDynamicService.addLike(likeArgs)
          fun toDeleteLike(likeArgs:HashMap<String,Any>) = personDynamicService.deleteLike(likeArgs)
          fun toQueryLike(queryLikeArgs:HashMap<String,Any>) = personDynamicService.queryLike(queryLikeArgs)
          fun toAddComments(commentsMsg: CommentsMsg):LiveData<ApiResponse<Any>> {
              val map = HashMap<String,Any>()
              map["dynamicId"] = commentsMsg.dynamicId
              map["userId"] = commentsMsg.userId
              map["userNickName"] = commentsMsg.userNickName
              map["friendNickName"] = commentsMsg.friendNickName
              map["msg"] = commentsMsg.msg
              map["times"] = commentsMsg.times
              map["commentGroupId"] = commentsMsg.commentGroupId
              return   personDynamicService.addComments(map)
          }
    /*
      *  @PrimaryKey override val id:Int = 0, //评论id
@ColumnInfo(name = "dynamic_id")   val dynamicId:Int = 1, //所属动态
@ColumnInfo(name = "user_id")      val userId:Int = 1, //评论人id
val userNickName:String = "123",   //评论人的昵称
val friendNickName:String = "123",  //被评论人的昵称。
val msg:String ="", //消息内容
val times: String = StringUtils.getDateTime(),
val commentGroupId:Int = 0, // 评论组id    0表示属于 评论作者组  其它值表示 该条评论属于commentGroupId这条评论组
// 通过commentGroupId 可以统计该条评论的被评论数  0时可以统计该条动态的被评论数
// val userAccount:String = "",//评论人的账户
val likeCount:String ="",// 点赞统计
val commentsCount:String=""// 评论统计
      * */
          fun toDeleteComments(dynamicId:Int) = personDynamicService.deleteComments(dynamicId)
          fun toQueryComments(queryCommentsArgs:HashMap<String,Any>) = personDynamicService.queryComments(queryCommentsArgs)
          fun toDeleteComment(commentsId:Int) = personDynamicService.deleteComment(commentsId)




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
//Cookie: JSESSIONID=231CE0F6D2BA3E8899B025BB8CB0C229

          companion object{
          @Volatile  private var network:Network? = null
          fun getInstance(context:Context):Network{
              return network?: synchronized(this){
                  network?: Network(context)
              }
          }

      }

}