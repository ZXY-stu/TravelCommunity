package com.bignerdranch.travelcommunity

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.bignerdranch.travelcommunity.base.NetworkConnectChangedReceiver

/**
 * @author zhongxinyu
 * @date 2020/4/4
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class TCApplication : Application() {

    private   var mNetworkChangeListener:NetworkConnectChangedReceiver? = null
    override fun onCreate() {
        super.onCreate()
        context = this
        if(mNetworkChangeListener==null){
            mNetworkChangeListener = NetworkConnectChangedReceiver()
        }
      val filter = IntentFilter()
      filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
      filter.addAction("android.net.wifi.WIFI_STATE_CHANGED")
      filter.addAction("android.net.wifi.STATE_CHANGE")
      registerReceiver(mNetworkChangeListener,filter)
    }



    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }



}