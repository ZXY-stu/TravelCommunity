package com.bignerdranch.travelcommunity.base

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bignerdranch.travelcommunity.data.ErrorCode
import com.bignerdranch.travelcommunity.data.db.entity.PersonDynamic
import com.bignerdranch.travelcommunity.data.db.entity.QueryDynamicArgs
import com.bignerdranch.travelcommunity.data.repository.BaseRepository
import com.bignerdranch.travelcommunity.util.LogUtil
import okhttp3.RequestBody

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

    /*动作的触发标志*/
    val refreshTrigger = MutableLiveData<Boolean>()  //是否触发刷新

    protected val page = MutableLiveData<Int>()   //当前的页面
    val refreshing = MutableLiveData<Boolean>()    // 是否正在刷新
    val moreLoading = MutableLiveData<Boolean>()   //是否正在加载更多
    val hasMore = MutableLiveData<Boolean>()     //是否有更多数据
    val autoRefresh = MutableLiveData<Boolean>()   //是否自动刷新

    val loading = MutableLiveData<Boolean>()    //正在加载..

    val close = MutableLiveData<Boolean>()   //关闭某个页面
    val navigationTo = MutableLiveData<Boolean>()  //进行页面跳转

    val personDynamics = MutableLiveData<List<PersonDynamic>>()
    val textContent = MutableLiveData<String>()

    val likeArgs = HashMap<String,String>()
    val commentsMsg = MutableLiveData<String>()
    val commentsArgs = HashMap<String, String>()
    val permissionArgs = HashMap<String, String>()
    val contentsArgs = HashMap<String, RequestBody>()
    val queryDynamicArgs = MutableLiveData<QueryDynamicArgs>()

    val localUser = baseRepository._userDao.getUser()
    val isLogin = Transformations.switchMap(localUser){
            user ->   MutableLiveData<Boolean>(user != null)
    }

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
    val toAddDynamic =    get()         //发表动态
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
    fun toChatPage() =      set(toChatPage)

    fun toQueryDynamics() = set(toQueryDynamic)
    fun toQueryLikes()  =   set(toQueryLikes)
    fun toQueryComments() = set(toQueryComments)
    fun toQueryChat()  =    set(toQueryChat)
    fun toQueryFriend() =   set(toQueryFriend)

    fun toDeleteFriend() =  set(toDeleteFriend)
    fun toDeleteLike()  =   set(toDeleteLike)
    fun toDeleteDynamic() = set(toDeleteDynamic)
    fun toDeleteComment() = set(toDeleteComment)
    fun toDeleteChar()   =  set(toDeleteChat)

    fun toAddDynamic() =    set(toAddDynamic)
    fun toAddFriend() =     set(toAddFriend)
    fun toAddComments() =   set(toAddComments)
    fun toAddLike() =       set(toAddLike)
    fun toAddChat() =       set(toAddChat)


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