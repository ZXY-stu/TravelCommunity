package com.bignerdranch.travelcommunity.util

import android.util.Log


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