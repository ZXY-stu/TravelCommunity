package com.bignerdranch.travelcommunity.data.db.entity

import androidx.room.*
import java.math.BigInteger


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
      indices = [(Index(value = ["dynamic_id"], unique = true)),(Index(value = ["user_id"], unique = true))]
)
/*创建了主键为id，外键为dynamic_id和user_id*/

/*评论区数据*/
data class CommentsMsg(
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name = "dynamic_id")   val dynamicId:Int,
    @ColumnInfo(name = "user_id")      val userId:Int,
    val sendUserId: Int,   //发送消息的人
    val receiverUserId: Int,  //回复消息的人
    val sender:String,   //发送人的昵称
    val receiver:String,  //回复人的昵称
    val Msg:String   //消息内容
)