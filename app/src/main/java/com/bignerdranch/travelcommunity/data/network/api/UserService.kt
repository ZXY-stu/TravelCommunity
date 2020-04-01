package com.bignerdranch.travelcommunity.data.network.api

import com.bignerdranch.travelcommunity.data.network.model.Model
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface UserService {
  //用户登录
  @GET("api/user")
  fun login(@QueryMap loginInfo:Map<String,String>):Call<Model.UserInfo>

  //注册用户
  @POST("api/user")
  fun register(@Body userRegister: Model.UserRegister):Call<Model.UserLogin>

  //用户注销
  @DELETE("api/user/{account}")
  fun logout(@Path("account") account:String):Call<Model.Info>

 //更新用户基本信息，包括头像，兴趣，爱好 职业等
  @Multipart
  @PUT("api/user/{account}")
  fun updateUser(@Path("account") account:String,@PartMap content: Map<String,RequestBody>):Call<Model.Info>
}