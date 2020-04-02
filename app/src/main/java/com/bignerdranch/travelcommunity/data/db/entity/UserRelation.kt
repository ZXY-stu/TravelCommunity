package com.bignerdranch.travelcommunity.data.db.entity

import androidx.room.*

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
@Fts4
@Entity(tableName = "user_relation")
/*用户关系表
* 用户添加好友时可以设置对方所属的分组
* */
data class UserRelation(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val userAccount:String,   //用户账号
    val friendAccount:String,  //用户朋友账号
    val groupId:Int  // 用户分组ID
)