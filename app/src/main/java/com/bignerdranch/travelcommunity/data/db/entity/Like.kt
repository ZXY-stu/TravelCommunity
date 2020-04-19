package com.bignerdranch.travelcommunity.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import org.json.JSONArray
import java.util.ArrayList

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
