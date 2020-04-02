package com.bignerdranch.travelcommunity.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bignerdranch.travelcommunity.data.db.entity.User
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
    fun getUser():User

    @Query("SELECT * from user where user.id = :loginId")
    fun getUser(loginId:Int):LiveData<User>

    //for test
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertUser(user:User)

    @Delete
    suspend fun deleteUser(user: User)


}

