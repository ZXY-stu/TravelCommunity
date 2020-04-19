package com.bignerdranch.travelcommunity.data.db.entity

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
@Fts4
@Entity(tableName = "access_shield")
/*朋友圈屏蔽列表
* A->B 表示B屏蔽A
* 当B对A设置开放时
* A->B所在的列将移动到开放列表
* */
data class DynamicAccessShield(
    @PrimaryKey(autoGenerate = true)  val id:Int,
    val account:String,
    val friendAccount:String
)