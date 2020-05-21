package com.bignerdranch.travelcommunity.base

/**
 * @author zhongxinyu
 * @date 2020/5/21
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
data class Message(val msg:String,
                   val type:Int){
    companion object{
        const val HIDE_BOTTOM_VIEW = 0
        const val SHOW_BOTTOM_VIEW = 1
        const val FULL_SCREEN = 2
        const val NOT_FULL_SCREEN = 3
    }
}




