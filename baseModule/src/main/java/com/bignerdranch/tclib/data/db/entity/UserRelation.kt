package com.bignerdranch.tclib.data.db.entity

import androidx.room.*


/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
@Entity(tableName = "user_relation",
    foreignKeys = [
        ForeignKey(entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"])
    ],indices = [Index(value = ["id"],unique = true), Index(value = ["userId"])]
)
/*用户关系表
* 用户添加好友时可以设置对方所属的分组
* */
/*
* userid	当前用户user表主键
friendid	好友user表主键
groupId	分组
stat1	屏蔽状态默认0未屏蔽1屏蔽
stat2	不让看状态默认0可以看1不让看
memo	备注名
*
* */
data class UserRelation(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val userId:Int,   //用户
    val friendId:Int,  //用户朋友
    val groupId:Int,  // 用户分组ID
    /* CanSeeMe
       屏蔽对方
       默认0未屏蔽1屏蔽
    */
    val  CanSeeMe:Int,
    /*   canSeeHe
    *   不让看状态
        0可以看我
        1不让看我
    * */
    val canSeeHe: Int,
    val memo:String
)