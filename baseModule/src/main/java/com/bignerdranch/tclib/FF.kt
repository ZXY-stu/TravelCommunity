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




fun main() =  runBlocking {
          val querys = HashMap<String,Any>()
          querys.put("userId",1)

        //  val net = Network.getInstance()
        //  val res = net.toQueryDynamics(querys)
    //http://lyndon.fun:8080/api/user/dynamic?userId=1&pageNumber=1&queryId=0&textContent=1231234123
    // &userNickName=%E5%BB%96%E4%BA%9A%E5%8D%97&headPortraitUrl=url&submitsTime=2019-11-11%
         // print(res)
      }




fun get(x:String?):String?{
    return x?:"你是猪"
}
