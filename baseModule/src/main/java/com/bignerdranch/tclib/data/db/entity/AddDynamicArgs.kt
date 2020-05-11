package com.bignerdranch.tclib.data.db.entity

/**
 * @author zhongxinyu
 * @date 2020/4/20
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
/*
* {account="用户账号",permissionId="权限类型",user_1="",user_2="",...}
* */
data class  AddDynamicArgs(
    val account:String,
    val permissionId:Int,
    val users:List<String>
)