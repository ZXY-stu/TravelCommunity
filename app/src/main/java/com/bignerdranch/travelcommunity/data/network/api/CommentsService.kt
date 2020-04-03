package com.bignerdranch.travelcommunity.data.network.api

import android.graphics.ColorSpace
import com.bignerdranch.travelcommunity.data.network.model.Model
import retrofit2.Call
import retrofit2.http.*

/**
 * @author zhongxinyu
 * @date 2020/4/3
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
interface CommentsService {

    //添加评论
    @POST("api/user/comments")
    fun comments(@Body message:String,
                 @QueryMap commentsArgs:Map<String,String>):Call<Model.BackInfo>
    //评论查询
    @GET("api/user/comments")
    fun queryComments(@QueryMap queryCommentsArgs:Map<String,String>):Call<Model.BackInfo>

    //删除评论
    @DELETE("api/user/comments")
    fun deleteComments(@Query("dynamicId") dynamicId:Int):Call<Model.BackInfo>

}