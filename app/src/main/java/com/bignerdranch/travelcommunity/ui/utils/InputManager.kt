package com.bignerdranch.travelcommunity.ui.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment

/**
 * @author zhongxinyu
 * @date 2020/5/7
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
object InputManager {
    fun open(context: Context,view: View){
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view,InputMethodManager.SHOW_FORCED)
    }

    fun close(context: Context,view:View){
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken,InputMethodManager.HIDE_NOT_ALWAYS)
    }

     fun getStatus(context: Context):Boolean{
         val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
         return inputMethodManager.isActive
     }
}