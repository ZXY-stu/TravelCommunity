package com.bignerdranch.travelcommunity.data.repository

import com.bignerdranch.travelcommunity.data.db.daos.CommentsMsgDao
import com.bignerdranch.travelcommunity.data.db.daos.LikeDao
import com.bignerdranch.travelcommunity.data.db.daos.PersonDynamicDao
import com.bignerdranch.travelcommunity.data.db.daos.UserDao
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




    /*=================================================================================================*/
    /*动态板块*/
    suspend fun toQueryPersonDynamics(queryDynamicArgs:Map<String,String>)
   = withContext(Dispatchers.IO){
    //  var personDynamics = if(NetWorkStateReceiver.haveNetwork)

          _network.toQueryDynamics(queryDynamicArgs)
        //  else  personDynamicDao.getPersonDynamics()

     /*   if(NetWorkStateReceiver.haveNetwork && personDynamics.isNotEmpty())
        personDynamicDao.insertAll(personDynamics)
*/
      // personDynamics
    }
    suspend fun toUploadDynamic(permissionArgs:Map<String,String>,contentsArgs:Map<String,RequestBody>) =  withContext(Dispatchers.IO){
        _network.toUploadDynamic(permissionArgs,contentsArgs)
    }
    suspend fun  toInsertDynamicToLocal(personDynamic: PersonDynamic) = withContext(Dispatchers.IO){
         personDynamicDao.insertPersonDynamic(personDynamic)
    }

    suspend fun  toInsertAll(personDynamics: List<PersonDynamic>) = withContext(Dispatchers.IO) {
        personDynamicDao.insertAll(personDynamics)
    }
    /*=================================================================================================*/
    /*评论板块*/
    suspend  fun toQueryCommentsMsg(queryCommentsArgs:Map<String,String>) = withContext(Dispatchers.IO){

    }
    suspend  fun  toSendComments(message:String,commentsArgs:Map<String,String>) = withContext(Dispatchers.IO){
        _network.toComments(message,commentsArgs)
    }
    suspend  fun toDeleteComments(msgId:Int) = withContext(Dispatchers.IO){
        _network.toDeleteComments(msgId)
    }
    suspend fun toInsertComments(commentsMsg: CommentsMsg) = withContext(Dispatchers.IO){
        commentsMsgDao.insertMsg(commentsMsg)
    }
    suspend  fun toDeleteLocalMsg(commentsMsg: CommentsMsg) = withContext(Dispatchers.IO) {
        commentsMsgDao.deleteMsg(commentsMsg)
    }
    suspend  fun toDeleteLocalMsgs(commentsMsgs: List<CommentsMsg>) = withContext(Dispatchers.IO) {
        commentsMsgDao.deleteMsgAll(commentsMsgs)
    }

    /*=================================================================================================*/
    /*点赞板块*/

    suspend fun toQueryLike(queryLikeArgs: Map<String, String>) = withContext(Dispatchers.IO){
    /*    var likes = if (NetWorkStateReceiver.haveNetwork) {
            _network.toQueryLike(queryLikeArgs)
        } else likeDao.queryLike()
        if(NetWorkStateReceiver.haveNetwork) {   //有网络，更新本地数据库中的内容

        }
        likes*/
    }

    suspend fun toInsertLike(like: Like) = withContext(Dispatchers.IO){
        likeDao.insertLike(like)
    }

    suspend fun toInsertLike(likes: List<Like>) = withContext(Dispatchers.IO){
        likeDao.insertLikes(likes)
    }

    suspend fun toAddLike(likeArgs:Map<String,String>) = withContext(Dispatchers.IO){
        _network.toAddLike(likeArgs)
    }

    suspend fun toCancelLike(likeId:Int) = withContext(Dispatchers.IO){
        _network.toCancelLike(likeId)
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