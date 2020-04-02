package com.bignerdranch.travelcommunity.util

import android.util.Log
/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/


object LogUtil {
    val TAG = "MainActivity"

    fun w(msg:String){
        Log.w(TAG,msg)
    }

    fun d(msg:String){
        Log.d(TAG,msg)
    }

    fun e(msg:String){
        Log.e(TAG,msg)
    }

}