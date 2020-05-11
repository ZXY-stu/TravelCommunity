package com.bignerdranch.tclib

import android.content.Context
import android.util.Log
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import com.bignerdranch.tclib.data.db.TCDataBases
import com.bignerdranch.tclib.data.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * @author zhongxinyu
 * @date 2020/4/23
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/




fun main() = runBlocking{

    print(get(null))
    print(get("你不是"))
//    print("net to ${data.data}")
}


fun get(x:String?):String?{
    return x?:"你是猪"
}
