package com.bignerdranch.tclib.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.bignerdranch.tclib.utils.StringUtils
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
    @PrimaryKey override val id:Int = -1,   //聊天记录ID
    val userId:Int = -1, //用户Id
    val friendId:Int = -1, //朋友用户Id
    val content:String = "", //这条对话内容
    val imageUrl:String = "",// 这条对话的图片内容
    val videoUrl:String ="",//这条对话的视频内容
    val voiceUrl:String = "", //这条对话的语言内容
    val time: String= StringUtils.getDateTime() //对话时间
):Base()

