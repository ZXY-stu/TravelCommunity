package com.bignerdranch.travelcommunity.ui.dynamic.viewModels

import androidx.lifecycle.*
import com.bignerdranch.travelcommunity.data.db.entity.CommentsMsg
import com.bignerdranch.travelcommunity.data.db.entity.PersonDynamic
import com.bignerdranch.travelcommunity.data.db.entity.QueryDynamicArgs
import com.bignerdranch.travelcommunity.data.db.entity.SimpleVideoData
import com.bignerdranch.travelcommunity.data.repository.PersonDynamicRepository
import com.bignerdranch.travelcommunity.base.BaseViewModel
import com.bignerdranch.travelcommunity.data.model.PersonDynamicBody
import com.bignerdranch.travelcommunity.data.network.model.ApiResponse
import com.bignerdranch.travelcommunity.util.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.RequestBody
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * @author zhongxinyu
 * @date 2020/4/3
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/



class PersonDynamicViewModel internal constructor(
    private val personDynamicRepository: PersonDynamicRepository) :
    BaseViewModel<PersonDynamicRepository>(personDynamicRepository) {

    val personDynamics = MutableLiveData<List<PersonDynamic>>()

    val queryDynamicArgs = MutableLiveData<QueryDynamicArgs>()
    val textContent = MutableLiveData<String>()
    val permissionArgs = HashMap<String, String>()
    val contentsArgs = HashMap<String, RequestBody>()
    val toUpLoad = MutableLiveData<Boolean>()
    val toCommentsPage = MutableLiveData<Boolean>()
    val toComments = MutableLiveData<Boolean>()
    val commentsMsg = MutableLiveData<String>()
    val commentsArgs = HashMap<String, String>()
    val toLike = MutableLiveData<Boolean>()
    val likeArgs = HashMap<String,String>()
    val toQueryDynamic = MutableLiveData<Boolean>()
    val toQueryComments = MutableLiveData<Boolean>()
    val toQueryLikes = MutableLiveData<Boolean>()



    val waitCommentsResponse = executeRequest(toComments) {
            personDynamicRepository.toSendComments(commentsMsg.value.toString(), commentsArgs)
    }

    val commentsResponsResult = waitResponseResult(waitCommentsResponse) {
        personDynamicRepository.toInsertComments(it)

    }

    val waitDynamicResponse =  executeRequest(toUpLoad){
        personDynamicRepository.toUploadDynamic(permissionArgs, contentsArgs)
    }

    val dynamicResponseResult = waitResponseResult(waitDynamicResponse){
        personDynamicRepository.toInsertDynamicToLocal(it)
    }

    val waitLikeResponse = executeRequest(toLike){
         personDynamicRepository.toAddLike(likeArgs)
    }

    val likeResponseResult = waitResponseResult(waitLikeResponse){
        personDynamicRepository
    }






    fun <T,R> executeRequest(liveData: LiveData<T>,block: suspend () -> LiveData<ApiResponse<R>>):LiveData<ApiResponse<R>>{
        return Transformations.switchMap(liveData){
            runBlocking {
                  block()
            }
        }
    }


    fun <T> waitResponseResult(liveData:LiveData<ApiResponse<T>>,block: suspend (T) -> Unit):LiveData<Int>{
            return Transformations.map(liveData){
                 it?.run {
                     when(errorCode){
                         SUCCESS -> {
                             data?.also {
                                 runBlocking {
                                     block(it)
                                 }
                             }
                             SUCCESS
                         }
                         else -> FAILUER
                     }
                 }?: SERVER_OR_NETWORK_ERROR
            }
    }







    val dynamicBody = PersonDynamicBody()
    val pers = ArrayList<PersonDynamic>()
    val images = listOf(
        "https://upload.wikimedia.org/wikipedia/commons/6/67/Mangos_criollos_y_pera.JPG","https://upload.wikimedia.org/wikipedia/commons/0/03/Grape_Plant_and_grapes9.jpg",
        "https://upload.wikimedia.org/wikipedia/commons/2/22/Apfelsinenbaum--Orange_tree.jpg", "https://upload.wikimedia.org/wikipedia/commons/a/aa/Sunflowers_in_field_flower.jpg",
        "https://api.ixiaowai.cn/gqapi/gqapi.php","https://upload.wikimedia.org/wikipedia/commons/a/ab/Cypripedium_reginae_Orchi_004.jpg"
    )
    var i:Int = 0


    init{

        queryDynamicArgs.postValue(QueryDynamicArgs())



    }




    fun videoRefresh(){

        toQueryDynamic()
    }

    fun upLoad(){
        toUpLoad.value = true
    }



    fun setQueryDynamicArgs(userAccount:String,friendAccount: String,pageNumber:Int){
        launch {
            queryDynamicArgs.postValue(QueryDynamicArgs(
                userAccount = userAccount,
                friendAccount = friendAccount,
                pageNumber = pageNumber))
        }
    }

    fun initUserVideo(){

    }

    suspend fun insertDynamic(personDynamic: PersonDynamic) = personDynamicRepository.toInsertDynamicToLocal(personDynamic)

    suspend fun  insertAll(personDynamics: List<PersonDynamic>)  = personDynamicRepository.toInsertAll(personDynamics)


    fun initHomePageVideo(){
        init{
            toQueryDynamic()
            toQueryComments()
        }
    }

    fun toQueryComments(){

    }


    fun toCommentsPage(){
        toCommentsPage.value = true
    }

    fun toLike(){
        toLike.value = true
    }

    fun toComments(){
        toComments.value = true
    }


   fun toQueryDynamic(){
       launch{
           val queryArgs = HashMap<String,String>()

           val userAccount = queryDynamicArgs.value?.userAccount
           val pageNumber = queryDynamicArgs.value?.pageNumber
           val friendAccount = queryDynamicArgs.value?.friendAccount
           LogUtil.e(""+pageNumber+""+friendAccount)
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


           pers.add(
               PersonDynamic(++i,1,"123","小暖男","想当小渣男，还是小暖男呢","",""
                   ,images[i%images.size],i,i, Date(System.currentTimeMillis()),"",i,i,i,i))
           personDynamics.value = pers

           personDynamicRepository.toInsertAll(pers)
           //personDynamics.value = personDynamicRepository.toQueryPersonDynamics(queryArgs).value
         //  LogUtil.e("执行网络查询。。。 + ${personDynamics.value}")
       }
   }


    fun toVideoSimpleData(value:List<PersonDynamic>):List<SimpleVideoData>{
       return  value.map {
            SimpleVideoData(dynamicId = it.id,
                            videoUrl = it.videoUrl,
                            imageUrls = it.imageUrls,
                            likeCount = it.likesCount)
        }
    }


   private  fun launch(block:suspend ()->Unit) = viewModelScope.launch{
        block()
    }

    companion object{
        const val  MY_FOCUSE = 0    //我的关注内容
        const val SYSTEM_RECOMMAND = 1  //系统推荐内容
        const val USER_DYNAMIC = 2   //用户信息+动态页
       /*
       *  MY_FOCUSE 表示自动查询我关注的好友动态
       *  SYSTEM_RECOMMAND 表示系统自动推荐比较有火的动态
       * */
        var toQueryWhat = MY_FOCUSE   //默认是我关注的内容

    }
}