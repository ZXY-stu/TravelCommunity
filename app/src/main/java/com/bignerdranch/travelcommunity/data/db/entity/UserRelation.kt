package com.bignerdranch.travelcommunity.data.db.entity

import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.Index
import androidx.room.PrimaryKey
import org.json.JSONArray
import java.util.*


/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
@Entity(tableName = "user_relation",
    indices = [Index(value = ["id"])]
)
/*用户关系表
* 用户添加好友时可以设置对方所属的分组
* */
data class UserRelation(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val userAccount:String,   //用户账号
    val friendAccount:String,  //用户朋友账号
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
    val canSeeHe: Int
)