package com.bignerdranch.tclib.data.network.api

import androidx.lifecycle.LiveData
import com.bignerdranch.tclib.data.db.entity.User
import com.bignerdranch.tclib.data.db.entity.UserRelation
import com.bignerdranch.tclib.data.network.model.ApiResponse

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/


interface UserService {

    // 登陆和注册 返回ApiResponse<User>
    //用户登录
    @GET("api/login")
    fun login(@Query("account")account:String,@Query("password") password:String):LiveData<ApiResponse<User>>

    //注册用户   返回ApiResponse<User>
    @POST("api/user/register")
    fun register(@Body user: User):LiveData<ApiResponse<User>>

    //注册用户   返回ApiResponse<User>
    @FormUrlEncoded
    @POST("api/user/register")
    fun register(@Field("account") account: String,@Field("password") password: String):LiveData<ApiResponse<User>>

    //用户注销
    @DELETE("api/user/logout")
    fun logout(@Query("account") account:String):LiveData<ApiResponse<Any>>

    //更新用户基本信息，包括头像，兴趣，爱好 职业等等
    // user为用户基本信息
    // content为用户的头像或者背景图片文件
    // 处理好图片路径，返回更新后的User
    @Multipart
    @POST("api/user/update")
    fun updateUser(@Body user:User, @PartMap content: HashMap<String,RequestBody>):LiveData<ApiResponse<User>>
    //    fun updateUser(@PartMap content: Map<String,RequestBody>)


    //查询好友API
    //返回查询到的一个或多个User
    @GET("api/user/queryList/{userInfo}")
    fun queryUsers(@Path("userInfo")  userInfo:String):LiveData<ApiResponse<List<User>>>

    //  添加好友
    // 也表示关注好友
    // friendId  对方用户
    // groupId  分组id
    // isRequireUser  是否为发起人 0是 1否
    // memo  添加好友备注内容
    // 若被添加的user的privateMode为0  表示开放，申请就可通过
    // 若被添加的user的privateMode为1  表示私密，需等待用户确认之后，才可添加
    // 用户发起的请求都存入AddFriendRecord表中
    @POST("api/user/addFriend")
    fun addUserWithBack(@FieldMap addWithBackArgs:HashMap<String,Any>):LiveData<ApiResponse<Any>>


   //获取好友列表
    @GET("api/user/query/friendLists")
    fun getFriendList(@Query("userId") userId: Int):LiveData<ApiResponse<List<UserRelation>>>

    //查询指定好友
    @GET("api/user/query/{userId}")
    fun queryUser(@Path("userId") userId:Int):LiveData<ApiResponse<User>>

    //删除好友
    //取消关注
    @DELETE("api/user/delete/{userId}")
    fun deleteFriend(@Path("userId") userId: Int):LiveData<ApiResponse<Any>>

}