package com.bignerdranch.travelcommunity.adapters

import androidx.databinding.InverseMethod
import androidx.room.TypeConverter
import java.util.*

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

object Coverters {

    @InverseMethod("fromStringToInt")
    @JvmStatic fun fromIntToString(value:Int?):String? {
        return value?.toString()?:""
    }

    @JvmStatic fun fromStringToInt(value:String?):Int? { return value?.toInt()?:-1}


    @JvmStatic fun fromDateToString(date: Date?): String? {
        return date.toString()
    }

    @JvmStatic fun getFristUrlfromString(imageUrl:String):String{
        return imageUrl
    }


}