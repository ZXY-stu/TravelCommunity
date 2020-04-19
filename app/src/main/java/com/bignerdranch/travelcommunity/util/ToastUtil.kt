package com.bignerdranch.travelcommunity.util

import android.widget.Toast
import com.bignerdranch.travelcommunity.TCApplication

/**
 * @author zhongxinyu
 * @date 2020/4/4
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
object ToastUtil {
    fun show(msg:String){
         Toast.makeText(TCApplication.context,msg,Toast.LENGTH_SHORT).show()
    }
    fun test(msg:String){
        Toast.makeText(TCApplication.context,msg,Toast.LENGTH_SHORT).show()
    }
}