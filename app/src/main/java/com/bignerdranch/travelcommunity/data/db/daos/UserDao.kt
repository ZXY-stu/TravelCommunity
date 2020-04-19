package com.bignerdranch.travelcommunity.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bignerdranch.travelcommunity.data.db.entity.*
import com.bignerdranch.travelcommunity.data.network.model.ApiResponse
import java.math.BigInteger

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

    @Query("SELECT * from user where user.id = :loginId")
    fun getUser(loginId:Int):LiveData<User>

    //for test
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertUser(user:User):Long

    @Delete
    suspend fun deleteUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriendRecord(addFriendRecord: AddFriendRecord)

    @Query("select * from addFriendRecord")
    fun getFriendRecord():LiveData<List<AddFriendRecord>>

    @Delete(entity = AddFriendRecord::class)
    fun deleteFriendRecord(friendId: FriendId)

    @Delete(entity = AddFriendRecord::class)
    fun deleteAllFriendRecord(userId:UserId)

    @Query("select * from chat where chat.userId =:userId and chat.friendId = :friendId")
    fun getChat(userId:Int,friendId:Int):LiveData<List<Chat>>

    @Delete
    fun deleteChat(chat:Chat)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChats(chats:List<Chat>)

    @Query("select * from chat")
    fun getChat():LiveData<List<Chat>>

    @Query("select * from user_relation")
    fun getUserRelation():LiveData<List<UserRelation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserRelation(userRelation: UserRelation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserRelations(userRelations: List<UserRelation>)

}

