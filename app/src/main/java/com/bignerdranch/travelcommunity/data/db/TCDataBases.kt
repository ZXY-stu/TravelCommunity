package com.bignerdranch.travelcommunity.data.db

import android.content.Context
import android.util.Log
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.bignerdranch.travelcommunity.data.db.daos.CommentsMsgDao
import com.bignerdranch.travelcommunity.data.db.daos.PersonDynamicDao
import com.bignerdranch.travelcommunity.data.db.daos.UserDao
import com.bignerdranch.travelcommunity.data.db.entity.CommentsMsg
import com.bignerdranch.travelcommunity.data.db.entity.PersonDynamic
import com.bignerdranch.travelcommunity.data.db.entity.User
import com.bignerdranch.travelcommunity.util.DATABASE_NAME
import com.bignerdranch.travelcommunity.util.LogUtil
import com.bignerdranch.travelcommunity.workers.SeedDatabaseWorker


/*
@Database(
    entities = [User::class, PersonDynamic::class, CommentsMsg::class],
    version = 1,
    exportSchema = false
)*/
//@TypeConverters(Converter::class)

//暂时不使用ROOM数据库
//接口都未实现
abstract  class TCDataBases{

    abstract  fun commentsMsgDao(): CommentsMsgDao
    abstract  fun personDynamicDao(): PersonDynamicDao
    abstract  fun userDao():UserDao

    companion object {
        // 单例模式
        @Volatile
        private var instance: TCDataBases? = null

        fun getInstance(context: Context): TCDataBases {
            LogUtil.e("The DataBase is not implements!!!")
            return instance?: synchronized(this){
                instance?: buildDatabase(context).also { instance = it }
            }
        }

        //用来预填充数据库数据进行相关测试
        private fun buildDatabase(context: Context): TCDataBases{
            /*  return Room.databaseBuilder(context, TCDataBases::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                         WorkManager.getInstance(context).enqueue(request)
                    }
                })
                .build()*/
            return null!!
        }
    }
}