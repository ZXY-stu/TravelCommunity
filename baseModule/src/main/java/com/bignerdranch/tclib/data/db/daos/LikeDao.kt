package com.bignerdranch.tclib.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bignerdranch.tclib.data.db.entity.Like


/**
 * @author zhongxinyu
 * @date 2020/4/3
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
@Dao
interface LikeDao {

    // 查询类的函数不能加suspend
    @Query(value = "select * from `like`")
     fun queryLike():LiveData<List<Like>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLike(like: Like)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLikes(likes:List<Like>)

    @Delete
    suspend fun deleteLike(like: Like)
}