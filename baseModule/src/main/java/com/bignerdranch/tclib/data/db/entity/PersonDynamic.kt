package com.bignerdranch.tclib.data.db.entity

import androidx.room.*
import com.bignerdranch.tclib.utils.StringUtils
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

/*
* 改动 ，Int -> String
* */
@Entity(tableName = "person_dynamic")
data class PersonDynamic(
 @PrimaryKey val id:Int = 0,  //主键
 @ColumnInfo(name = "user_id") val userId:Int = 1, //外部键
 val account:String = "zxy",//用户账号
 val userNickName:String = "zxy",//用户昵称
 val textContent: String ="zxy",  //文本内容
 val headPortraitUrl:String = "",//头像url
    //https://storage.googleapis.com/exoplayer-test-media-1/gen-3/screens/dash-vod-single-segment/video-avc-baseline-480.mp4
   // val videoUrl:String = "https://media.w3.org/2010/05/sintel/trailer.mp4", // 视频Url

 val videoUrl:String = "http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4", // 视频Url
 val imageUrls:String="",  //图片集合url地址 Josn格式
 val likesCount:String = "10w", //被点赞数统计
 val commentsCount:String = "100",//被评论数统计
 val submitsTime: String = StringUtils.getDateTime(), //发布时间
 val location:String = "",  //用户的位置
 val fullWatchCount:String = "",  //完播次数统计
 val backWatchCount:String = "", //观看次数统计
    //  火热程度根据点赞数、评论数、完播次数以及回看次数来计算
 val heatDegree:String = "", //动态的火热程度
    /*
    * 隐私设置
    0表示开放
    1表示仅关注的人可见
    2表示自定义，需通过查询权限表获取访问权限
    3表示私密，仅自己可见
    * */
    val privateModel:Int = 0
)



