package com.bignerdranch.travelcommunity.util

import android.content.Context
import android.net.ConnectivityManager
import com.bignerdranch.tclib.LogUtil

/**
 * @author zhongxinyu
 * @date 2020/4/10
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 */
class NetUtil {



    companion object {
        /**
         * 没有连接网络
         */
        private const val NETWORK_NONE = -1
        /**
         * 移动网络
         */
        private const val NETWORK_MOBILE = 0
        /**
         * 无线网络
         */
        private const val NETWORK_WIFI = 1

        fun getNetWorkState(context: Context): Int { // 得到连接管理器对象
            val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            LogUtil.e("nee")

            val activeNetworkInfo = connectivityManager
                .activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                if (activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) {
                    return NETWORK_WIFI
                } else if (activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                    return NETWORK_MOBILE
                }
            } else {
                return NETWORK_NONE
            }

            return NETWORK_NONE
        }
    }
}