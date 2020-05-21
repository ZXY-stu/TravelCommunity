package com.bignerdranch.tclib.data.network.api

import androidx.lifecycle.LiveData
import com.bignerdranch.tclib.data.db.entity.CommentsMsg
import com.bignerdranch.tclib.data.db.entity.Like
import com.bignerdranch.tclib.data.db.entity.PersonDynamic
import com.bignerdranch.tclib.data.network.model.ApiResponse

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*
import kotlin.collections.HashMap


/**
 * @author zhongxinyu
 * @date 2020/4/3
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
interface PersonDynamicService {

    // 所有请求
    // 成功设置errorCode = 0
    // 失败设置errorCode = 1

    /* 动态发表
    *  permissionArgs  权限列表
    *  name = userId 发表人Id
    *  name = permissionId  隐私id
    *  0 表示开放
    *  1 表示仅关注的人可见
    *  2 选中用户可见
    *  3 选中用户不可见
    *  4 表示私密，仅自己可见
    *
    *  name =  userList
    *  userList为需要处理权限的用户列表，处理后，存入UserDynamicPermission
    *  当permissionId为2或者3时，就会传userList至服务器
    *  否则，userList就不存在
    *
    * contentArgs  内容列表
    * name = textContent  动态的文本内容
    * name = imgs    动态的图片，可以有多张
    * name = video   动态的视频文件
    * userNickName 用户昵称
    * headPortraitUrl 用户头像
    * submitTimes 发布时间
    * 只能同时发图片和文字  或者  视频和文字
    * 图片和视频通过name区分
    * */
  @Multipart
  @POST("api/user/dynamic")
  fun addDynamic(@PartMap permissionArgs:HashMap<String,Any>
     ,@PartMap contentsArgs: HashMap<String, RequestBody>): LiveData<ApiResponse<PersonDynamic>>
    
    //删除动态
    @DELETE("api/user/dynamic")
    fun deleteDynamic(@Query("dynamicId") dynamicId: Int):LiveData<ApiResponse<Any>>

    /* 查询动态
    *  name = userId 用户id
    *  name = queryId 查询类型
    *  queryId = 0 系统自动推荐用户动态
    *  queryId = 1 查询userId用户好友的动态
    *  queryId = 2 查询userId用户发表过的动态
    *  pageNumber 当前页数
    *  返回最新的10条动态
    * */

    @GET("api/user/dynamic")
    fun queryDynamics(@QueryMap queryDynamicArgs: HashMap<String,Any>):LiveData<ApiResponse<List<PersonDynamic>>>

    //添加评论

    @POST("api/user/comments")
    fun addComments(@Body commentsMsg: CommentsMsg):LiveData<ApiResponse<Any>>

    /*评论查询
    *  dynamicId  动态id
    *  commentsId 评论Id
    *  pageNumber 当前第一层的查询页数
    *  subPageNumber 当前第二层评论的查询页数
    *  CommentsGroupId用于分层
    *  commentsId = 0 时，返回评论作者的最新10条评论，属于第一层
    *  commentsId ！=0 时，返回 CommentsGroupId == commentsId的最新10条评论，属于第二层
    * */
    /*
    *   每条评论都会设置对应的CommentsGroupId，
    *   评论作者的评论，CommentsGroupId 就是0
    *   不是评论的作者，CommentsGroupId = 被评论的commentsId
    *
    *   然后，我传给你的实体类保存了它所属的CommentsGroupId ，以及他要评论的人的昵称
    *   你只需要拿到数据后，插入指定的 CommentsGroupId 和 他要评论的人的昵称  所属的那条评论后面就OK了
    *
    *   然后评论按时间排序
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
    fun addLike(@FieldMap likeArgs:HashMap<String,Any>):LiveData<ApiResponse<Any>>

    /* 取消某个动态或者评论的点赞
    *  id 评论或动态id
    *  userId  取消点赞用户id
    *  stat  0动态  1评论
    */

    @DELETE("api/user/like")
    fun deleteLike(@QueryMap likeArgs:HashMap<String,Any>):LiveData<ApiResponse<Any>>

    /* 查询某个动态的点赞
    *  id 表示需要查询的id
    *  当queryId为0时，id为dynamicId,当queryId为1时，表示userId
    *  queryId 查询类型
    *  0 表示查询该条动态id的所有点赞
    *  1 表示查询该用户id所有对动态的点赞
    *  两者任选一 进行不同内容的查询
    */
    @GET("api/user/like")
    fun queryLike(@QueryMap likeArgs:HashMap<String,Any>):LiveData<ApiResponse<List<Like>>>

}