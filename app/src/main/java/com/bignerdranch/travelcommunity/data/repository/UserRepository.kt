package com.bignerdranch.travelcommunity.data.repository

import androidx.lifecycle.*
import com.bignerdranch.travelcommunity.TCApplication
import com.bignerdranch.travelcommunity.base.NetworkConnectChangedReceiver.Companion.haveNetwork
import com.bignerdranch.travelcommunity.data.db.daos.CommentsMsgDao
import com.bignerdranch.travelcommunity.data.db.daos.LikeDao
import com.bignerdranch.travelcommunity.data.db.daos.PersonDynamicDao
import com.bignerdranch.travelcommunity.data.db.daos.UserDao
import com.bignerdranch.travelcommunity.data.db.entity.*
import com.bignerdranch.travelcommunity.data.network.Network
import com.bignerdranch.travelcommunity.data.network.model.ApiResponse
import com.bignerdranch.travelcommunity.util.LogUtil
import com.bignerdranch.travelcommunity.util.SUCCESS
import com.bignerdranch.travelcommunity.util.ToastUtil
import com.bignerdranch.travelcommunity.util.toJson
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import java.net.PasswordAuthentication
import java.util.*

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

class UserRepository(private val userDao: UserDao):BaseRepository(userDao){


      suspend fun toUserLogin(account:String,password:String) = withContext(Dispatchers.IO) {
          _network.toLogin(account, password)
      }



      suspend fun toUserRegister(user:User) = withContext(Dispatchers.IO){
          _network.toRegister(user)
      }

     suspend fun toUserRegister(account: String,password: String) = withContext(Dispatchers.IO) {
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

    suspend fun toInsertUserLocal(user:User) = withContext(Dispatchers.IO){
        _userDao.insertUser(user)
    }

      suspend fun toUserLogout(user: User) = withContext(Dispatchers.IO){   //本地用户和服务器端均下线
           userDao.deleteUser(user)
          _network.toLogout(user?.account)
      }

    suspend fun toQueryFriend(userInfo:String) = withContext(Dispatchers.IO){     //查找用户
         _network.toQueryFriend(userInfo)
    }

    suspend fun toInsertChatLocal(chat: Chat) = withContext(Dispatchers.IO){
        userDao.insertChat(chat)
    }

    suspend fun  toInsertChatsLocal(chats:List<Chat>) = withContext(Dispatchers.IO){
         userDao.insertChats(chats)
    }

    suspend fun toSendMessage(account:String,chat:Chat) = withContext(Dispatchers.IO){

    }

    suspend fun toQueryFriendList(account: String) = withContext(Dispatchers.IO){
        _network.toQueryFriendList(account)
    }

    suspend fun toUpdateUserInfo(account: String,contentArgs:Map<String,RequestBody>) = withContext(Dispatchers.IO){
        _network.toUpdateUser(account,contentArgs)
    }

    suspend fun toDeleteFriend(friendAccount: String) = withContext(Dispatchers.IO){
        _network.toDeleteFriend(friendAccount)
    }

    suspend fun toDeleteFriendRecord(friendId: FriendId) = withContext(Dispatchers.IO){
         userDao.deleteFriendRecord(friendId)
    }

    suspend fun toDeleteFriendRecordAll(userId: UserId) = withContext(Dispatchers.IO){
        userDao.deleteAllFriendRecord(userId)
    }

    suspend fun toDeleteChat(chat: Chat) = withContext(Dispatchers.IO){
        userDao.deleteChat(chat)
    }

    suspend fun  toDeleteChatAbout(friendId: FriendId) = withContext(Dispatchers.IO){
        userDao.deleteChatAbout(friendId)
    }

    suspend fun  toDeleteChatAll(userId: UserId) = withContext(Dispatchers.IO){
        userDao.deleteAllChat(userId)
    }


    suspend fun toAddFriend(friendAccount:String,addWithBackArgs:Map<String,String>) = withContext(Dispatchers.IO){
        _network.toAddUserWithBack(friendAccount,addWithBackArgs)
    }

    suspend fun toInsertRelationLocal(userRelation: UserRelation) = withContext(Dispatchers.IO) {
        userDao.insertUserRelation(userRelation)
    }

    suspend fun toInsertRelationsLocal(userRelations: List<UserRelation>) = withContext(Dispatchers.IO) {
        userDao.insertUserRelations(userRelations)
    }

    suspend fun  toQueryFriendListLocal() = withContext(Dispatchers.IO){   //获取好友列表
          userDao.getUserRelation()
    }

    suspend fun toDeleteFriendLocal(userRelation: UserRelation) = withContext(Dispatchers.IO){
          userDao.deleteRelation(userRelation)
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