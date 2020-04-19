package com.bignerdranch.travelcommunity.data.network.api

import androidx.lifecycle.LiveData
import com.bignerdranch.travelcommunity.data.db.entity.CommentsMsg
import com.bignerdranch.travelcommunity.data.db.entity.Like
import com.bignerdranch.travelcommunity.data.db.entity.PersonDynamic
import com.bignerdranch.travelcommunity.data.network.callAdapter.LiveDataCallAdapterFactory
import com.bignerdranch.travelcommunity.data.network.model.ApiResponse
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

/**
 * @author zhongxinyu
 * @date 2020/4/3
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
interface PersonDynamicService {

    //动态上传
    @POST("api/user/dynamic")
    fun uploadDynamic(
        @QueryMap permissionArgs: Map<String, String>
        , @PartMap contentsArgs: Map<String, RequestBody>
    ):LiveData<ApiResponse<PersonDynamic>>

    //删除动态
    @DELETE("api/user/dynamic/{dynamicId}")
    fun deleteDynamic(@Path("dynamicId")dynamicId:Int)

    //查询动态
    @GET("api/user/dynamic")
    fun queryDynamics(@QueryMap queryDynamicArgs: Map<String,String>):LiveData<List<PersonDynamic>>

//http://lyndon.fun:8080/api/user/dynamic
    //http://lyndon.fun:8080/api/download?filePath=group1/M00/00/00/L2ZgWV6L2--EN0TQAAAAAIzB8-8381.jpg
    @GET("api/download")
    fun toDownload(@Query("filePath") filePath:String):LiveData<ApiResponse<String>>

    //添加评论
    @POST("api/user/comments")
    fun comments(@Body message:String,
                 @QueryMap commentsArgs:Map<String,String>):LiveData<ApiResponse<CommentsMsg>>
    //评论查询
    @GET("api/user/comments")
    fun queryComments(@QueryMap queryCommentsArgs:Map<String,String>):LiveData<ApiResponse<List<CommentsMsg>>>

    //删除评论
    @DELETE("api/user/comments")
    fun deleteComments(@Query("id") dynamicId:Int)

    //为某个动态点赞
    @POST("api/user/like")
    fun addLike(@QueryMap likeArgs:Map<String,String>):LiveData<ApiResponse<Like>>

    //取消某个动态的点赞
    @DELETE("api/user/like")
    fun cancelLike(@Query("likeId") likeId:Int)

    //查询某个动态的点赞
    @GET("api/user/like")
    fun queryLike(@QueryMap queryLikeArgs:Map<String,String>):LiveData<ApiResponse<List<Like>>>



}