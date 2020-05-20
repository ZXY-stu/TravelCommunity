package com.bignerdranch.tclib.data.db.entity

import androidx.room.*
import java.sql.Timestamp
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
    @PrimaryKey
    @ColumnInfo(name="id") var userId:Int,
    @ColumnInfo(name = "nick_name") var nickName:String="杀猪饲料",   //昵称
    var account:String="", //用户账号
    var age:Int=0,          //年龄
    var birthday:String = "",  //出生日期
    var headPortraitUrl:String? ="", //头像图片地址
  //  val backgroundImageUrl:String="",//个人主页背景图片
    var phoneNumber:String="", //手机号
    var password:String="", // 密码
   // val address:String="",//地址
    var identifyNumber:String="",  //身份证号
    var sex:String="",        //性别
    var hobby:String?="",    //兴趣爱好
    var introduce:String?="介绍下自己吧...",  //自我介绍
    var stat:Int = 0,   //0表示在线  1表示下线
    var lastLoginTime:String = "", //最后登录时间
    var isMember:Int = 0,  //普通设置为0 达人设置为1
    var privateModel:Int = 0 //隐私模式
    //0表示开放，只要有人申请关注，就可以通过
    //1表示私密，需要申请并由用户本人确认后，才可以通过
   // val likeTotal:String="", //获赞数量
   // val fansTotal:String="",//粉丝数量
    //val focusTotal:String=""//关注朋友数量

)

