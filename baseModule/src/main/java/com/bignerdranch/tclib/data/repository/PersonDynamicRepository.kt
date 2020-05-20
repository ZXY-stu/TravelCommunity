package com.bignerdranch.tclib.data.repository


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.tclib.data.db.daos.CommentsMsgDao
import com.bignerdranch.tclib.data.db.daos.LikeDao
import com.bignerdranch.tclib.data.db.daos.PersonDynamicDao
import com.bignerdranch.tclib.data.db.daos.UserDao
import com.bignerdranch.tclib.data.db.entity.CommentsMsg
import com.bignerdranch.tclib.data.db.entity.EntityId
import com.bignerdranch.tclib.data.db.entity.Like
import com.bignerdranch.tclib.data.db.entity.PersonDynamic
import com.bignerdranch.tclib.data.network.model.ApiResponse
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
    private val userDao: UserDao,
    private val context: Context
):BaseRepository(personDynamicDao,commentsMsgDao, likeDao,userDao,context){

    private var i = 0
    private suspend fun <T> launch(block:suspend ()->T):T = withContext(Dispatchers.IO){ block() }

    /*=================================================================================================*/
    /*动态板块*/
    suspend fun toQueryPersonDynamics(queryDynamicArgs:HashMap<String,Any>) = launch {
        _network.toQueryDynamics(queryDynamicArgs)
    }

    suspend fun toAddDynamic(permissionArgs:HashMap<String,Any>,contentsArgs:HashMap<String,RequestBody>) = launch {
        _network.toAddDynamic(permissionArgs,contentsArgs)
   /*     i++
        MutableLiveData(ApiResponse(data = PersonDynamic(videoUrl = permissionArgs["imageFiles"].toString(),
            textContent = permissionArgs["textContent"].toString(),id = i),errorCode = 1,
            errorMsg = ""
        ))*/
    }

    suspend fun toDeleteDynamic(dynamicId:Int) = launch {
        toDeletePersonDynamicLocalById(dynamicId)
        _network.toDeleteDynamic(dynamicId)
    }

   fun toQueryPersonDynamicLocal()  = personDynamicDao.getPersonDynamics()
    fun toQueryUserPersonDynamic(userId:Int) = personDynamicDao.getPersonDynamicsById(userId)

    suspend fun toDeletePersonDynamicLocal(personDynamic: PersonDynamic) = launch {
        personDynamicDao.deletePersonDynamic(personDynamic)
    }

    suspend fun toDeletePersonDynamicLocalById(id:Int) = launch {
        personDynamicDao.deletePersonDynamicById(EntityId(id))
    }

    suspend fun  toInsertDynamicLocal(personDynamic: PersonDynamic) = launch {
         personDynamicDao.insertPersonDynamic(personDynamic)
    }
    suspend fun  toInsertDynamicAllLocal(personDynamics: List<PersonDynamic>) = launch {
        personDynamicDao.insertAll(personDynamics)
    }


    /*=================================================================================================*/
    /*评论板块*/
    suspend  fun toQueryComments(queryCommentsArgs:HashMap<String,Any>) = launch {
        _network.toQueryComments(queryCommentsArgs)
    }

    suspend  fun  toAddComments(commentsMsg:CommentsMsg) = launch {
        _network.toAddComments(commentsMsg)
       // MutableLiveData(ApiResponse(data = commentsMsg, errorCode = 0, errorMsg = ""))
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
     //   MutableLiveData(commentsMsg)
    }

    suspend  fun toDeleteCommentLocal(commentsMsg: CommentsMsg) = launch {
        commentsMsgDao.deleteMsg(commentsMsg)
    }


    /*=================================================================================================*/
    /*点赞板块*/

    suspend fun toQueryLike(queryLikeArgs: HashMap<String, Any>) = launch {
            _network.toQueryLike(queryLikeArgs)
    }
    suspend fun toAddLike(likeArgs:HashMap<String,Any>) = launch {
        _network.toAddLike(likeArgs)
    }
    suspend fun toDeleteLike(likeArgs:HashMap<String,Any>) = launch {
        _network.toDeleteLike(likeArgs)
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
            userDao: UserDao,
            context: Context
        ): PersonDynamicRepository {
            return instance ?: synchronized(this) {
                instance ?: PersonDynamicRepository(
                    personDynamicDao,
                    commentsMsgDao,
                    likeDao,
                    userDao,
                    context
                ).also { instance = it }
            }
        }
    }
}