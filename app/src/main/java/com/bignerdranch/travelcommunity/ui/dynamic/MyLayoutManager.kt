package com.bignerdranch.travelcommunity.ui.dynamic

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

/**
 * @author zhongxinyu
 * @date 2020/5/18
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class MyLayoutManager(val context: Context):LinearLayoutManager(context) {
    var canScrollX = false

    override fun canScrollHorizontally(): Boolean {
        return super.canScrollHorizontally() && canScrollX
    }

    fun setScrollX(enable:Boolean){
        canScrollX = enable
    }
}