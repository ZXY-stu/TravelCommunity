package com.bignerdranch.travelcommunity.base

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
import com.bignerdranch.travelcommunity.ui.dynamic.PrivateFragment.Companion.OPEN
import com.bignerdranch.travelcommunity.util.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.RequestBody
import java.util.*
import kotlin.collections.HashMap

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
        "https://api.ixiaowai.cn/gqapi/gqapi.php","https://api.ixiaowai.cn/api/api.php",
        "https://api.ixiaowai.cn/mcapi/mcapi.php", "https://api.ixiaowai.cn/api/api.php",
        "https://api.ixiaowai.cn/gqapi/gqapi.php", "https://api.ixiaowai.cn/mcapi/mcapi.php"
    )

    val account = MutableLiveData<String>()   //账号
    val password = MutableLiveData<String>()   //密码
    val code = MutableLiveData<String>()   //验证码



    var _friendId  = -1  //朋友ID
    var _dynamicId  = -1    //动态Id
    var _commentsId  = -1   //评论Id
    var _likeId  = -1       //点赞ID
    var _groupId = 0        //用户组Id
    var isRequireUser = 0   //是否为发起人
    var memo = ""           //备注

    var dynamicPrivateModel = OPEN   //动态权限，默认开放


     var localUserId = 1; //当前登录用户默认ID

     val userOperationList = ArrayList<Int>()   //用户

    protected var editorUser: User? = null

    val localUser = baseRepository._userDao.getUser(localUserId)  //本地用户


    val currentUser = baseRepository._userDao.getUser(_friendId)  //当前查询用户

    val isLogin = Transformations.switchMap(localUser){
            user ->  LogUtil.eee("创建了_baseViewmodel $user")
        MutableLiveData<Boolean>(user != null)
    }
    companion object {
        var userIsLogin = false
        var isParentHaveSetFont = false  //状态栏字体颜色状态
    }
    init {

      // liveDataMap.put("toQueryDynamics",get())
       runBlocking {
          /*  baseRepository._userDao.insertUser(User(userId = 1, headPortraitUrl = images[1],nickName = "ssadasd"))

            baseRepository._userDao.insertUser(User(userId = 2,
               headPortraitUrl = images[2],nickName = "1asd",account = "zxczxczxczc"))
            baseRepository._userDao.insertUser(User(userId = 3,nickName = "wsadsadasd",account = "22222",
                    headPortraitUrl = images[3]))
            baseRepository._userDao.insertUser(User(userId = 4,nickName = "ezx",account = "zxczxssssczxczc", headPortraitUrl = images[4]
            ))*/
        }

        isLogin.observeForever{
           userIsLogin = it
        }
    }

    lateinit var  friendAccount:String

    lateinit var friendCommentsMsg: CommentsMsg

     var currentCommentsMsg:CommentsMsg? = null

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

    val toOpenAlbum    =  get()         //打开相册
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
    val toQueryFriendById=   get()         //查询好友
    val toQueryChat =     get()         //查询聊天记录
    val toQueryFriendList = get()     //查询好友列表
    val toQueryFriendByUserInfo = get()

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

    fun toOpenAlbum(){
        set(toOpenAlbum)
    }

    fun toUpdateNickName(){
        editorUser = localUser.value
        editorUser?.nickName = ""+textContent.value
        set(toUpdateUser)
    }

    fun toUpDateIntroduce(){
        editorUser = localUser.value
        editorUser?.introduce = ""+textContent.value
        set(toUpdateUser)
    }

    fun toUpDateSex(sex:String){
        editorUser = localUser.value
        editorUser?.sex = ""+sex
        set(toUpdateUser)
    }

    fun toUpDateAddress(){
        editorUser = localUser.value
       // editorUser?.add = ""+textContent.value
        //set(toUpdateUser)
    }

    fun toUpdateHeadUrl(url:String){
        editorUser = localUser.value
        editorUser?.headPortraitUrl = ""+url
        set(toUpdateUser)
    }

    fun toUpdateBackGroundUrl(url:String){
      //  editorUser = localUser.value
      //  editorUser?.headPortraitUrl = ""+url
      //  set(toUpdateUser)
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

    /* 查询动态
       *  name = userId 用户id
       *  name = queryId 查询类型
       *  queryId = 0 系统自动推荐用户动态
       *  queryId = 1 查询userId用户好友的动态
       *  queryId = 2 查询userId用户发表过的动态
       *  pageNumber 当前页数
       *  返回最新的10条动态
       * */

    fun setMap(key:String){
        //liveDataMap.get(key)?.value = true
    }

    fun toQueryDynamics(queryId:Int,pageNumber:Int){
        requestArgs.put("userId", currentUser.value?.userId?:-1)
        requestArgs.put("queryId",queryId)
        requestArgs.put("pageNumb" +
                "er",pageNumber)
        set(toQueryDynamic)
    }

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

     fun toQueryFriendByUserInfo() {
        set(toQueryFriendByUserInfo)
     }

    fun toQueryFriendById(friendId:Int) {
        _friendId = friendId
        set(toQueryFriendById)
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
    /* 动态发表
*  permissionArgs  权限列表
*  userId 发表人Id
*  permissionId  隐私设置
*  0 表示开放
*  1 表示仅关注的人可见
*  2 表示自定义，需通过查询权限表获取访问权限
*  3 表示私密，仅自己可见
*  userList     需要处理权限的用户列表，处理后，存入UserDynamicPermission
* contentArgs  动态内容列表
* textContent  动态的文本内容
* imgs   动态的图片文件 最多9张
* video   动态的视频文件
* userNickName 用户昵称
* headPortraitUrl 用户头像
* submitsTime 发布时间
* */
    fun toAddDynamic() {
        permissionArgs["userId"] = getUserId()
        permissionArgs["userList"] = userOperationList
        permissionArgs["permissionId"] = ""+dynamicPrivateModel
        permissionArgs["userNickName"] = ""+localUser.value?.nickName
        permissionArgs["headPortraitUrl"] = ""+localUser.value?.headPortraitUrl
            toAddDynamic.value = true
    }
    //  添加好友
    // 也表示关注好友
    // friendId  对方用户
    // groupId  分组id
    // isRequireUser  是否为发起人 0是 1否
    // memo  添加好友备注内容
    // 若被添加的user的privateMode为0  表示对外开放，申请直接通过
    // 若被添加的user的privateMode为1  表示私密，服务器需通知对方用户，对方确认之后，才可添加 暂时可以不实现这个功能
    // 更新AddFriendRecord表
    fun toAddFriend(friendId: Int?) {
        if(friendId!=null) {
            _friendId = friendId
            requestArgs["friendId"] = friendId
            requestArgs["groupId"] =  _groupId
            requestArgs["isRequireUser"] = isRequireUser
            requestArgs["memo"] = memo
            set(toAddFriend)
        }
    }

    fun toAddComments() {
       // eee("toSetFriendCommentsInfo $friendCommentsMsg ${textContent.value}")
                currentCommentsMsg = CommentsMsg(
            id = System.currentTimeMillis().toInt(),
                                        dynamicId = friendCommentsMsg.dynamicId,
                                        userId =  localUser.value!!.userId,
                                        userNickName = localUser.value!!.nickName,
                                        commentGroupId = friendCommentsMsg.commentGroupId,
                                       friendNickName = friendCommentsMsg.userNickName,
                                       msg =  textContent.value.toString())
        //eee("currentMsg $currentCommentsMsg")

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
            requestArgs.clear()

            eee(""+requestArgs)
            requestArgs["id"] = id
            requestArgs["userId"] = localUser.value?.userId?:0
            requestArgs["stat"] = stat

            eee(""+requestArgs)
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

    protected fun executeResult(id:Int,msg:String) {
        Thread {
            Looper.prepare()  //在子线程调用 Looper.prepare()
            when (id) {
                SUCCESS ->   ToastUtil.show(msg+"成功")
                FAILUER ->  ToastUtil.show(msg+"失败")
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
        contentsArgs.clear()
        permissionArgs.clear()
        requestArgs.clear()
        eee("zhixing")
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

    fun getUserId() = localUser.value?.userId?:-1
    fun mergeData_1(data1:String) = data1 + "评论数"
    fun mergeData_2(data1:String) = "共"+data1 + "条评论"
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) { callbacks.add(callback) }
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) { callbacks.remove(callback) }
    fun notifyValueChanged(fieldId:Int){ callbacks.notifyCallbacks(this,fieldId,null) }
    fun notifyChangedAll(){ callbacks.notifyCallbacks(this,0,null) }

}