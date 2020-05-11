package com.bignerdranch.tclib.data.repository



import androidx.lifecycle.MutableLiveData
import com.bignerdranch.tclib.data.db.daos.UserDao
import com.bignerdranch.tclib.data.db.entity.*
import com.bignerdranch.tclib.data.network.model.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import java.sql.Timestamp
import java.util.*
import kotlin.collections.HashMap

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

class UserRepository(private val userDao: UserDao):BaseRepository(userDao){


      suspend fun toUserLogin(account:String,password:String) = withContext(Dispatchers.IO) {

          _network.toLogin(account, password)
      }



      suspend fun toUserRegister(user: User) = withContext(Dispatchers.IO){
          _network.toRegister(user)
      }

     suspend fun toUserRegister(account: String,password: String) = withContext(Dispatchers.IO) {
         val user =   User(account=account,password = password)
         _network.toRegister(user)
     }

    suspend fun toInsertUserLocal(user:User) = withContext(Dispatchers.IO){
        _userDao.insertUser(user)
    }

    suspend fun toInsertUserAllLocal(users:List<User>) = withContext(Dispatchers.IO){
        _userDao.insertUsers(users)
    }

      suspend fun toUserLogout(user: User) = withContext(Dispatchers.IO){   //本地用户和服务器端均下线
           userDao.deleteUser(user)
          _network.toLogout(user.account)
      }

    suspend fun toQueryFriend(userInfo:String) = withContext(Dispatchers.IO){     //查找用户
         _network.toQueryFriend(userInfo)
    }

    suspend fun toQueryFriend(userId:Int) = withContext(Dispatchers.IO){     //查找用户
        _network.toQueryFriend(userId)
        MutableLiveData(ApiResponse(data = User(userId,"杀猪"),errorMsg = "",errorCode = 1))
    }

    suspend fun toInsertChatLocal(chat: Chat) = withContext(Dispatchers.IO){
        userDao.insertChat(chat)
    }

    suspend fun  toInsertChatsLocal(chats:List<Chat>) = withContext(Dispatchers.IO){
         userDao.insertChats(chats)
    }

    suspend fun toAddChat(account:String,chat:Chat) = withContext(Dispatchers.IO){
    }

    suspend fun toQueryFriendList(userId:Int) = withContext(Dispatchers.IO){
        _network.toQueryFriendList(userId)
    }

    suspend fun toUpdateUserInfo(user:User,contentArgs:HashMap<String,RequestBody>) = withContext(Dispatchers.IO){
        _network.toUpdateUser(user,contentArgs)
    }

    suspend fun toDeleteFriend(userId:Int) = withContext(Dispatchers.IO){
        _network.toDeleteFriend(userId)
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


    suspend fun toAddFriend(requestArgs: HashMap<String, Any>) = withContext(Dispatchers.IO){
        _network.toAddUserWithBack(requestArgs)
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