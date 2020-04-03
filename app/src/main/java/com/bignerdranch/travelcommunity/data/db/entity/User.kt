package com.bignerdranch.travelcommunity.data.db.entity

import androidx.room.*
import androidx.room.util.TableInfo
import java.math.BigInteger

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

/*用户信息表*/
@Entity(
    tableName = "user",
    indices = [Index(value=["nick_name","account","identifyNumber","phoneNumber"],unique = true)]
)

/*   nick_name account identifyNumber phoneNumber都不能重复 */
data class User(
    @PrimaryKey
    @ColumnInfo(name= "id") val userId:Int,
    @ColumnInfo(name = "nick_name") val nickName:String,   //昵称
    val account:String, //用户账号
    val age:Int,          //年龄
    val birthday:String,  //出生日期
    val headPortraitUrl:String,  //头像图片地址
    val phoneNumber:String, //手机号
    val password:String, // 密码
    val identifyNumber:String,  //身份证号
    val sex:String,        //性别
    val hobby:String,    //兴趣爱好
    val introduce:String  //自我介绍
)






