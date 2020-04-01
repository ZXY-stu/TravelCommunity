package com.bignerdranch.travelcommunity.data.repository

import com.bignerdranch.travelcommunity.data.db.daos.UserDao
import com.bignerdranch.travelcommunity.data.network.Network

class UserRepository private  constructor(private val userDao: UserDao,private val network: Network){

       fun getUser() = userDao.getUser()
       fun getUser(userId:Int) = userDao.getUser(userId)
       fun getUser(nickName: String) = userDao.getUser(nickName)
       fun getNickName(userId: Int) = userDao.getNickName(userId)
       fun getUserIdByNickName(nickName:String) = userDao.getUserId(nickName)

     companion object{
          @Volatile private  var instance:UserRepository? = null

          fun getInstance(userDao: UserDao,network: Network):UserRepository{
              return instance ?: synchronized(this){
                  instance ?:UserRepository(userDao,network).also{ instance = it }
              }
          }
     }
}