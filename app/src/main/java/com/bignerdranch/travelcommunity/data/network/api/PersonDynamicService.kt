package com.bignerdranch.travelcommunity.data.network.api

import com.bignerdranch.travelcommunity.data.network.model.Model
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

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
    ): Call<Model.BackInfo>

    //删除动态
    @DELETE("api/user/dynamic/{dynamicId}")
    fun deleteDynamic(@Path("dynamicId")dynamicId:Int): Call<Model.BackInfo>

    //查询动态
    @GET("api/user/dynamic")
    fun queryDynamics(@QueryMap queryDynamicArgs: Map<String,String>):Call<Model.BackInfo>
}