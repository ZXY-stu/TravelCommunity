package com.bignerdranch.travelcommunity.data.db.entity

import androidx.room.*
import androidx.room.util.TableInfo
import org.json.JSONArray
import java.math.BigInteger
import java.util.*

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

/*用户信息表*/
@Entity(
    tableName = "user",
    indices = [Index(value=["id"],unique = true),
        Index(value=["nick_name"],unique = true)
    ]
)

/*   nick_name account identifyNumber phoneNumber都不能重复 */
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id") val userId:Int,
    @ColumnInfo(name = "nick_name") val nickName:String,   //昵称
    val account:String, //用户账号
    val age:Int,          //年龄
    val birthday:String,  //出生日期
    val headPortraitUrl:String, //头像图片地址
    val backgroundImageUrl:String,//个人主页背景图片
    val phoneNumber:String, //手机号
    val password:String, // 密码
    val address:String,//地址
    val identifyNumber:String,  //身份证号
    val sex:String,        //性别
    val hobby:String,    //兴趣爱好
    val introduce:String,  //自我介绍
    val likeTotal:String, //获赞数量
    val fansTotal:String,//粉丝数量
    val focusTotal:String,//关注朋友数量
    val stat:Int,   //0表示在线  1表示下线
    val lastLoginTime:Date, //最后登录时间
    val isMember:Int,  //普通设置为0 达人设置为1
    val privateModel:Int //隐私模式
    //0表示开放，只要有人申请关注，就可以通过
   //1表示私密，需要申请并由用户本人确认后，才可以通过
)

