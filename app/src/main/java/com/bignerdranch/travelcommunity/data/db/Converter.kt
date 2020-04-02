package com.bignerdranch.travelcommunity.data.db

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

object Converter{
    @TypeConverter
    fun fromListToString(lists:List<String>):String{
        val str = StringBuilder(lists[0])
        lists.forEach {
            str.append(",").append(it)
        }
        return str.toString()
    }
    @TypeConverter
    fun fromStringToList(str:String):List<String>{
        return  str.split(",")
    }


    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

}