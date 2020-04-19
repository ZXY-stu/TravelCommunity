package com.bignerdranch.travelcommunity.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

/**
 * @author zhongxinyu
 * @date 2020/4/12
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

@Entity(tableName = "chat",
     foreignKeys = [
         ForeignKey(
         entity = User::class,
         parentColumns = ["id"],
         childColumns = ["userId"],
         onDelete = ForeignKey.CASCADE,
         onUpdate = ForeignKey.CASCADE
         )],indices = [Index(value = ["id"]), Index(value = ["userId"])])
data class Chat(
    @PrimaryKey val id:Int,   //聊天记录ID
    val userId:Int, //用户Id
    val friendId:Int, //朋友用户Id
    val content:String, //这条对话内容
    val time: Date //对话时间
)