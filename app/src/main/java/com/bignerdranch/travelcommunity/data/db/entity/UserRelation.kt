package com.bignerdranch.travelcommunity.data.db.entity

// 用户关系表

data class UserRelation(
    val rId:Int,
    val userId:Int,   //用户ID
    val friendId:Int  //用户朋友ID
)