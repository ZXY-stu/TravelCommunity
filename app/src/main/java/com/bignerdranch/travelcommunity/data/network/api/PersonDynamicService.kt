package com.bignerdranch.travelcommunity.data.network.api

import androidx.lifecycle.LiveData
import com.bignerdranch.travelcommunity.data.db.entity.CommentsMsg
import com.bignerdranch.travelcommunity.data.db.entity.Like
import com.bignerdranch.travelcommunity.data.db.entity.PersonDynamic
import com.bignerdranch.travelcommunity.data.network.model.ApiResponse
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File


/**
 * @author zhongxinyu
 * @date 2020/4/3
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
interface PersonDynamicService {

    //动态发表
  @Multipart
    @POST("api/upload")
    fun addDynamic(
      @PartMap contentsArgs: HashMap<String, RequestBody>
    ): Call<String>

    //删除动态
    @DELETE("api/user/dynamic/{dynamicId}")
    fun deleteDynamic(@Path("dynamicId")dynamicId:Int)

    //查询动态
    @GET("api/user/dynamic")
    fun queryDynamics(@QueryMap queryDynamicArgs: Map<String,String>):LiveData<List<ApiResponse<PersonDynamic>>>


    @GET("api/download")
    fun toDownload(@Query("filePath") filePath:String):LiveData<ApiResponse<String>>

    //添加评论
    @POST("api/user/comments")
    fun addComments(@Body commentsMsg: CommentsMsg):LiveData<ApiResponse<CommentsMsg>>

    //评论查询
    @GET("api/user/comments")
    fun queryComments(@QueryMap queryCommentsArgs:Map<String,String>):LiveData<ApiResponse<List<CommentsMsg>>>

    //删除评论
    @DELETE("api/user/comments")
    fun deleteComments(@Query("id") dynamicId:Int)

    //为某个动态点赞
    @POST("api/user/like")
    fun addLike(@FieldMap likeArgs:Map<String,String>):LiveData<ApiResponse<Like>>

    //取消某个动态的点赞
    @DELETE("api/user/like")
    fun deleteLike(@Query("likeId") likeId:Int)

    //查询某个动态的点赞
    @GET("api/user/like")
    fun queryLike(@QueryMap queryLikeArgs:Map<String,String>):LiveData<ApiResponse<List<Like>>>

}