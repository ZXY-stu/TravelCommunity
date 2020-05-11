package com.bignerdranch.tclib.data.db.entity

import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
@Fts4
@Entity(tableName = "allow_access")
/*加载朋友圈动态时可看权限列表
* A->B 表示B对A开放
* 当B屏蔽了A时
* A->B所在的列将移动到屏蔽列表中
* */
data class DynamicAccessAllow(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val account:String,
    val friendAccount:String
)