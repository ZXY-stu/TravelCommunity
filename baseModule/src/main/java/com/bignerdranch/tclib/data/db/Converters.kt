package com.bignerdranch.tclib.data.db

import androidx.room.TypeConverter
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

class Converters{
    @TypeConverter fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter fun  stringToTimestamp(times:String):Timestamp{
       val time =  Timestamp(System.currentTimeMillis())
        return Timestamp.valueOf(times)
    }

    @TypeConverter fun timeStampToString(timestamp: Timestamp):String{
           val sdf = SimpleDateFormat("yyyy-mm-dd hh:mm:ss")
        return sdf.format(timestamp)
    }


}