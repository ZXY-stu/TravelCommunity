package com.bignerdranch.tclib.data.db

import android.content.Context
import androidx.room.*
import com.bignerdranch.tclib.data.db.daos.CommentsMsgDao
import com.bignerdranch.tclib.data.db.daos.LikeDao
import com.bignerdranch.tclib.data.db.daos.PersonDynamicDao
import com.bignerdranch.tclib.data.db.daos.UserDao
import com.bignerdranch.tclib.data.db.entity.*


const val DATABASE_NAME = "travelCommunity-db"

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/


@Database(
    entities = [User::class, PersonDynamic::class, CommentsMsg::class, Like::class, Chat::class,AddFriendRecord::class,
    UserRelation::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)


abstract  class TCDataBases:RoomDatabase(){

    abstract  fun commentsMsgDao(): CommentsMsgDao
    abstract  fun personDynamicDao(): PersonDynamicDao
    abstract  fun userDao(): UserDao
    abstract fun  likeDao(): LikeDao


    companion object {
        // 单例模式
        @Volatile
        private var instance: TCDataBases? = null

        fun getInstance(context: Context): TCDataBases {
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