package com.bignerdranch.tclib.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bignerdranch.tclib.data.db.entity.*


/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

/*User表操作接口函数*/
@Dao
interface UserDao {
    @Query("SELECT * from user")
    fun getUser():LiveData<User>

    @Query("SELECT * from user where user.id = :userId")
    fun getUser(userId:Int):LiveData<User>

    @Query("SELECT * from user where user.account = :account")
    fun getUser(account:String):LiveData<User>

    @Query("select * from addFriendRecord")
    fun getFriendRecord():LiveData<List<AddFriendRecord>>

    @Query("select * from chat where chat.userId =:userId and chat.friendId = :friendId")
    fun getChat(userId:Int,friendId:Int):LiveData<List<Chat>>

    @Query("select * from user_relation")
    fun getUserRelation():LiveData<List<UserRelation>>   //获取好友关系列表

    @Query("select * from chat")
    fun getChat():LiveData<List<Chat>>


    //for test
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertUser(user:User)

    //for test
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertUsers(user:List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriendRecord(addFriendRecord: AddFriendRecord)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChats(chats:List<Chat>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChat(chat:Chat)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserRelation(userRelation: UserRelation)   //添加一个好友

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserRelations(userRelations: List<UserRelation>)    //插入好友关系表


    @Delete
    suspend fun deleteUser(user: User)

    @Delete(entity = AddFriendRecord::class)
    suspend fun deleteFriendRecord(friendId: FriendId)

    @Delete(entity = AddFriendRecord::class)
    suspend fun deleteAllFriendRecord(userId:UserId)

    @Delete
    suspend fun deleteChat(chat:Chat)

    @Delete(entity = Chat::class)
    suspend  fun deleteAllChat(userId:UserId)

    @Delete(entity = Chat::class)
    suspend  fun deleteChatAbout(friendId: FriendId)

    @Delete
    suspend  fun deleteRelation(userRelation: UserRelation)

}

