package com.bignerdranch.travelcommunity.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.bignerdranch.travelcommunity.R
import kotlinx.android.synthetic.main.mytitle.view.*


/**
 * @author zhongxinyu
 * @date 2020/4/18
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class TopBar(context: Context,attrs:AttributeSet) : LinearLayout(context,attrs) {

     private lateinit var listener:TitleBarListener
    interface TitleBarListener{
        fun onBackClicked()
        fun onMenuClicked()
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.mytitle,this)

        val typeArray =  context.obtainStyledAttributes(attrs,R.styleable.TopBar)

        val background = typeArray.getResourceId(R.styleable.TopBar_background,R.color.black)
        val text = typeArray.getString(R.styleable.TopBar_titleText)
        val textColor = typeArray.getColor(R.styleable.TopBar_titleTextColor,0xffffff)
        val textSize = typeArray.getDimension(R.styleable.TopBar_titleTextSize,
            R.dimen.TabText.toFloat()
        )

        typeArray.recycle()

        title.text = text
        title.setTextColor(textColor)
        title.textSize = textSize

        back.setOnClickListener {
            listener?.onBackClicked()
        }

        action.setOnClickListener {
            listener?.onMenuClicked()
        }
    }

    fun setListener(listener: TitleBarListener){
        this.listener = listener
    }


    fun setBackVisibility(visible:Boolean){
         when(visible){
             true -> back.visibility = View.VISIBLE
             else -> {
                 back.visibility = View.GONE
             }
         }
    }

    fun setTitleVisibility(visible:Boolean){
        when(visible){
            true -> title.visibility = View.VISIBLE
            else -> title.visibility = View.GONE
        }
    }
}