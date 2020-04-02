package com.bignerdranch.travelcommunity.data.db.entity

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

// 用户关系表
data class UserRelation(
    val rId:Int,
    val userId:String,   //用户ID
    val friendId:String,  //用户朋友ID
    val groupId:Int  // 用户分组ID
)