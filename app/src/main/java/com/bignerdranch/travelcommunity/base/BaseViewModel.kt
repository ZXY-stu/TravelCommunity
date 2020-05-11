package com.bignerdranch.travelcommunity.base

import android.content.Context
import android.net.Uri
import android.os.Looper
import android.text.TextUtils
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.*
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.tclib.data.db.entity.CommentsMsg
import com.bignerdranch.tclib.data.db.entity.User
import com.bignerdranch.tclib.data.network.model.ApiResponse
import com.bignerdranch.tclib.data.repository.BaseRepository
import com.bignerdranch.travelcommunity.util.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.RequestBody
import java.util.*
import kotlin.collections.HashMap
import kotlin.properties.Delegates

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

//可实现双向绑定的ViewModel
open class BaseViewModel<T: BaseRepository>(protected val baseRepository: T):ViewModel(),Observable{
    private val callbacks:PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    private fun get() = MutableLiveData<Boolean>()  //获取一个MutableLiveDate实例
    private fun  set(data:MutableLiveData<Boolean>){ data.value = true }   //设置状态变化



    val images = listOf(
        "https://upload.wikimedia.org/wikipedia/commons/6/67/Mangos_criollos_y_pera.JPG","https://upload.wikimedia.org/wikipedia/commons/0/03/Grape_Plant_and_grapes9.jpg",
        "https://upload.wikimedia.org/wikipedia/commons/2/22/Apfelsinenbaum--Orange_tree.jpg", "https://upload.wikimedia.org/wikipedia/commons/a/aa/Sunflowers_in_field_flower.jpg",
        "https://api.ixiaowai.cn/gqapi/gqapi.php","https://upload.wikimedia.org/wikipedia/commons/a/ab/Cypripedium_reginae_Orchi_004.jpg"
    )

    val account = MutableLiveData<String>()   //账号
    val password = MutableLiveData<String>()   //密码
    val code = MutableLiveData<String>()   //验证码


    var _friendId  = -1  //朋友ID
    var _dynamicId  = -1    //动态Id
    var _commentsId  = -1   //评论Id
    var _likeId  = -1       //点赞ID
    private var userId = 1; //当前登录用户默认ID

    val localUser = baseRepository._userDao.getUser(userId)
    val currentUser = baseRepository._userDao.getUser(_friendId)

    val isLogin = Transformations.switchMap(localUser){
            user ->  LogUtil.e("创建了_baseViewmodel")
        MutableLiveData<Boolean>(user != null)
    }
    companion object {
        var userIsLogin = false
    }
    init {
        runBlocking {
            baseRepository._userDao.insertUser(User(userId = 1, headPortraitUrl = images[1],nickName = "ssadasd"))

            baseRepository._userDao.insertUser(User(userId = 2,
               headPortraitUrl = images[2],nickName = "1asd",account = "zxczxczxczc"))
            baseRepository._userDao.insertUser(User(userId = 3,nickName = "wsadsadasd",account = "22222",
                    headPortraitUrl = images[3]))
            baseRepository._userDao.insertUser(User(userId = 4,nickName = "ezx",account = "zxczxssssczxczc", headPortraitUrl = images[5]
            ))
        }

        isLogin.observeForever{
           userIsLogin = it
        }
    }

    lateinit var  friendAccount:String

    lateinit var friendCommentsMsg: CommentsMsg

    lateinit var currentCommentsMsg:CommentsMsg

    /*动作的触发标志*/
    val refreshTrigger = MutableLiveData<Boolean>()  //是否触发刷新

    protected val page = MutableLiveData<Int>(0)   //当前的页面
    val refreshing = MutableLiveData<Boolean>(false)    // 是否正在刷新
    val moreLoading = MutableLiveData<Boolean>()   //是否正在加载更多
    val hasMore = MutableLiveData<Boolean>()     //是否有更多数据
    val autoRefresh = MutableLiveData<Boolean>()   //是否自动刷新

    val loading = MutableLiveData<Boolean>(false)    //正在加载..

    val close = MutableLiveData<Boolean>()   //关闭某个页面
    val navigationTo = MutableLiveData<Boolean>()  //进行页面跳转


    val textContent = MutableLiveData<String>()

    val requestArgs = HashMap<String,Any>()    //改动HashMap<String,String> -> HashMap<String,Any>()
    val commentsArgs = HashMap<String, String>()

    var permissionArgs = HashMap<String, Any>()
    var contentsArgs = HashMap<String, RequestBody>()




    var _userInfo = ""  //要查询的用户账号或者昵称


    val toStopVideo    =  get()
    val toPublishPage  =  get()         //去发布页面
    val toUserPage     =  get()         //去朋友用户页
    val toOpenUserMenu =  get()         //用户页右侧菜单
    val toCommentsPage =  get()         //用户评论页
    val toRegisterPage =  get()         //用户注册页
    val toChatPage =      get()         //用户聊天页
    val toLoginPage =     get()         //去登录页面
    val toRegister =      get()         //进行注册
    val toLogin =         get()         //进行登录
    val toLogout =        get()         //注销
    val toFindPassword =  get()         //忘记密码
    val toUpdateUser   =  get()        //更新用户信息


    val toAddLike =       get()         //点赞
    val toAddComments =   get()         //评论
    val toAddFriend =     get()         //关注
    val toAddDynamic =    MutableLiveData<Boolean>()          //发表动态
    val toAddChat =       get()         //发消息

    val toDeleteFriend =  get()         //取消关注
    val toDeleteComment = get()         //删除评论
    val toDeleteDynamic = get()         //删除动态
    val toDeleteLike =    get()         //取消点赞
    val toDeleteChat =    get()         //删除聊天记录

    val toQueryLikes =    get()         //查询点赞
    val toQueryDynamic =  get()         //查询动态
    val toQueryComments = get()         //查询评论
    val toQueryFriend =   get()         //查询好友
    val toQueryChat =     get()         //查询聊天记录
    val toQueryFriendList = get()     //查询好友列表

    fun toLogin() {
        if(checkError())
        set(toLogin)
    }


    fun toLogout() =     set(toLogout)

    fun toRegister() {
        if(checkError())
            set(toRegister)
    }

    fun toUpdateUser(){
        set(toUpdateUser)
    }

    fun toFindPassword() =  set(toFindPassword)

    fun toLoginPage()    =  set(toLoginPage)
    fun toOpenUserMenu() =  set(toOpenUserMenu)
    fun toCommentsPage() =  set(toCommentsPage)
    fun toRegisterPage() =  set(toRegisterPage)
    fun toPublishPage() =   set(toPublishPage)

    fun close() {
    }

    fun toStopVideo(dynamicId:Int?){
        if (dynamicId != null) {
            _dynamicId = dynamicId
        }
        set(toStopVideo)
    }

    fun toChatPage(friendId: Int) {
         _friendId = friendId
        set(toChatPage)
    }

    fun toQueryDynamics() = set(toQueryDynamic)

    fun toQueryLikes(dynamicId: Int) {
         _dynamicId = dynamicId
        set(toQueryLikes)
    }

    fun toQueryComments(dynamicId: Int) {
        _dynamicId = dynamicId
        set(toQueryComments)
    }

    fun toQueryComments(friendId: Int,dynamicId: Int) {
        _friendId = friendId
        _dynamicId = dynamicId
        set(toQueryComments)
    }

     fun toQueryChat(friendId: Int) {
         _friendId = friendId
         // friendId 和 userId
         set(toQueryChat)
     }

     fun toQueryFriend() {
        set(toQueryFriend)
     }

    fun toDeleteFriend(friendId: Int) {
         _friendId = friendId
        set(toDeleteFriend)
    }

    fun toQueryFriendList(){
        set(toQueryFriendList)
    }

    fun toDeleteLike(id: Int,stat: Int) {
        /*
       * stat = 0  取消动态点赞
       * stat = 1  取消评论点赞
       * */
        requestArgs["id"] =   id
        requestArgs["userId"] = localUser.value!!.userId
        requestArgs["stat"] =  stat
        set(toDeleteLike)
    }

    fun toDeleteDynamic(dynamicId:Int) {
        _dynamicId  =  dynamicId
        set(toDeleteDynamic)
    }

    fun toDeleteComment(commentsId:Int) {
        _commentsId = commentsId
        set(toDeleteComment)
    }
    fun toDeleteChar() {
        set(toDeleteChat)
    }
    fun toUserPage(friendId: Int) {
        _friendId = friendId
        set(toUserPage)
    }
    fun toAddDynamic(image: Uri,context: Context) {
        LogUtil.e("变化了")
        permissionArgs["imageFiles"] = PathUtils.getPath(context,image)
        permissionArgs["textContent"] = ""+textContent.value
        toAddDynamic.value = true
    }

    fun toAddFriend(friendId: Int?) {
        if(friendId!=null) {
            _friendId = friendId
            set(toAddFriend)
        }
    }

    fun toAddComments() {
        eee("toSetFriendCommentsInfo $friendCommentsMsg")
        currentCommentsMsg = CommentsMsg(
            id = System.currentTimeMillis().toInt(),
                                        dynamicId = friendCommentsMsg.dynamicId,
                                        user_id = localUser.value!!.userId,
                                        userNickName = localUser.value!!.nickName,
                                        userAccount = localUser.value!!.account,
                                        commentGroupId = friendCommentsMsg.commentGroupId,
                                       friendNickName = friendCommentsMsg.userNickName,
                                       msg =  textContent.value.toString())
        set(toAddComments)
    }

    fun toSetFriendCommentsInfo(commentMsg:CommentsMsg){

      this.friendCommentsMsg = commentMsg
        eee("toSetFriendCommentsInfo $commentMsg")
    }

    fun toSetCurrentCommentsInfo(commentMsg: CommentsMsg){
        this.currentCommentsMsg = commentMsg
    }

    fun toAddLike(id: Int?,stat:Int){
        /*
        * stat = 0  给动态点赞  id为动态id
        * stat = 1  给评论点赞  id为评论id
        * */
        if(id!=null) {
            requestArgs["id"] = id
            requestArgs["userId"] = localUser.value?.userId?:0
            requestArgs["stat"] = stat
            set(toAddLike)
        }
    }

    fun toAddChat() {
        set(toAddChat)
    }

    fun toAddHomeVideoPage() {
        LogUtil.e("toAddHomePage")
        page.value?.plus(1)
        LogUtil.e(page.value.toString()+"wode")
    }


    fun attachLoading(state:MutableLiveData<Boolean>){
        loading.observeForever{
            state.value = it
        }
    }

    fun attachPublishPage(state:MutableLiveData<Boolean>){
        eee("attachPublishPage")
        toPublishPage.observeForever{
            eee("toPublishPage changed")
            state.value = it

        }
    }

    fun attachClose(state:MutableLiveData<Boolean>){
        close.observeForever{
            state.value = it
        }
    }

    fun dismiss(){

    }

    protected   fun launch(block: suspend () -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    protected fun executeResult(id:Int) {
        Thread {
            Looper.prepare()  //在子线程调用 Looper.prepare()
            when (id) {
                SUCCESS ->   ToastUtil.show("成功")
                FAILUER ->  ToastUtil.show("失败")
                SERVER_OR_NETWORK_ERROR -> ToastUtil.show("服务器出错！")
            }
            Looper.loop() //在子线程调用 Looper.loop()执行UI操作
        }.start()
    }


    private fun checkError():Boolean{
       return if(!TextUtils.isEmpty(account.value) && !TextUtils.isEmpty(password.value)){
           true
        }else {
           if (TextUtils.isEmpty(account.value) && TextUtils.isEmpty(password.value)){
               showInfo("账号和密码不能为空")
           }else{
               showInfo("账号或密码不能为空")
           }
           false
       }
    }

    private fun showInfo(msg:String){
        Thread{
            Looper.prepare()
            ToastUtil.show(msg)
            Looper.loop()
        }.start()
    }

    protected fun <T,R> executeRequestLocal(liveData: LiveData<List<T>>, block: suspend (List<T>) -> LiveData<R>):LiveData<R>{
        return Transformations.switchMap(liveData){
            runBlocking {
                block(it)
            }
        }
    }

    protected fun <T,R> executeRequestList(liveData: LiveData<T>, block: suspend (T) -> LiveData<ApiResponse<List<R>>>):LiveData<ApiResponse<List<R>>>{
        return Transformations.switchMap(liveData){
            runBlocking {
                block(it)
            }
        }
    }

    protected fun <T,R> executeRequest(liveData: LiveData<T>, block: suspend (T) -> LiveData<ApiResponse<R>>):LiveData<ApiResponse<R>>{
        return Transformations.switchMap(liveData){
            runBlocking {
                block(it)
            }
        }
    }

    protected fun <T> waitResponseResult(liveData:LiveData<ApiResponse<T>>, block: suspend (T) -> Unit):LiveData<Int>{
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

    fun mergeData(data1:String) = data1 + "评论数"
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) { callbacks.add(callback) }
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) { callbacks.remove(callback) }
    fun notifyValueChanged(fieldId:Int){ callbacks.notifyCallbacks(this,fieldId,null) }
    fun notifyChangedAll(){ callbacks.notifyCallbacks(this,0,null) }

}