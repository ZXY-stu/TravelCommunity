package com.bignerdranch.travelcommunity.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bignerdranch.travelcommunity.data.db.entity.User
import java.math.BigInteger


/*User表操作接口函数*/
@Dao
interface UserDao {
    @Query("SELECT * from user")
    fun getUser():LiveData<User>

    @Query("SELECT * from user where user.id = :userId")
    fun getUser(userId:Int):LiveData<User>

    @Query("SELECT nick_name from user Where user.id = :userId")
    fun getNickName(userId:Int):LiveData<String>

    @Query("select id from user where user.nick_name = :nickName")
    fun getUserId(nickName:String):LiveData<Int>

    @Query("select * from user where user.nick_name = :nickName")
    fun getUser(nickName: String):LiveData<User>


    //for test
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUser(users:List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertUser(user:User)

    @Delete
    suspend fun deleteUser(userId: Int)
}

