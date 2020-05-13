package com.bignerdranch.tclib.data.network.api

import androidx.lifecycle.LiveData
import com.bignerdranch.tclib.data.db.entity.CommentsMsg
import com.bignerdranch.tclib.data.db.entity.Like
import com.bignerdranch.tclib.data.db.entity.PersonDynamic
import com.bignerdranch.tclib.data.network.model.ApiResponse

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


/**
 * @author zhongxinyu
 * @date 2020/4/3
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
interface PersonDynamicService {

    /* 动态发表
    *  permissionArgs  权限列表
    *  userId 发表人Id
    *  permissionId  隐私设置
    *  0 表示开放
    *  1 表示仅关注的人可见
    *  2 表示自定义，需通过查询权限表获取访问权限
    *  3 表示私密，仅自己可见
    *  userList     需要处理权限的用户列表，处理后，存入UserRelation
    *
    * contentArgs  动态内容列表
    * textContent  动态的文本内容
    * imageFiles   动态的图片文件 最多9张
    * videoFile   动态的视频文件
    * */
 /* @Multipart
    @POST("api/user/dynamic")
    fun addDynamic(
     @PartMap permissionArgs:HashMap<String,Any>
     ,@PartMap contentsArgs: HashMap<String, RequestBody>
    ): LiveData<ApiResponse<PersonDynamic>>
*/

    @Multipart
    @POST("api/user/dynamic")
    fun addDynamic(
        @PartMap contentsArgs: HashMap<String, RequestBody>
    ): LiveData<ApiResponse<PersonDynamic>>


    //删除动态
    @DELETE("api/user/dynamic")
    fun deleteDynamic(@Query("dynamicId") dynamicId: Int):LiveData<ApiResponse<Any>>


    /* 查询动态
    *  userId 用户id
    *  queryId 查询类型
    *  0 系统自动推荐用户动态
    *  1 查询userId用户的好友动态
    *  2 查询userId用户发表过的动态
    *  pageNumber 当前页数
    * */
    @GET("api/user/dynamic")
    fun queryDynamics(@QueryMap queryDynamicArgs: HashMap<String,Any>):LiveData<ApiResponse<List<PersonDynamic>>>

    @GET("api/download")
    fun toDownload(@Query("filePath") filePath:String):LiveData<ApiResponse<String>>

    //添加评论
    @POST("api/user/comments")
    fun addComments(@Body commentsMsg: CommentsMsg):LiveData<ApiResponse<CommentsMsg>>


    /*评论查询
    * dynamicId  动态id
    * commentsId 评论Id
    * queryId   查询类型
    * queryId = 0 查询CommentsGroupId = 0 的评论 评论的对象是作者
    * queryId = 1 查询commentsGroupId = commentsId 的评论  评论的对象是commentsId这条评论的发起用户
    * pageNumber 当前页数
    * */
    @GET("api/user/comments")
    fun queryComments(@QueryMap queryCommentsArgs:HashMap<String,Any>):LiveData<ApiResponse<List<CommentsMsg>>>

    //删除该条动态的所有评论
    @DELETE("api/user/comments")
    fun deleteComments(@Query("dynamicId") dynamicId:Int):LiveData<ApiResponse<Any>>

   //删除该条评论
   @DELETE("api/user/comments/{commentsId}")
    fun deleteComment(@Path("CommentsId") commentsId:Int):LiveData<ApiResponse<Any>>

    /* 为某个动态或者评论点赞
    *  id 评论或动态id
    *  userId  点赞用户id
    *  stat 0动态 1 评论
    */
    @FormUrlEncoded
    @POST("api/user/like")
    fun addLike(@FieldMap likeArgs:HashMap<String,Any>):LiveData<ApiResponse<Like>>

    /* 取消某个动态或者评论的点赞
    * id 评论或动态id
    * userId  取消点赞用户id
    * stat  0动态  1评论
    */
    @DELETE("api/user/like")
    fun deleteLike(@FieldMap likeArgs:HashMap<String,Any>):LiveData<ApiResponse<Any>>

    /*查询某个动态的点赞
    * id  queryId为0时，表示动态id,为1时，表示userId
    * queryId 查询类型
    * 0 表示查询该条动态id的所有点赞
    * 1 表示查询该用户id所有对动态的点赞
    * 两者任选一 进行不同内容的查询
    */
    @GET("api/user/like")
    fun queryLike(@FieldMap likeArgs:HashMap<String,Any>):LiveData<ApiResponse<List<Like>>>
}