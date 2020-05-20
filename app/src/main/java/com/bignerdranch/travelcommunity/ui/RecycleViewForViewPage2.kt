package com.bignerdranch.travelcommunity.ui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.tclib.LogUtil.eee


/**
 * @author zhongxinyu
 * @date 2020/5/18
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

/*
* 自定义RecycleView
* 向外部提供了X方向的滑动控制接口
* 主要用于解决嵌套滑动冲突
* 当手势不是平行于X轴滑动时，而是斜方向滑动时，将发出禁止滑动信号
* */
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
    private var  unableScorllXListener:EnableScorllXListener? = null
    val distance = 260
    var lastDistX = 0
    var maxDisY = 50

  interface EnableScorllXListener{
      fun enable(flag:Boolean)
  }

    fun setUnableScorllXListener(unableScorllXListener:EnableScorllXListener):RecyclerViewForViewPage2{
        this.unableScorllXListener = unableScorllXListener
        return this
    }

    fun setMaxDisY(maxY:Int):RecyclerViewForViewPage2{
        maxDisY = maxY
        return this
    }


    override fun onTouchEvent(e: MotionEvent?): Boolean {
       val su =  super.onTouchEvent(e)
        when (e?.action) {
            MotionEvent.ACTION_MOVE -> {

            }
        }
        return su
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when(ev?.action){
            MotionEvent.ACTION_DOWN->{
                startX = ev.x.toInt()
                startY = ev.y.toInt()
            }  MotionEvent.ACTION_MOVE -> {
            val endX = ev.x.toInt()
            val endY = ev.y.toInt()
            val disX = Math.abs(endX - startX)
            val disY = Math.abs(endY - startY)
            eee("endx$endX  endY $endY  disX$disX  disY$disY")
            if(disY>maxDisY) {
                if (disX < distance){    //移动距离小于阈值时，才禁止滑动
                    unableScorllXListener?.enable(false)
                    lastDistX = disX
                }
            }else unableScorllXListener?.enable(true)

        }MotionEvent.ACTION_UP->{
           if(lastDistX!=0) {
               unableScorllXListener?.enable(true)   //如果已经移动了一段距离，应该启用滑动
               lastDistX = 0
           }
        }
        }
      return   super.dispatchTouchEvent(ev)
    }


    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        val isInterrupted = super.onInterceptTouchEvent(e)

        when(e?.action){
            MotionEvent.ACTION_DOWN->{
                eee("onInterceptTouchEvent ACTION_DOWN")
            }
            MotionEvent.ACTION_MOVE->{
                eee("onInterceptTouchEvent ACTION_MOVE")

            }
            MotionEvent.ACTION_UP->{
                eee("onInterceptTouchEvent ACTION_UP")
            }
        }
        return isInterrupted
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