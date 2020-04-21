package com.bignerdranch.travelcommunity.base

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.*
import com.bignerdranch.travelcommunity.data.ErrorCode
import com.bignerdranch.travelcommunity.data.db.entity.CommentsMsg
import com.bignerdranch.travelcommunity.data.db.entity.PersonDynamic
import com.bignerdranch.travelcommunity.data.db.entity.QueryDynamicArgs
import com.bignerdranch.travelcommunity.data.repository.BaseRepository
import com.bignerdranch.travelcommunity.util.LogUtil
import okhttp3.RequestBody
import kotlin.properties.Delegates

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

//可实现双向绑定的ViewModel
open class BaseViewModel<T:BaseRepository>(protected val baseRepository: T):ViewModel(),Observable{
    private val callbacks:PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    private fun get() = MutableLiveData<Boolean>()  //获取一个MutableLiveDate实例
    private fun  set(data:MutableLiveData<Boolean>){ data.value = true }   //设置状态变化
    val localUser = baseRepository._userDao.getUser()
    val isLogin = Transformations.switchMap(localUser){
            user ->   MutableLiveData<Boolean>(user != null)
    }

    lateinit var  friendAccount:String

    /*动作的触发标志*/
    val refreshTrigger = MutableLiveData<Boolean>()  //是否触发刷新

    protected val page = MutableLiveData<Int>(0)   //当前的页面
    val refreshing = MutableLiveData<Boolean>(false)    // 是否正在刷新
    val moreLoading = MutableLiveData<Boolean>()   //是否正在加载更多
    val hasMore = MutableLiveData<Boolean>()     //是否有更多数据
    val autoRefresh = MutableLiveData<Boolean>()   //是否自动刷新

    val loading = MutableLiveData<Boolean>()    //正在加载..

    val close = MutableLiveData<Boolean>()   //关闭某个页面
    val navigationTo = MutableLiveData<Boolean>()  //进行页面跳转


    val textContent = MutableLiveData<String>()

    val likeArgs = HashMap<String,String>()
    val commentsArgs = HashMap<String, String>()
    var permissionArgs = HashMap<String, String>()
    var contentsArgs = HashMap<String, RequestBody>()

    var _friendId by Delegates.notNull<Int>()   //朋友ID
    var _dynamicId by Delegates.notNull<Int>()   //动态Id
    var _commentsId by Delegates.notNull<Int>()  //评论Id
    var _likeId by Delegates.notNull<Int>()      //点赞ID


    val toUserPage     =  get()         //去朋友用户页
    val toOpenUserMenu =  get()         //用户页右侧菜单
    val toCommentsPage =  get()         //用户评论页
    val toRegisterPage =  get()         //用户注册页
    val toChatPage =      get()         //用户聊天页

    val toRegister =      get()         //进行注册
    val toLogin =         get()         //进行登录
    val toLogout =        get()         //注销
    val toFindPassword =  get()         //忘记密码


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

    fun toLogin() =         set(toLogin)
    fun toLogout() =        set(toLogout)
    fun toRegister() =      set(toRegister)
    fun toFindPassword() =  set(toFindPassword)

    fun toOpenUserMenu() =  set(toOpenUserMenu)
    fun toCommentsPage() =  set(toCommentsPage)
    fun toRegisterPage() =  set(toRegisterPage)

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

     val userInfo = MutableLiveData<String>()  //要查询的用户账号或者昵称
     fun toQueryFriend() {
        set(toQueryFriend)
     }

    fun toDeleteFriend(friendId: Int) {
         _friendId = friendId
        set(toDeleteFriend)
    }

    fun toDeleteLike(dynamicId: Int) {
        _dynamicId = dynamicId
        // dynamicId 和 userId
        set(toDeleteLike)
    }

    fun toDeleteLikeBy(likeId:Int) {
        _likeId = likeId
        // likeId
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
    fun toAddDynamic() {
        LogUtil.e("变化了")
        toAddDynamic.value = true
    }

    fun toAddFriend(friendId: Int) {
        _friendId = friendId
        set(toAddFriend)
    }

    fun toAddComments(friendId: Int,dynamicId: Int) {
        _friendId = friendId
        _dynamicId = dynamicId
        // dynamicId 和 friendId 以及 userId
        set(toAddComments)
    }

    fun toAddLike(friendId:Int){
        _friendId = friendId
        set(toAddLike)
    }

    fun toAddChat() {
        set(toAddChat)
    }

    fun toAddHomeVideoPage() {
        page.value?.inc()
        LogUtil.e(page.value.toString()+"wode")
    }


    fun attachLoading(state:MutableLiveData<Boolean>){
        loading.observeForever{
            state.value = it
        }
    }

    fun attachClose(state:MutableLiveData<Boolean>){
        close.observeForever{
            state.value = it
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) { callbacks.add(callback) }
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) { callbacks.remove(callback) }
    fun notifyValueChanged(fieldId:Int){ callbacks.notifyCallbacks(this,fieldId,null) }
    fun notifyChangedAll(){ callbacks.notifyCallbacks(this,0,null) }

}