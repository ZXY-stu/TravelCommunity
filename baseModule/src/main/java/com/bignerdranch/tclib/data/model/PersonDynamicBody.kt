package com.bignerdranch.tclib.data.model

import java.util.*

/**
 * @author zhongxinyu
 * @date 2020/4/19
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
data class PersonDynamicBody(
    val account:String ="",//用户账号
    val userNickName:String ="",//用户昵称
    val textContent: String ="",  //文本内容
    val headPortraitUrl:String ="",//头像url
    val videoUrl:String="", // 视频Url
    val imageUrls:String="",  //图片集合url地址 Josn格式
    val submitsTime: Date= Date(System.currentTimeMillis()), //发布时间 应该需要设置为Date类型
    val location:String =""  //用户的位置
)