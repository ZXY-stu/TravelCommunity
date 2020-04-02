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

class Converters{
    @TypeConverter fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

}