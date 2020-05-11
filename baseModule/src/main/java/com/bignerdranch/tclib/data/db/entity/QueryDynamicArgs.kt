package com.bignerdranch.tclib.data.db.entity

/**
 * @author zhongxinyu
 * @date 2020/4/7
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

/*   queryArgs.put("userAccount","")
                   queryArgs.put("friendAccount","")
                   queryArgs.put("pageNumber","1")*/
data class  QueryDynamicArgs(
    val userAccount:String="",
    val friendAccount:String="",
    val pageNumber:Int=1
)
