package com.bignerdranch.tclib.data.db.entity

import androidx.room.*
import androidx.room.util.StringUtil
import com.bignerdranch.tclib.utils.StringUtils
import java.util.*

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

@Entity(
    tableName = "comment_msg",
  /*  foreignKeys = [
        ForeignKey(
        entity =PersonDynamic::class,
        parentColumns = ["id"],
        childColumns =  ["dynamic_id"],
        onUpdate = ForeignKey.CASCADE ,
        onDelete = ForeignKey.CASCADE
    )],*/
    indices = [Index(value = ["dynamic_id"]), Index(value = ["user_id"])]
)
/*创建了主键为id，外键为dynamic_id和user_id*/
//* cid,dynamicId,p_userId,cgId,userId,userAccount,userNickName,friendNickName,msg,times
/*评论区数据*/

data class CommentsMsg(
    @PrimaryKey override val id:Int = 0, //评论id
    @ColumnInfo(name = "dynamic_id")   val dynamicId:Int = 1, //所属动态
    @ColumnInfo(name = "user_id")      val userId:Int = 2, //评论人id
    val userNickName:String = "123",   //评论人的昵称
    val friendNickName:String = "123",  //被评论人的昵称。
    val msg:String ="", //消息内容
    val times: String = StringUtils.getDateTime(),
    val commentGroupId:Int = 0, // 评论组id    0表示属于 评论作者组  其它值表示 该条评论属于commentGroupId这条评论组
    // 通过commentGroupId 可以统计该条评论的被评论数  0时可以统计该条动态的被评论数
    // val userAccount:String = "",//评论人的账户
    val likeCount:String ="",// 点赞统计
    val commentsCount:String=""// 评论统计
):Base()
/*
*
*
* */