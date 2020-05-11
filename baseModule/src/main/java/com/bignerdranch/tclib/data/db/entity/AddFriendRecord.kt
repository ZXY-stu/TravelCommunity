package com.bignerdranch.tclib.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

/**
 * @author zhongxinyu
 * @date 2020/4/12
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

@Entity(tableName = "addFriendRecord",
    foreignKeys = [
    ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )],indices = [Index(value = ["id"]), Index(value = ["userId"])])
data class AddFriendRecord(
    @PrimaryKey val id:Int,
    val userId:Int,  //发起用户
    val friendId:Int,  //被添加用户
    val stat:Int,   // 默认0请求中1接受2拒绝
    val time: Date,  //请求时间
    val groupId:Int,  //分组id
    val memo:String  //备注名
)

