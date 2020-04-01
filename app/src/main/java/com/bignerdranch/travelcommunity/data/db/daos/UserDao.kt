package com.bignerdranch.travelcommunity.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bignerdranch.travelcommunity.data.db.entity.User
import java.math.BigInteger


/*User表操作接口函数*/
@Dao
interface UserDao {
    @Query("SELECT * from user")
    fun getUser():User

    @Query("SELECT * from user where user.id = :loginId")
    fun getUser(loginId:String):LiveData<User>

    //for test
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertUser(user:User)

    @Delete
    suspend fun deleteUser(user: User)


}

