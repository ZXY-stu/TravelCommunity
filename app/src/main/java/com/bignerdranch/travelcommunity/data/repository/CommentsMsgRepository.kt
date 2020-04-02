package com.bignerdranch.travelcommunity.data.repository

import com.bignerdranch.travelcommunity.data.db.daos.CommentsMsgDao
import com.bignerdranch.travelcommunity.data.db.entity.CommentsMsg
import com.bignerdranch.travelcommunity.data.network.Network

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

class CommentsMsgRepository private constructor(
    private val commentsMsgDao: CommentsMsgDao,
    private val network: Network
){
    fun getCommentsMsgFromUserId(userId:Int) = commentsMsgDao.getCommentsMsgFromUserId(userId)
    fun getCommentsMsgFromMsgId(msgId:Int) = commentsMsgDao.getCommentsMsgFromMsgId(msgId)
    fun getCommentsMsgFromDynamicId(dynamicId:Int) = commentsMsgDao.getCommentsMsgFromDynamicId(dynamicId)
    fun getCommentsMsgFromUserAndDynamicId(dynamicId: Int,userId: Int) = commentsMsgDao.getCommentsMsgFromUserAndDynamicId(dynamicId,userId)
    fun insertMsg(commentsMsg: CommentsMsg) { commentsMsgDao.insertMsg(commentsMsg) }
    fun insertMsgAll(commentsMsgList: List<CommentsMsg>){ commentsMsgDao.insertMsgAll(commentsMsgList) }
    //删除这个用户的所有评论
    fun deleteUserMsg(userId: Int)  { commentsMsgDao.deleteUserMsg(userId) }
    //删除这个动态的所有评论
    fun deleteDynamicMsg(dynamicId: Int)  { commentsMsgDao.deleteDynamicMsg(dynamicId) }
    //删除指定动态指定用户的评论
    fun deleteByUserIdAndDynamicId(userId: Int,dynamicId: Int) { commentsMsgDao.deleteByUserIdAndDynamicId(userId, dynamicId) }
    //删除这条评论
    fun deleteMsg(msgId: Int) { commentsMsgDao.deleteMsg(msgId) }

    companion object {
        // 单例
        @Volatile  private var  instance:CommentsMsgRepository? = null
        fun getInstance(commentsMsgDao: CommentsMsgDao,network: Network):CommentsMsgRepository{
            return instance?: synchronized(this){
                instance?:CommentsMsgRepository(commentsMsgDao,network).also { instance = it }
            }
        }
    }
}