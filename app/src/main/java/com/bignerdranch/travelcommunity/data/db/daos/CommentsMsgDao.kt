package com.bignerdranch.travelcommunity.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bignerdranch.travelcommunity.data.db.entity.CommentsMsg

@Dao
interface CommentsMsgDao {

    @Query("select * from comment_msg where comment_msg.user_id = :userId")
    fun getCommentsMsgFromUserId(userId:Int):LiveData<List<CommentsMsg>>

    @Query("select * from comment_msg where comment_msg.id = :msgId")
    fun getCommentsMsgFromMsgId(msgId:Int):LiveData<CommentsMsg>

    @Query("select * from comment_msg where comment_msg.dynamic_id = :dynamicId")
    fun getCommentsMsgFromDynamicId(dynamicId:Int):LiveData<List<CommentsMsg>>

    @Query("select * from comment_msg where comment_msg.dynamic_id = :dynamicId and comment_msg.user_id = :userId")
    fun getCommentsMsgFromUserAndDynamicId(dynamicId: Int,userId: Int):LiveData<List<CommentsMsg>>

    @Insert
    fun insertMsg(commentsMsg: CommentsMsg)

    @Insert
    fun insertMsgAll(commentsMsgList: List<CommentsMsg>)

    @Delete
    fun deleteUserMsg(userId: Int)   //删除这个用户的所有评论

    @Delete
    fun deleteDynamicMsg(dynamicId: Int)  //删除这个动态的所有评论

    @Delete
    fun deleteByUserIdAndDynamicId(userId: Int,dynamicId: Int) //删除指定动态指定用户的评论

    @Delete
    fun deleteMsg(msgId: Int)  //删除这条评论
}