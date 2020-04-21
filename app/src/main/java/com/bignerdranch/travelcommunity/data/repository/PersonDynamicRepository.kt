package com.bignerdranch.travelcommunity.data.repository

import com.bignerdranch.travelcommunity.data.db.daos.CommentsMsgDao
import com.bignerdranch.travelcommunity.data.db.daos.LikeDao
import com.bignerdranch.travelcommunity.data.db.daos.PersonDynamicDao
import com.bignerdranch.travelcommunity.data.db.daos.UserDao
import com.bignerdranch.travelcommunity.data.db.entity.AddDynamicArgs
import com.bignerdranch.travelcommunity.data.db.entity.CommentsMsg
import com.bignerdranch.travelcommunity.data.db.entity.Like
import com.bignerdranch.travelcommunity.data.db.entity.PersonDynamic
import com.bignerdranch.travelcommunity.util.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

class PersonDynamicRepository private constructor(
    private val personDynamicDao: PersonDynamicDao,
    private val commentsMsgDao: CommentsMsgDao,
    private val likeDao: LikeDao,
    private val userDao: UserDao
):BaseRepository(personDynamicDao,commentsMsgDao, likeDao,userDao){

    private suspend fun <T> launch(block:suspend ()->T):T = withContext(Dispatchers.IO){ block() }

    /*=================================================================================================*/
    /*动态板块*/
    suspend fun toQueryPersonDynamics(queryDynamicArgs:Map<String,String>) = launch {
        _network.toQueryDynamics(queryDynamicArgs)
    }

    suspend fun toAddDynamic(permissionArgs: HashMap<String,String>,contentsArgs:HashMap<String,RequestBody>) = launch {
        _network.toAddDynamic(contentsArgs)
    }

    suspend fun toDeleteDynamic(dynamicId:Int) = launch {
        _network.toDeleteDynamic(dynamicId)
    }

    fun toQueryPersonDynamicLocal()  = run {
        personDynamicDao.getPersonDynamics()
    }

    suspend fun toDeletePersonDynamicLocal(personDynamic:PersonDynamic) = launch {
        personDynamicDao.deletePersonDynamic(personDynamic)
    }

    suspend fun  toInsertDynamicLocal(personDynamic: PersonDynamic) = launch {
         personDynamicDao.insertPersonDynamic(personDynamic)
    }
    suspend fun  toInsertDynamicAllLocal(personDynamics: List<PersonDynamic>) = launch {
        personDynamicDao.insertAll(personDynamics)
    }


    /*=================================================================================================*/
    /*评论板块*/
    suspend  fun toQueryComments(queryCommentsArgs:Map<String,String>) = launch {
        _network.toQueryComments(queryCommentsArgs)
    }

    suspend  fun  toAddComments(commentsMsg:CommentsMsg) = launch {
        _network.toAddComments(commentsMsg)
    }

    suspend  fun toDeleteComments(msgId:Int) = launch {
        _network.toDeleteComments(msgId)
    }

    fun toQueryCommentsMsgLocal() = run{
        commentsMsgDao.getCommentsMsg()
    }

    suspend fun toInsertCommentsLocal(commentsMsgs: List<CommentsMsg>) = launch {
        commentsMsgDao.insertMsgAll(commentsMsgs)
    }

    suspend fun toInsertCommentLocal(commentsMsg: CommentsMsg) = launch {
        commentsMsgDao.insertMsg(commentsMsg)
    }

    suspend  fun toDeleteCommentLocal(commentsMsg: CommentsMsg) = launch {
        commentsMsgDao.deleteMsg(commentsMsg)
    }


    /*=================================================================================================*/
    /*点赞板块*/

    suspend fun toQueryLike(queryLikeArgs: Map<String, String>) = launch {
            _network.toQueryLike(queryLikeArgs)
    }
    suspend fun toAddLike(likeArgs:Map<String,String>) = launch {
        _network.toAddLike(likeArgs)
    }
    suspend fun toDeleteLike(likeId:Int) = launch {
        _network.toDeleteLike(likeId)
    }

    suspend fun toInsertLikesLocal(likes: List<Like>) = launch {
          likeDao.insertLikes(likes)
    }

    suspend fun toInsertLikeLocal(like: Like) = launch {
        likeDao.insertLike(like)
    }

    suspend fun toDeleteLikeLocal(like: Like) = launch {
         likeDao.deleteLike(like)
    }

     fun toQueryLikeLocal() = run{
        likeDao.queryLike()
    }


    companion object {
        @Volatile
        private var instance: PersonDynamicRepository? = null

        fun getInstance(
            personDynamicDao: PersonDynamicDao,
            commentsMsgDao: CommentsMsgDao,
            likeDao: LikeDao,
            userDao: UserDao
        ): PersonDynamicRepository {
            return instance ?: synchronized(this) {
                instance ?: PersonDynamicRepository(
                    personDynamicDao,
                    commentsMsgDao,
                    likeDao,
                    userDao
                )
                    .also { instance = it }
            }
        }


    }
}