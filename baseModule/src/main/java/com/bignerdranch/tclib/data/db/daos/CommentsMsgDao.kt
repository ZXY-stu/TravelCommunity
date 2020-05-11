package com.bignerdranch.tclib.data.db.daos
import androidx.lifecycle.LiveData
import androidx.room.*
import com.bignerdranch.tclib.data.db.entity.CommentsMsg

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

@Dao
interface CommentsMsgDao {


    @Query("select * from comment_msg")
     fun getCommentsMsg():LiveData<List<CommentsMsg>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMsgAll(commentsMsgList: List<CommentsMsg>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMsg(commentsMsg: CommentsMsg)

    @Delete
    suspend  fun deleteMsg(commentsMsg: CommentsMsg)  //删除这条评论

    @Delete
    suspend  fun deleteMsgAll(commentsMsgList: List<CommentsMsg>)
}