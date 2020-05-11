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
    foreignKeys = [
        ForeignKey(
        entity =PersonDynamic::class,
        parentColumns = ["id"],
        childColumns =  ["dynamic_id"],
        onUpdate = ForeignKey.CASCADE ,
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["dynamic_id"]), Index(value = ["user_id"])]
)
/*创建了主键为id，外键为dynamic_id和user_id*/
//* cid,dynamicId,p_userId,cgId,userId,userAccount,userNickName,friendNickName,msg,times
/*评论区数据*/
data class CommentsMsg(
    @PrimaryKey val id:Int = 0, //评论id
    @ColumnInfo(name = "dynamic_id")   val dynamicId:Int = 1, //所属动态
    @ColumnInfo(name = "user_id")      val user_id:Int = 1, //评论人id

    val commentGroupId:Int = 0, // 评论组id    0表示属于 评论作者组  其它值表示 该条评论属于commentGroupId这条评论组
    // 通过commentGroupId 可以统计该条评论的被评论数  0时可以统计该条动态的被评论数
    val userAccount:String = "",//评论人的账户
    val userNickName:String = "123",   //评论人的昵称
    val friendNickName:String = "123",  //被评论人的昵称。
    val msg:String ="", //消息内容
    val likeCount:String ="",// 点赞统计
    val commentsCount:String="",// 评论统计
    val times: String = StringUtils.getDate()
)
/*
*
*
*  // val authorUserId:Int = -1, //动态发布人id,非发布人 authorUserId = -1
* */
/*
* 1 1 1 "aaa" 0 "zxy" "" "发现一个有趣的人"
* 2 1 2 "aaa" 1 "yyy" "" "啥呀"
* 3 1 3 "aaa" 1 "hhh" "" "哟，哪里呀"
* 4 1 5 "aaa" 0 "iii" "" "哎，我们之间的差距就差一个妹子"
* 5 1 6 "aaa" 4 "zzz" "" "有道理"
* 6 1 7 "aaa" 4 "tit" "" "哈哈，我们之间的差距就差一张面膜"
* 7 1 5 "aaa" 4 "iii" "tit" “只怕远远不够"
* 8 1 8 "aaa" 4 "iti" "iii" "哈哈，太过真实"
* 9 1 2 "aaa" 4 "yyy" "iii" "嫌弃脸"
* */

/*

* */

//  aaa
/*
*   zxy: 发现个有趣的人
*       yyy:啥呀
*       hhh:哟，哪里呀
*   iii: 哎，我们之间的差距就差一个美字
*       zzz:有道理
*       tit:哈哈，我们之间的差距就差一张面膜
*       iii 回复 tit  只怕远远不够
*       iti 回复 iii  哈哈，太过真实
*       yyy 回复 iii  嫌弃脸
* */
