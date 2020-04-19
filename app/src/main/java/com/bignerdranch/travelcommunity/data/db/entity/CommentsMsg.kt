package com.bignerdranch.travelcommunity.data.db.entity

import androidx.room.*
import org.json.JSONArray
import java.util.*

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

@Entity(
    tableName = "comment_msg",
    foreignKeys = [
        ForeignKey(
        entity =PersonDynamic::class,
        parentColumns = ["id"],
        childColumns =  ["dynamic_id"],
        onUpdate = ForeignKey.CASCADE ,
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
       entity =User::class,
       parentColumns = ["id"],
       childColumns =  ["user_id"],
       onUpdate = ForeignKey.CASCADE ,
       onDelete = ForeignKey.CASCADE)],
    indices = [Index(value = ["dynamic_id"],unique = true), Index(value = ["user_id"],unique = true)]
)
/*创建了主键为id，外键为dynamic_id和user_id*/

/*评论区数据*/
data class CommentsMsg(
    @PrimaryKey val id:Int,
    @ColumnInfo(name = "dynamic_id")   val dynamicId:Int,
    @ColumnInfo(name = "user_id")      val user_id:Int,
    val userAccount:String,//评论人的账户
    val UserNickName:String,   //评论人的昵称
    val friendNickName:String,  //被评论人的昵称。
    val Msg:String , //消息内容
    val times: String  //评论时间 可能需要考虑Date类型
)