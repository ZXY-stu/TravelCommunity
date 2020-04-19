package com.bignerdranch.travelcommunity.data.repository

import com.bignerdranch.travelcommunity.data.db.daos.CommentsMsgDao
import com.bignerdranch.travelcommunity.data.db.daos.LikeDao
import com.bignerdranch.travelcommunity.data.db.daos.PersonDynamicDao
import com.bignerdranch.travelcommunity.data.db.daos.UserDao
import com.bignerdranch.travelcommunity.data.network.Network

/**
 * @author zhongxinyu
 * @date 2020/4/10
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
open class BaseRepository(var _userDao: UserDao){


    protected  lateinit var _personDynamicDao: PersonDynamicDao
    protected  lateinit var _likeDao: LikeDao
    protected  lateinit var _commentsMsgDao: CommentsMsgDao
    protected val _network: Network =  Network.getInstance()


    constructor(personDynamicDao: PersonDynamicDao,commentsMsgDao: CommentsMsgDao,likeDao: LikeDao,
                userDao: UserDao)
          : this(userDao) {
        _personDynamicDao = personDynamicDao
        _commentsMsgDao = commentsMsgDao
        _likeDao = likeDao
    }



   companion object {
          private   var instance:BaseRepository? = null
        fun getInstance(userDao: UserDao):BaseRepository{
           return  instance?: synchronized(this){
               instance?:BaseRepository(userDao)
           }
        }

    }
}