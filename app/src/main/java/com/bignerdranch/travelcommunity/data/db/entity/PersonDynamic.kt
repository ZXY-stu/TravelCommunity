package com.bignerdranch.travelcommunity.data.db.entity

import androidx.room.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

/*这是一个朋友圈动态数据表*/
/*
@Entity(tableName = "person_dynamic",
        foreignKeys = [ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns =  ["user_id"]
        )
        ],indices = [Index(value = (["id"]),unique = true),Index(value=(["user_id"]),unique = true)]
)*/
@Entity(tableName = "person_dynamic")
data class PersonDynamic(
    @PrimaryKey val id:Int = 0,  //主键
   @ColumnInfo(name = "user_id") val userId:Int, //外部键
    val account:String,//用户账号
    val userNickName:String,//用户昵称
    val textContent: String,  //文本内容
    val headPortraitUrl:String,//头像url
    val videoUrl:String, // 视频Url
    val imageUrls:String,  //图片集合url地址 Josn格式
    val likesCount:Int, //被点赞数统计
    val commentsCount:Int,//被评论数统计
    val submitsTime: Date, //发布时间 应该需要设置为Date类型
    val location:String,  //用户的位置
    val fullWatchCount:Int,  //完播次数统计
    val backWatchCount:Int, //观看次数统计
    //  火热程度根据点赞数、评论数、完播次数以及回看次数来计算
    val heatDegree:Int, //动态的火热程度
    /*
    * 隐私设置
    0表示开放
    1表示仅关注的人可见
    2表示自定义，需通过查询权限表获取访问权限
    3表示私密，仅自己可见
    * */
    val privateModel:Int
)



