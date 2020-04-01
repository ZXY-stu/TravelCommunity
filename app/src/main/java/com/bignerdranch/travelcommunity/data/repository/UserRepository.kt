package com.bignerdranch.travelcommunity.data.repository

import com.bignerdranch.travelcommunity.data.db.daos.UserDao
import com.bignerdranch.travelcommunity.data.db.entity.User
import com.bignerdranch.travelcommunity.data.network.Network
import com.bignerdranch.travelcommunity.util.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.PasswordAuthentication

class UserRepository private  constructor(private val userDao: UserDao,private val network: Network){

       suspend  fun getUserRoom() = withContext(Dispatchers.IO){ userDao.getUser()}
       suspend  fun getUserFromRoom(userId:String) = withContext(Dispatchers.IO){ userDao.getUser(userId)}
       suspend fun insertUserToRoom(user:User) = withContext(Dispatchers.IO){ userDao.insertUser(user) }
       suspend fun deleteUserFromRoom(user: User) = withContext(Dispatchers.IO){userDao.deleteUser(user)}

       suspend fun loginToNetwork(account:String,password:String) = withContext(Dispatchers.IO) {network.toLogin(account,password)}
       suspend fun logoutFromNetwork(account: String) = withContext(Dispatchers.IO){network.toLogout(account)}
       suspend fun registerToNetwork(user:User) = withContext(Dispatchers.IO){network.toRegister(user)}

       companion object{
          @Volatile private  var instance:UserRepository? = null

          fun getInstance(userDao: UserDao,network: Network):UserRepository{
              return instance ?: synchronized(this){
                  instance ?:UserRepository(userDao,network).also{ instance = it }
              }
          }
     }
}