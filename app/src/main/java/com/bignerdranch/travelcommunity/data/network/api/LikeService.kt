package com.bignerdranch.travelcommunity.data.network.api

import com.bignerdranch.travelcommunity.data.network.model.Model
import retrofit2.Call
import retrofit2.http.*

/**
 * @author zhongxinyu
 * @date 2020/4/3
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
interface LikeService {

    //为某个评论点赞
    @POST("api/user/like")
    fun addLike(@QueryMap likeArgs:Map<String,String>):Call<Model.BackInfo>

    //取消某个评论的点赞
    @DELETE("api/user/like")
    fun cancelLike(@Query("dynamicId") dynamicId:Int):Call<Model.BackInfo>

    //查询某个评论的点赞
    @GET("api/user/like")
    fun queryLike(@QueryMap queryLikeArgs:Map<String,String>):Call<Model.BackInfo>
}