package com.bignerdranch.tclib.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

/*点赞列表*/


@Entity(tableName = "like")
data  class Like(
    @PrimaryKey val id:Int,
    val dynamicId:Int,  //动态
    val userId:Int  //点赞用户
)
