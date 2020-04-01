package com.bignerdranch.travelcommunity.data.db.entity

import androidx.room.*
import androidx.room.util.TableInfo
import java.math.BigInteger


/*用户信息表*/

@Entity(
    tableName = "user",
    indices = [Index(value=["nick_name","userName","identifyNumber"],unique = true)]
)

/*   nick_name userName identifyNumber 都不能重复  以及*/
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "id") val loginId:Int,
    @ColumnInfo(name = "nick_name") val nickName:String,   //昵称
    val age:Int,          //年龄
    val userName:String, //用户名
    val birthday:String,  //出生日期
    val headPortraitUrl:String,  //头像图片地址
    val phoneNumber:String, //手机号
    val password:String, // 密码
    val identifyNumber:String,  //身份证号
    val sex:String,        //性别
    val hobby:String,    //兴趣爱好
    val introduce:String  //自我介绍
)






