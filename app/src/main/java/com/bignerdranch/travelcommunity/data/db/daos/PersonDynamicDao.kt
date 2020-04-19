package com.bignerdranch.travelcommunity.data.db.daos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.bignerdranch.travelcommunity.data.db.entity.PersonDynamic
import com.bignerdranch.travelcommunity.data.db.entity.User
import java.math.BigInteger

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

/*PersonDynamic表操作接口函数*/
@Dao
interface PersonDynamicDao {
    @Query("SELECT * from PERSON_DYNAMIC")
    fun getPersonDynamics(): List<PersonDynamic>

    @Query("SELECT user_id  from person_dynamic where person_dynamic.id = :personDynamicId")
    fun getUserId(personDynamicId:Int):LiveData<Int>

    @Query("SELECT id  from person_dynamic where person_dynamic.user_id = :userId")
    fun getDynamicId(userId:Int):LiveData<Int>

    //for test
     @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonDynamic(personDynamic: PersonDynamic)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(personDynamics:List<PersonDynamic>)

    @Delete
    suspend fun deletePersonDynamic(personDynamic:PersonDynamic)
}