package com.bignerdranch.travelcommunity.data.network.api

import com.bignerdranch.travelcommunity.data.db.entity.User
import com.bignerdranch.travelcommunity.data.network.model.Model
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.lang.reflect.MalformedParameterizedTypeException

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

// 更新
interface UserService {
  //用户登录
  @GET("api/user")
  fun login(@Query("account")account:String,@Query("password") password:String):Call<Model.BackInfo>

  //注册用户
  @POST("api/user")
  fun register(@Body user: User):Call<Model.BackInfo>

  //用户注销
  @DELETE("api/user/{account}")
  fun logout(@Path("account") account:String):Call<Model.BackInfo>

 //更新用户基本信息，包括头像，兴趣，爱好 职业等等
  @Multipart
  @POST("api/user/{account}")
  fun updateUser(@Path("account") account:String,@PartMap content: Map<String,RequestBody>):Call<Model.BackInfo>

    //查询好友API
    @GET("api/user/{userInfo}")
    fun queryUser(@Path("userInfo") userInfo:String):Call<Model.BackInfo>

    //添加好友

    @POST("api/user/{friendAccount}")
    fun addUserWithBack(@Path("friendAccount") friendAccount:String,
                        @QueryMap addWithBackArgs:Map<String,String>):Call<Model.BackInfo>
   //获取好友列表
    @GET("api/user/lists")
    fun getFriends(@Query("account") account:String):Call<Model.BackInfo>

    //删除好友
    @DELETE("api/user/{friendAccount}")
    fun deleteFriend(@Path("friendAccount") friendAccount: String):Call<Model.BackInfo>
}