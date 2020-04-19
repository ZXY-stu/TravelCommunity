package com.bignerdranch.travelcommunity.util

import com.google.gson.Gson

/**
 * @author zhongxinyu
 * @date 2020/4/3
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

fun  toJson(bean:Any) = Gson().toJson(bean)


fun String.toEncrypt():String{
    return this
}

fun String.toDeCode():String{
    return this
}


