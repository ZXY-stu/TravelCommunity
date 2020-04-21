package com.bignerdranch.travelcommunity.data.network.api

import androidx.lifecycle.LiveData
import com.bignerdranch.travelcommunity.data.db.entity.User
import com.bignerdranch.travelcommunity.data.db.entity.UserRelation
import com.bignerdranch.travelcommunity.data.network.model.ApiResponse
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


interface UserService {

    // 登陆和注册 都需要返回一个ApiResponse<User>
    //用户登录
    @GET("api/login")
    fun login(@Query("account")account:String,@Query("password") password:String):LiveData<ApiResponse<String>>

    //注册用户
    @POST("api/user")
    fun register(@Body user: User):LiveData<ApiResponse<User>>

    //注册用户
    @FormUrlEncoded
    @POST("api/user")
    fun register(@Field("account") account: String,@Field("password") password: String):LiveData<ApiResponse<User>>

    //用户注销
    @DELETE("api/user/{account}")
    fun logout(@Path("account") account:String)

    //更新用户基本信息，包括头像，兴趣，爱好 职业等等
    @Multipart
    @POST("api/user/{account}")
    fun updateUser(@Path("account") account:String,@PartMap content: Map<String,RequestBody>)

    //查询好友API
    @GET("api/user/{userInfo}")
    fun queryUsers(@Path("userInfo") userInfo:String):LiveData<ApiResponse<List<User>>>

    //添加好友
    @POST("api/user/{friendAccount}")
    fun addUserWithBack(@Path("friendAccount") friendAccount:String,
                        @QueryMap addWithBackArgs:Map<String,String>)
   //获取好友列表
    @GET("api/user/lists")
    fun getFriendList(@Query("account") account:String):LiveData<ApiResponse<List<UserRelation>>>

    //删除好友
    @DELETE("api/user/{friendAccount}")
    fun deleteFriend(@Path("friendAccount") friendAccount: String)



}