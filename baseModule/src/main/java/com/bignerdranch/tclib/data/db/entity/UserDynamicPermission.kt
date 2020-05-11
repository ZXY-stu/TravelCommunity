package com.bignerdranch.tclib.data.db.entity

/**
 * @author zhongxinyu
 * @date 2020/4/12
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
data class UserDynamicPermission(
    val userId:Int,
    val dynamicId:Int,
    val friendId:Int,
    val permission:Int
)