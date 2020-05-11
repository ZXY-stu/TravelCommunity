package com.bignerdranch.travelcommunity.ui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.annotation.Nullable
import androidx.core.view.ViewCompat.canScrollHorizontally
import androidx.core.view.ViewCompat.canScrollVertically
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.tclib.LogUtil


/**
 * @author zhongxinyu
 * @date 2020/4/30
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class RecyclerViewForViewPage2 : RecyclerView {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, @Nullable attrs: AttributeSet?) : super(context, attrs) {}
    constructor(
        context: Context, @Nullable attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    private var startX = 0
    private var startY = 0

    override fun onTouchEvent(e: MotionEvent?): Boolean {

        when (e?.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = e.x.toInt()
                startY = e.y.toInt()
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val endX = e.x.toInt()
                val endY = e.y.toInt()
                val disX = Math.abs(endX - startX)
                val disY = Math.abs(endY - startY)
                LogUtil.ee("endx$endX  endY $endY  disX$disX  disY$disY")
                return disY <= 5
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL ->
                parent.requestDisallowInterceptTouchEvent(
                    false
                )
        }
        return   super.onTouchEvent(e)
    }
/*
    override fun onTouchEvent(e: MotionEvent?): Boolean {

        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = ev.x.toInt()
                startY = ev.y.toInt()
               parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val endX = ev.x.toInt()
                val endY = ev.y.toInt()
                val disX = Math.abs(endX - startX)
                val disY = Math.abs(endY - startY)
                LogUtil.ee("endx$endX  endY $endY  disX$disX  disY$disY")
                if(disY>5){
                   parent.requestDisallowInterceptTouchEvent(true)
               } else {
                  parent.requestDisallowInterceptTouchEvent(false)
                }

              /*  if (disX > disY) {
                 parent.requestDisallowInterceptTouchEvent(canScrollHorizontally(startX - endX))
                } else {
                    parent.requestDisallowInterceptTouchEvent(canScrollVertically(startY - endY))
                }*/
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL ->
               parent.requestDisallowInterceptTouchEvent(
               false
            )
        }
        return super.dispatchTouchEvent(ev)
    }*/
}