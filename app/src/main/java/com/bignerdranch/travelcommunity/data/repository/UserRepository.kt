package com.bignerdranch.travelcommunity.data.repository

import androidx.lifecycle.*
import com.bignerdranch.travelcommunity.TCApplication
import com.bignerdranch.travelcommunity.base.NetworkConnectChangedReceiver.Companion.haveNetwork
import com.bignerdranch.travelcommunity.data.db.daos.CommentsMsgDao
import com.bignerdranch.travelcommunity.data.db.daos.LikeDao
import com.bignerdranch.travelcommunity.data.db.daos.PersonDynamicDao
import com.bignerdranch.travelcommunity.data.db.daos.UserDao
import com.bignerdranch.travelcommunity.data.db.entity.User
import com.bignerdranch.travelcommunity.data.db.entity.UserRelation
import com.bignerdranch.travelcommunity.data.network.Network
import com.bignerdranch.travelcommunity.data.network.model.ApiResponse
import com.bignerdranch.travelcommunity.util.LogUtil
import com.bignerdranch.travelcommunity.util.SUCCESS
import com.bignerdranch.travelcommunity.util.ToastUtil
import com.bignerdranch.travelcommunity.util.toJson
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.PasswordAuthentication
import java.util.*

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

class UserRepository(private val userDao: UserDao):BaseRepository(userDao){


      suspend fun userLogin(account:String,password:String) = withContext(Dispatchers.IO){
             _network.toLogin(account, password)
      }

     fun getUser() = Transformations.switchMap(_userDao.getUser()){
         MutableLiveData(ApiResponse(data = it,errorCode = 0,errorMsg = ""))
     }


      suspend fun userRegister(user:User) = withContext(Dispatchers.IO){
          _network.toRegister(user)
      }

     suspend fun userRegister(account: String,password: String) = withContext(Dispatchers.IO) {
         _network.toRegister(account, password)
       val user =   User(
             2,
             "你长的真好看呀",
             "小宇宙",
             20,
             "0219",
             "https://upload.wikimedia.org/wikipedia/commons/8/82/Hibiscus_rosa-sinensis_flower_2.JPG",
             "https://upload.wikimedia.org/wikipedia/commons/8/82/Hibiscus_rosa-sinensis_flower_2.JPG",
             "13245679",
             "123456",
             "湖南醴陵",
             "430253156245",
             "m",
             "终于等到你" +
                     "我喜欢追求自由，与我一起旅行吧！",
             "终于等到你，你这么好看还可以关注我！" +
                     "我喜欢追求自由，与我一起旅行吧 ",
             "100w",
             "10w",
             "123",
             1,
             Date(System.currentTimeMillis()),
             0,
             0
         )

        MutableLiveData(ApiResponse(user, SUCCESS,""))
     }

    suspend fun insertUser(user:User) = withContext(Dispatchers.IO){
        _userDao.insertUser(user)
    }

      suspend fun userLogout(user: User) = withContext(Dispatchers.IO){
          _userDao.deleteUser(user)
          _network.toLogout(user.account)
      }

     suspend fun toAddFriend(friendAccount:String,addWithBackArgs:Map<String,String>) = withContext(Dispatchers.IO){
         _network.toAddUserWithBack(friendAccount,addWithBackArgs)
     }

     suspend fun toQueryFriend(userInfo:String) = withContext(Dispatchers.IO){
         _network.toQueryFriend(userInfo)
     }

     suspend fun toDeleteFriend(friendAccount: String) = withContext(Dispatchers.IO){
         _network.toDeleteFriend(friendAccount)
     }



       companion object{
          @Volatile private  var instance:UserRepository? = null
          fun getInstance(userDao: UserDao):UserRepository{
              return instance ?: synchronized(this){
                  instance ?:UserRepository(userDao).also{ instance = it }
              }
          }
     }

}