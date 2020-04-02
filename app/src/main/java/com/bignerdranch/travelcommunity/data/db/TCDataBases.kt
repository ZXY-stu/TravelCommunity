package com.bignerdranch.travelcommunity.data.db

import android.content.Context
import androidx.room.*
import com.bignerdranch.travelcommunity.data.db.daos.UserDao
import com.bignerdranch.travelcommunity.data.db.entity.CommentsMsg
import com.bignerdranch.travelcommunity.data.db.entity.PersonDynamic
import com.bignerdranch.travelcommunity.data.db.entity.User
import com.bignerdranch.travelcommunity.util.DATABASE_NAME
import com.bignerdranch.travelcommunity.util.LogUtil


/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/


@Database(
    entities = [User::class, PersonDynamic::class, CommentsMsg::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)

//暂时不使用ROOM数据库
//接口都未实现
abstract  class TCDataBases:RoomDatabase(){

   // abstract  fun commentsMsgDao(): CommentsMsgDao
   // abstract  fun personDynamicDao(): PersonDynamicDao
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
              return Room.databaseBuilder(context, TCDataBases::class.java, DATABASE_NAME).build()
             /*   .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                      //   WorkManager.getInstance(context).enqueue(request)
                    }
                })*/
                //.build()
        }
    }
}