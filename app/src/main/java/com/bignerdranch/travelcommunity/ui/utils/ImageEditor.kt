package com.bignerdranch.travelcommunity.ui.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.PointF
import android.graphics.RectF
import android.net.Uri
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.core.view.drawToBitmap
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.LogUtil.eee

/**
 * @author zhongxinyu
 * @date 2020/5/18
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
const val NONE = 0
const val DRAG = 1
const val ZOOM = 2
const val MAX_SCALE = 4f //最大缩放比例
class ImageEditor {
    private var mode = NONE
    private var oldDist = 0f
    private val matrix = Matrix()
    private val savedMatrix = Matrix()
    private val start = PointF()
    private val mid = PointF()
    private var stateChangeListener: StateChangeListener? = null
    var view: ImageView? = null
    var bitmap: Bitmap? = null
    var dm: DisplayMetrics? = null
    var height = 0
    var width = 0
    var minScaleR = 0.8f //最少缩放比例
    var dist = 1f

    interface StateChangeListener {
        fun stateChanged(state: Int)
    }

    fun setStateChangedListener(stateChangeListener: StateChangeListener): ImageEditor {
        this.stateChangeListener = stateChangeListener
        return this
    }

    //限制最大最小缩放比例
    fun CheckScale() {
        val p = FloatArray(9)
        matrix.getValues(p)
        if (mode == ZOOM) {
            if (p[0] < minScaleR) {
                matrix.setScale(minScaleR, minScaleR)
            }
            if (p[0] > MAX_SCALE) {
                matrix.set(savedMatrix)
            }
        }
    }

    fun changeScale(scale:Float){

    }

    //自动居中  左右及上下都居中
    fun center() {
         center(true, true)
    }



        fun center(horizontal: Boolean, vertical: Boolean) {
            val m = Matrix()
            m.set(matrix)
            val rect = RectF(0F, 0F, bitmap!!.width.toFloat(), bitmap!!.height.toFloat())
            m.mapRect(rect)
            val height = rect.height()
            val width = rect.width()
            var deltaX = 0f
            var deltaY = 0f
            if (vertical) { //int screenHeight = dm.heightPixels;  //手机屏幕分辨率的高度
                val screenHeight = dm!!.heightPixels
                if (height < screenHeight) {
                    deltaY = (screenHeight - height) / 2 - rect.top
                } else if (rect.top > 0) {
                    deltaY = -rect.top
                } else if (rect.bottom < screenHeight) {
                    deltaY = view!!.height - rect.bottom
                }
            }
            if (horizontal) { //int screenWidth = dm.widthPixels;  //手机屏幕分辨率的宽度
                val screenWidth = dm!!.widthPixels
                if (width < screenWidth) {
                    deltaX = (screenWidth - width) / 2 - rect.left
                } else if (rect.left > 0) {
                    deltaX = -rect.left
                } else if (rect.right < screenWidth) {
                    deltaX = screenWidth - rect.right
                }
            }
            LogUtil.eee("deltaX$deltaX  deltay $deltaY")
            matrix.postTranslate(deltaX, deltaY)
        }




    fun bindTouchEvent(itemView: ImageView,activity:Activity):ImageEditor {
        view = itemView
        bitmap = itemView.drawToBitmap()
        // view?.setImageURI(uri)
        //  bitmap =   itemView.drawToBitmap()
        dm = DisplayMetrics()
        activity.window.windowManager.defaultDisplay.getMetrics(dm) //获取分辨率
        matrix.setScale(minScaleR, minScaleR) //开始先缩小
        //view?.scaleType  = ImageView.ScaleType.CENTER_INSIDE
        //matrix.postTranslate((dm!!.widthPixels/2).toFloat(), (dm!!.heightPixels/2).toFloat()) //图片的位置相对于imageview的左上角往右往下各偏移120个像素


        center()
        view!!.imageMatrix = matrix


      /*  view!!.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(
                v: View,
                event: MotionEvent
            ): Boolean {
                val view = v as ImageView
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_DOWN -> {
                        savedMatrix.set(matrix)
                        start[event.x] = event.y
                        mode = DRAG
                        eee("你好")
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> mode = NONE
                    MotionEvent.ACTION_POINTER_DOWN -> {
                        oldDist = spacing(event).toFloat()
                        if (oldDist > 10f) {
                            savedMatrix.set(matrix)
                            midPoint(mid, event)
                            mode = ZOOM
                        }
                    }
                    MotionEvent.ACTION_MOVE -> if (mode == DRAG) {
                        stateChangeListener?.stateChanged(DRAG)
                           matrix.set(savedMatrix)
                        /*   matrix.postTranslate(
                               event.x - start.x,
                               event.y - start.y
                           ) *///xy方向都可以拖动
                        //  matrix.postTranslate(event.getX() - start.x,0f); //只在x轴方向拖动 即左右拖动  上下不动
                    matrix.postTranslate(0f,event.getY() - start.y);  //只在y轴方向拖动 即上下拖动  左右不动
                    } else if (mode == ZOOM) {
                        stateChangeListener?.stateChanged(ZOOM)
                        val newDist = spacing(event)
                        LogUtil.eee("ZOOM x ${mid.x} y ${mid.y}")
                        if (newDist > 10f) {
                            matrix.set(savedMatrix)
                            val scale = newDist / oldDist
                            matrix.postScale(scale.toFloat(), scale.toFloat(), mid.x, mid.y)
                        }
                    }
                }
                view.imageMatrix = matrix  //设置最新尺寸值
                CheckScale() //限制缩放范围
                center() //居中控制
                return true
            }

            //两点的距离
            private fun spacing(event: MotionEvent): Double {
                val x = event.getX(0) - event.getX(1)
                val y = event.getY(0) - event.getY(1)
                return Math.sqrt((x * x + y * y).toDouble())
            }

            //两点的中点
            private fun midPoint(point: PointF, event: MotionEvent) {
                val x = event.getX(0) + event.getX(1)
                val y = event.getY(0) + event.getY(1)
                point[x / 2] = y / 2
            }
        })*/
    return  this


}
}