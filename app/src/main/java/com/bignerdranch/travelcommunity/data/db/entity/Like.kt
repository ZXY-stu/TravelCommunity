package com.bignerdranch.travelcommunity.data.db.entity

import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

/*点赞列表*/
@Fts4
@Entity(tableName = "like")
data  class Like(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val dynamicId:Int,
    val account:String
)