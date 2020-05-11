package com.bignerdranch.travelcommunity.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Parcelable
import android.util.Log
import com.bignerdranch.tclib.LogUtil

/**
 * @author zhongxinyu
 * @date 2020/4/10
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 */
class NetworkConnectChangedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) { // 这个监听wifi的打开与关闭，与wifi的连接无关

        if (WifiManager.WIFI_STATE_CHANGED_ACTION == intent.action) {
            val wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0)
            Log.e(TAG1, "wifiState$wifiState")
            when (wifiState) {
                WifiManager.WIFI_STATE_DISABLED -> {
                }
                WifiManager.WIFI_STATE_DISABLING -> {
                }
                WifiManager.WIFI_STATE_ENABLING -> {
                }
                WifiManager.WIFI_STATE_ENABLED -> {
                }
                WifiManager.WIFI_STATE_UNKNOWN -> {
                }
                else -> {
                }
            }
        }
        // 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager
// .WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
// 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，
// 当然刚打开wifi肯定还没有连接到有效的无线
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION == intent.action) {
            val parcelableExtra = intent
                .getParcelableExtra<Parcelable>(WifiManager.EXTRA_NETWORK_INFO)
            if (null != parcelableExtra) {
                val networkInfo = parcelableExtra as NetworkInfo
                val state = networkInfo.state
                val isConnected =
                    state == NetworkInfo.State.CONNECTED // 当然，这边可以更精确的确定状态
                Log.e(
                    TAG1,
                    "isConnected$isConnected"
                )
                if (isConnected) {
                    //    APP.getInstance().setWifi(true);
                } else { //    APP.getInstance().setWifi(false);
                }
            }
        }
        // 这个监听网络连接的设置，包括wifi和移动数据的打开和关闭。.
// 最好用的还是这个监听。wifi如果打开，关闭，以及连接上可用的连接都会接到监听。见log
// 这个广播的最大弊端是比上边两个广播的反应要慢，如果只是要监听wifi，我觉得还是用上边两个配合比较合适
        if (ConnectivityManager.CONNECTIVITY_ACTION == intent.action) {
            val manager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            Log.i(TAG1, "CONNECTIVITY_ACTION")
            val activeNetwork = manager.activeNetworkInfo
            if (activeNetwork != null) {
                // connected to the internet
                if (activeNetwork.isConnected) {
                    if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                        // connected to wifi
                        Log.e(TAG, "当前WiFi连接可用 ")
                        haveNetwork = true
                    } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                        // connected to the mobile provider's data plan
                            LogUtil.e("当前移动网络连接可用")
                        haveNetwork = true
                    }
                } else {
                    LogUtil.e("没有网络连接")
                    haveNetwork = false
                }

            } else {
                LogUtil.e("没有网络连接")
                haveNetwork = false
            }
        }
    }

    companion object {
        var haveNetwork = false
        private const val TAG = "xujun"
        const val TAG1 = "xxx"
    }
}