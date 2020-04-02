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


    //删除这条评论
    fun deleteMsg(commentsMsg: CommentsMsg) { commentsMsgDao.deleteMsg(commentsMsg) }

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