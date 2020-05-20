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

    // 所有请求
    // 成功设置errorCode = 0
    // 失败设置errorCode = 1


    //用户登录
    @GET("api/login")
    fun login(@Query("account")account:String,@Query("password") password:String):LiveData<ApiResponse<User>>

    //注册用户   注册后，服务器自动登陆  返回登陆后的User
    @FormUrlEncoded
    @POST("api/user/register")
    fun register(@Field("account") account: String,@Field("password") password: String):LiveData<ApiResponse<User>>

    //用户注销
    @DELETE("api/user/logout")
    fun logout(@Query("account") account:String):LiveData<ApiResponse<Any>>

    //更新用户基本信息，包括头像，兴趣，爱好 职业等等
    // user为用户基本信息
    // contentsArgs为用户的头像和背景图片文件
    // name = backgroundImageUrl  用户背景图片
    // name = headPortraitUrl  用户头像
    // user实体类  包含用户其它文字信息
    // 图片处理后，更新User信息
    @Multipart
    @POST("api/user/update")
    fun updateUser(@PartMap contentsArgs: HashMap<String,RequestBody>):LiveData<ApiResponse<User>>


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
    // 若被添加的user的privateMode为0  表示对外开放，申请直接通过
    // 若被添加的user的privateMode为1  表示私密，服务器需通知对方用户，对方确认之后，才可添加 暂时可以不实现这个功能
    // 更新AddFriendRecord表
    @FormUrlEncoded
    @POST("api/user/addFriend")
    fun addUserWithBack(@FieldMap addWithBackArgs:HashMap<String,Any>):LiveData<ApiResponse<Any>>


    //获取好友列表 返回该用户的好友列表
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