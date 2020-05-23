package com.bignerdranch.travelcommunity.ui.dynamic.viewModels

import androidx.lifecycle.*
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.tclib.data.db.entity.CommentsMsg
import com.bignerdranch.tclib.data.db.entity.PersonDynamic
import com.bignerdranch.tclib.data.db.entity.SimpleVideoData
import com.bignerdranch.tclib.data.network.model.ApiResponse
import com.bignerdranch.tclib.data.repository.PersonDynamicRepository
import com.bignerdranch.travelcommunity.base.BaseViewModel
import com.bignerdranch.travelcommunity.task.TaskServer
import com.bignerdranch.travelcommunity.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.absoluteValue

/**
 * @author zhongxinyu
 * @date 2020/4/3
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/



class PersonDynamicViewModel internal constructor(
    private val personDynamicRepository: PersonDynamicRepository
) :
    BaseViewModel<PersonDynamicRepository>(personDynamicRepository) {
    private val pers = ArrayList<PersonDynamic>()

    private var i:Int = 0


     init {

    /*  pers.add(PersonDynamic(id = 1 ,userId = 1,videoUrl = "",
             imageUrls = images[0],headPortraitUrl = images[0],userNickName = "RIO微醺",likesCount = "100w",textContent =
          "原来爱情是：\n" +
                  "\n" +
                  "我正要表白，而你也刚好“正在输入”"))
       pers.add(PersonDynamic(id = 2 ,userId = 2,videoUrl = "http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4",
             imageUrls = "",headPortraitUrl = images[1],userNickName = "江小白",likesCount = "100w",textContent =
             "人生没有早知道，\n" +
                     "\n" +
                     "只有当下酒，眼前人。\n" +
                     "\n"))
         pers.add(PersonDynamic(id = 3 ,userId = 3,videoUrl = "https://media.w3.org/2010/05/sintel/trailer.mp4",
             imageUrls = "",headPortraitUrl = images[2],userNickName = "方太",likesCount = "100w",textContent =
             "我没有离开家，\n" +
                     "\n" +
                     "只是把家带去了远方。"))
         pers.add(PersonDynamic(id = 4 ,userId = 4,videoUrl = "",
             imageUrls = images[3],headPortraitUrl = images[3],userNickName = "唯品会",likesCount = "100w",textContent =
                     "我们的一句随口说说 就是父母的大动干戈。"))

         pers.add(PersonDynamic(id = 55 ,userId = 31,videoUrl = "http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4",
             imageUrls = "",headPortraitUrl = images[4],userNickName = "京东 & 宝洁",likesCount = "100w",textContent =
             "爱在日常，才不寻常。"))
         pers.add(PersonDynamic(id = 44 ,userId = 22,videoUrl = "",
             imageUrls = images[5],headPortraitUrl = images[5],userNickName = "杜蕾斯春日诗集",likesCount = "100w",textContent =
             "我把我种在你身体里\n" +
                     "\n" +
                     "然后一起躲进时间的褶皱里。"))
         pers.add(PersonDynamic(id = 23 ,userId = 32,videoUrl = "https://media.w3.org/2010/05/sintel/trailer.mp4",
             imageUrls = "",headPortraitUrl = images[1],userNickName = "蒙牛",likesCount = "100w",textContent =
             "我不是天生强大，\n" +
                     "\n" +
                     "我只是天生要强。"))
         pers.add(PersonDynamic(id = 34 ,userId = 14,videoUrl = "",
             imageUrls = images[2],headPortraitUrl = images[2],userNickName = "CCTV",likesCount = "100w",textContent =
             "上天给了你太多才华，\n" +
                     "\n" +
                     "就注定不会给你太平坦的路。"))*/

       runBlocking {
          // personDynamicRepository.toInsertDynamicAllLocal(pers)
          personDynamicRepository.toInsertCommentLocal(CommentsMsg(commentGroupId = 0,id = 1,userId = 1,msg = "上天给了你太多才华，就注定不会给你太平坦的路",userNickName = "CCTV",friendNickName = ""))
           personDynamicRepository.toInsertCommentLocal(CommentsMsg(commentGroupId = 1,id = 2,msg = "我没有离开家，\n" +
                   "\n" +
                   "只是把家带去了远方。",userNickName = "方太",friendNickName = "CCTV",likeCount = "10"))
           personDynamicRepository.toInsertCommentLocal(CommentsMsg(commentGroupId = 1,id = 3,msg = "我不是天生强大，我只是天生要强。",
               userNickName = "蒙牛",friendNickName = "CCTV"))
           personDynamicRepository.toInsertCommentLocal(CommentsMsg(commentGroupId = 1,id = 4,msg = "爱在日常，才不寻常。",userNickName = "京东 & 宝洁",friendNickName = "蒙牛"))

           personDynamicRepository.toInsertCommentLocal(CommentsMsg(commentGroupId = 0,id = 5,msg = "我们的一句随口说说 就是父母的大动干戈。",userNickName = "唯品会",friendNickName = ""))
           personDynamicRepository.toInsertCommentLocal(CommentsMsg(commentGroupId = 0,id = 6,msg = "人生没有早知道，\n" +
                   "\n" +
                   "只有当下酒，眼前人。\n" +
                   "\n",userNickName ="江小白" ,friendNickName = ""))
           personDynamicRepository.toInsertCommentLocal(CommentsMsg(commentGroupId = 6,id = 7,msg =  "原来爱情是：\n" +
                   "\n" +
                   "我正要表白，而你也刚好“正在输入”",userNickName = "RIO微醺",friendNickName = "江小白"))
           personDynamicRepository.toInsertCommentLocal(CommentsMsg(commentGroupId = 6,id = 8,msg = "一个个太有才了",userNickName = "zzx",friendNickName = "江小白"))
           personDynamicRepository.toInsertCommentLocal(CommentsMsg(commentGroupId = 6,id = 9,msg =  "我把我种在你身体里\n" +
                   "\n" +
                   "然后一起躲进时间的褶皱里。",userNickName = "春日诗集",friendNickName = "RIO微醺"))
           personDynamicRepository.toInsertCommentLocal(CommentsMsg(commentGroupId = 0,id = 10,msg = "我要的只是诗和远方！",userNickName = "唐骏白",friendNickName = ""))

       }
     }

    val personDynamics = personDynamicRepository.toQueryPersonDynamicLocal()
    val userPersonDynamic = personDynamicRepository.toQueryUserPersonDynamic(userId = getUserId())
    val commentsMsgLocal = personDynamicRepository.toQueryCommentsMsgLocal()
    val likesLocal = personDynamicRepository.toQueryLikeLocal()


    val waitAddComment= executeRequest(toAddComments) {
            personDynamicRepository.toAddComments(currentCommentsMsg!!)
    }

    val addCommentsResult = waitResponseResult(waitAddComment) {
        eee("addCommentsResult"+it)
      // personDynamicRepository.toInsertCommentLocal(it)
    }

    val waitAddDynamic=  executeRequest(toAddDynamic) {
        /* 动态发表
         *  permissionArgs  权限列表
         *  account 发表人的账户
         *  permissionId  隐私设置
         *  0 表示开放
         *  1 表示仅关注的人可见
         *  2 表示自定义，需通过查询权限表获取访问权限
         *  3 表示私密，仅自己可见
         *  userList      用户列表
         *
         * contentArgs  内容列表
         * textContent  动态的文本内容
         * imageUrl   动态的图片文件 最多9张
         * videoUrl   动态的视频文件
         * */
         personDynamicRepository.toAddDynamic(permissionArgs,contentsArgs)
    }

    val addDynamicResult = waitResponseResult(waitAddDynamic){
        eee("得到了dynamic")
        personDynamicRepository.toInsertDynamicLocal(it)
    }

    val waitAddLike = executeRequest(toAddLike){
        //为某个动态或者评论点赞 requestArgs
        // id 评论或动态id
        // userId
        // stat 0动态 1 评论
         personDynamicRepository.toAddLike(requestArgs)

    }

    val addLikeResult = waitResponseResult(waitAddLike){

      //  personDynamicRepository.toInsertLikeLocal(it)
    }


    val waitDeleteLike = executeRequest(toDeleteLike){
        //取消某个动态或者评论的点赞 requestArgs
        // id 评论或动态id
        // userId
        // stat 0动态 1 评论
        personDynamicRepository.toDeleteLike(requestArgs)
    }

    val  deleteLikeResult = waitResponseResult(waitDeleteLike){}

    val waitDeleteDynamic = executeRequest(toDeleteDynamic){
        personDynamicRepository.toDeleteDynamic(_dynamicId)
    }

    val deleteDynamicResult = waitResponseResult(waitDeleteDynamic){}

    val waitDeleteComments = executeRequest(toDeleteComment){
        personDynamicRepository.toDeleteComments(_commentsId)
    }

    val deleteCommentsResult = waitResponseResult(waitDeleteComments){
    }


    /*评论查询 requestArgs
   * dynamicId 动态id,不能为0
   * commentsId 评论Id,若不为0，则查询该评论的所有评论
   * pageNumber 当前页数
   * */
   val  waitQueryComments = executeRequest(toQueryComments){
       personDynamicRepository.toQueryComments(requestArgs)
   }

   val queryCommentsResult = waitResponseResult(waitQueryComments){
        personDynamicRepository.toInsertCommentsLocal(it)
   }

    val waitQueryLike  = executeRequest(toQueryLikes){
        /*查询点赞 requestArgs
         *  dynamicId, 获取该动态或者评论所有用户的点赞
         *  userId，获取该用户所有的对评论或者动态的点赞
         *  stat 0动态  1评论
         *  要么dynamicId == -1 要么userId == -1
         *  两者任选一 进行不同内容的查询
         * */
        personDynamicRepository.toQueryLike(requestArgs)
    }

    val queryLikeResult = waitResponseResult(waitQueryLike){
        personDynamicRepository.toInsertLikesLocal(it)
    }


    val waitQueryDynamic = executeRequest(toQueryDynamic){
        personDynamicRepository.toQueryPersonDynamics(requestArgs)
    }

    val queryDynamicResult = waitResponseResult(waitQueryDynamic){
        if(it.isNotEmpty()){
         eee(""+it)
            personDynamicRepository.toInsertDynamicAllLocal(it)

       }
    }

      init {
          addLikeResult.observeForever {
              executeResult(it,"点赞")
          }

          addDynamicResult.observeForever {
              executeResult(it,"上传")
          }

          addCommentsResult.observeForever {
              executeResult(it,"评论")
          }

          deleteLikeResult.observeForever {
              executeResult(it,"取消点赞")
          }

          deleteDynamicResult.observeForever {
              executeResult(it,"动态删除")
          }

          deleteCommentsResult.observeForever {
              executeResult(it,"评论删除")
          }

          queryLikeResult.observeForever {
              executeResult(it,"查询")
          }

          queryCommentsResult.observeForever {
              executeResult(it,"查询")
          }

          queryDynamicResult.observeForever {
              executeResult(it,"查询")
          }




      }



    /*val commentsMsgs = executeRequestLocal(personDynamics){
        dynamics ->
         dynamics.forEach {
             personDynamics.value?.forEach {

             }
         }
          personDynamicRepository.toQueryCommentsMsgLocal()

    }*/

/*
    val userAccount:String="",
    val friendAccount:String="",
    val pageNumber:Int=1*/

   val wait = Transformations.map(page){
       LogUtil.e("dasdasd")
     //  addTest()
   }

    val waitDynamicResponses = executeRequestList(page){ pa ->
         val  args = HashMap<String,Any>()
         val userAccount = localUser.value?.account?:""
        when(toQueryWhat){
            MY_FOCUSE -> {
                LogUtil.e("执行网络查询 MyFoucs")
                args["userAccount"] = ""+userAccount
                args["friendAccount"] = ""+friendAccount
            }
            SYSTEM_RECOMMAND ->{
                args["userAccount"] = ""
                args["friendAccount"] = ""
            }
            USER_DYNAMIC ->{
                args["userAccount"] = ""
                args["friendAccount"] = ""+friendAccount
            }
        }
         args["pageNumber"] = pa?.toString()?:"1"
         addTest()
         personDynamicRepository.toQueryPersonDynamics(args)
       // MutableLiveData<List<ApiResponse<PersonDynamic>>>(null)
    }


    fun addTest() = runBlocking{
        val l = System.currentTimeMillis().toInt().absoluteValue
       pers.add(
           PersonDynamic(
               l,l, "123", "${l}", "想当小渣男，还是小暖男呢", "", ""
               , images[l%5], ""+l,""+l, ""+Date(System.currentTimeMillis()),
               ""+i, ""+i,  i
           )
       )
        LogUtil.e("$l  ${pers.size}")
      /*  personDynamicRepository.toInsertDynamicAllLocal(
           pers
        )*/
    }










    fun loadingMore(){
        //toQueryDynamic()
        addTest()
        }




    fun videoRefresh(){
     //   toQueryDynamic()
    }



/*
   fun toQueryDynamic(){
       launch{
           val queryArgs = HashMap<String,String>()


           when(toQueryWhat){
               MY_FOCUSE -> {
                   LogUtil.e("执行网络查询 MyFoucs")
                   queryArgs.put("userAccount",""+userAccount)
                   queryArgs.put("friendAccount","")
                   queryArgs.put("pageNumber",""+pageNumber)
               }
               SYSTEM_RECOMMAND ->{
                   LogUtil.e("执行网络查询 System")
                  queryArgs.put("userAccount","")
                   queryArgs.put("friendAccount","")
                  queryArgs.put("pageNumber",""+pageNumber)
               }
           }
         /*  listOf(
               CommentsMsg(2,3,1,"","","","",Date(System.currentTimeMillis()))
           )*/


           //personDynamics.value = personDynamicRepository.toQueryPersonDynamics(queryArgs).value
         //  LogUtil.e("执行网络查询。。。 + ${personDynamics.value}")
       }
   }*/




    fun toVideoSimpleData(value:List<PersonDynamic>):List<SimpleVideoData>{
       return  value.map {
            SimpleVideoData(dynamicId = it.id,
                            videoUrl = ""+it.videoUrl,
                            imageUrls = ""+it.imageUrls,
                            likeCount = it.likesCount)
        }
    }




    companion object{
        const val  MY_FOCUSE = 0    //我的关注内容
        const val SYSTEM_RECOMMAND = 1  //系统推荐内容
        const val USER_DYNAMIC = 2   //用户信息+动态页
        const val USER_WORK = 3  //用户的作品
        const val USER_LIKE = 4 //用户的喜欢
       /*
       *  MY_FOCUSE 表示自动查询我关注的好友动态
       *  SYSTEM_RECOMMAND 表示系统自动推荐比较有火的动态
       * */
        var toQueryWhat = MY_FOCUSE   //默认是我关注的内容

    }
}