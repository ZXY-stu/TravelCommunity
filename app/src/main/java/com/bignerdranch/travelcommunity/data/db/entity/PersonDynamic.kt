package com.bignerdranch.travelcommunity.data.db.entity

import androidx.room.*
import java.util.*
import kotlin.collections.ArrayList

/*这是一个朋友圈动态数据表*/


@Entity(tableName = "person_dynamic",
        foreignKeys = [ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns =  ["user_id"],
            onUpdate = ForeignKey.CASCADE ,
            onDelete = ForeignKey.CASCADE)
        ],indices = [Index(value = (["user_id"]),unique = true)]
)
data class PersonDynamic(
    @PrimaryKey(autoGenerate = true) val id:Int,  //主键
    @ColumnInfo(name = "user_id") val userId:Int, //外部键
    val textContent: String,  //文本内容
    val videoUrl:String, // 视频Url
    val imageUrls:ArrayList<String>,  //图片集合url地址
    val likeCount:Int, //被点赞数统计
    val submitsTime: Date //发布时间
)





