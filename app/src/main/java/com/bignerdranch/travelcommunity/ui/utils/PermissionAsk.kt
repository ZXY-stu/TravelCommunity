package com.bignerdranch.travelcommunity.ui.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * @author zhongxinyu
 * @date 2020/4/23
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/


object PermissionAsk{

     fun checkPermission(activity: Activity,permission:String,requestCode:Int,block:()->Unit){
           if(ContextCompat.checkSelfPermission(activity,permission)!=PackageManager.PERMISSION_GRANTED){
               ActivityCompat.requestPermissions(activity, arrayOf(permission),requestCode)
           }else {
               block()
           }
     }
}