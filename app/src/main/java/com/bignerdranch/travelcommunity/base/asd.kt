package com.bignerdranch.travelcommunity.base

import android.R
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager

import java.lang.reflect.Field


/**
 * @author zhongxinyu
 * @date 2020/5/12
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 */
object Check {
    /**
     * 判断全面屏是否启用虚拟键盘
     */
    private const val NAVIGATION = "navigationBarBackground"

    fun isNavigationBarExist(activity: Activity): Boolean {
        val vp = activity.window.decorView as ViewGroup
        if (vp != null) {
            for (i in 0 until vp.childCount) {
                vp.getChildAt(i).context.packageName
                if (vp.getChildAt(i).id != -1 && NAVIGATION == activity.resources.getResourceEntryName(
                        vp.getChildAt(i).id
                    )
                ) {
                    return true
                }
            }
        }
        return false
    }


    fun phoneHasNav(activity: Activity): Boolean {
        var flag = false
        if (Build.VERSION.SDK_INT < 14) {
            flag = false
        } else {
            val content: View = activity.getWindow().getDecorView()
                .findViewById(R.id.content)
            if (content != null) {
                val wm = activity.getSystemService(Context.WINDOW_SERVICE)  as WindowManager
                val display = wm!!.defaultDisplay
                val point = Point()
                display.getRealSize(point)

                    val bottom: Int = content.getBottom() // 页面的底部
                    if (bottom != point.y) {
                        flag = true
                    }
            }
        }
        return flag
    }
/*
    fun isNavigationBarExist(
        activity: Activity?,
        onNavigationStateListener: OnNavigationStateListener?
    ) {
        if (activity == null) {
            return
        }
        val height = getNavigationHeight(activity)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            activity.window.decorView.setOnApplyWindowInsetsListener { v, windowInsets ->
                var isShowing = false
                var b = 0
                if (windowInsets != null) {
                    b = windowInsets.systemWindowInsetBottom
                    isShowing = b == height
                }
                if (onNavigationStateListener != null && b <= height) {
                    onNavigationStateListener.onNavigationState(isShowing, b)
                }
                windowInsets
            }
        }
    }*/

    fun getNavigationHeight(activity: Context?): Int {
        if (activity == null) {
            return 0
        }
        val resources: Resources = activity.getResources()
        val resourceId: Int = resources.getIdentifier(
            "navigation_bar_height",
            "dimen", "android"
        )
        var height = 0
        if (resourceId > 0) { //获取NavigationBar的高度
            height = resources.getDimensionPixelSize(resourceId)
        }
        return height
    }


    /**
     * 不用内容高度和屏幕真实高度作对比来判断。
     * 这里只适用于21以后的版本，方法是从DecorView源码中来的，
     * 测试了模拟器21版本，和我自己手机Android 8.1.0都是有效的
     * api min is 21 version
     * 0:statusbar is visible
     * 1:navigation is visible
     *
     * @return statusbar, navigation是否可见
     */
    fun isSystemUiVisible(window: Window?): BooleanArray? {
        val result = booleanArrayOf(false, false)
        if (window == null) {
            return result
        }
        val attributes: WindowManager.LayoutParams = window.getAttributes()
        if (attributes != null) {
            result[0] =
                attributes.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN != WindowManager.LayoutParams.FLAG_FULLSCREEN
            //
            val decorView = window.getDecorView() as ViewGroup
            result[1] =
                attributes.systemUiVisibility or decorView.windowSystemUiVisibility and
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION == 0 && attributes.flags and WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS != 0
        }
        //
        val decorViewObj: Any = window.getDecorView()
        val clazz: Class<*> = decorViewObj.javaClass
        var mLastBottomInset = 0
        var mLastRightInset = 0
        var mLastLeftInset = 0
        try {
            val mLastBottomInsetField: Field = clazz.getDeclaredField("mLastBottomInset")
            mLastBottomInsetField.setAccessible(true)
            mLastBottomInset = mLastBottomInsetField.getInt(decorViewObj)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            val mLastRightInsetField: Field = clazz.getDeclaredField("mLastRightInset")
            mLastRightInsetField.setAccessible(true)
            mLastRightInset = mLastRightInsetField.getInt(decorViewObj)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            val mLastLeftInsetField: Field = clazz.getDeclaredField("mLastLeftInset")
            mLastLeftInsetField.setAccessible(true)
            mLastLeftInset = mLastLeftInsetField.getInt(decorViewObj)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val isNavBarToRightEdge = mLastBottomInset == 0 && mLastRightInset > 0
        val size =
            if (isNavBarToRightEdge) mLastRightInset else if (mLastBottomInset == 0 && mLastLeftInset > 0) mLastLeftInset else mLastBottomInset
        result[1] = result[1] && size > 0
        return result
    }

}