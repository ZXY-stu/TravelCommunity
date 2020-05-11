package com.bignerdranch.travelcommunity.util

import android.graphics.Color
import android.graphics.fonts.FontFamily
import android.os.Build
import android.text.Html

/**
 * @author zhongxinyu
 * @date 2020/5/7
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class  HtmlTextStyleUtil private constructor(){
    private var text:String = ""
    private var subText:String = ""
    private var color = ""
    private var fontFamily = ""
    private var fontSize = ""
    private var nickName = ""
    private var msg = ""


    companion object{
        val START = 0
        val END = 1
        val SINGLE = 2
        val NOREPLAY_WITH_TIME = 3
        val REPLAY_WITH_TIME = 4
        private var htmlStyleUtil:HtmlTextStyleUtil? = null
        fun getInstance():HtmlTextStyleUtil {
            return htmlStyleUtil?: synchronized(this){
                htmlStyleUtil?:HtmlTextStyleUtil()
            }
        }
    }


    fun setText(text:String):HtmlTextStyleUtil{
        this.text = text
        return this
    }


    fun setSubText(subText:String):HtmlTextStyleUtil{
        this.subText = subText
        return this
    }

    fun setNickName(nickName:String):HtmlTextStyleUtil{
        this.nickName = nickName
        return this
    }


    fun setColor(color:String):HtmlTextStyleUtil{
        this.color = color
        return this
    }

    fun setFontFamily(fontFamily: String):HtmlTextStyleUtil{
        this.fontFamily = fontFamily
        return this
    }

    fun setFontSize(fontSize:String):HtmlTextStyleUtil{
        this.fontSize = fontSize
        return this
    }


    fun buildStyle(type:Int):CharSequence{

        when(type){
            START ->  msg = "$text<font color='$color' size='${fontSize}dp'>$subText</font>"
            END ->  msg = "<font color='$color' size='${fontSize}dp'> $subText</font>$text"
            SINGLE ->  msg = "<font color='$color' size='${fontSize}dp'> $text</font>"
            NOREPLAY_WITH_TIME -> msg = "$text <font color='${Color.GRAY}' size='${fontSize}dp'> $subText</font>"
            REPLAY_WITH_TIME -> msg = "回复 <font color='${Color.GRAY}'>$nickName</font>: $text <font color='${Color.GRAY}' size='${fontSize}dp'> $subText</font>"
        }
        val charSequence = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(msg, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(msg)
        }
        return charSequence
    }






}