package com.bignerdranch.travelcommunity.base

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bignerdranch.travelcommunity.data.ErrorCode
import com.bignerdranch.travelcommunity.data.repository.BaseRepository
import com.bignerdranch.travelcommunity.util.LogUtil

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

//可实现双向绑定的ViewModel
open class BaseViewModel<T:BaseRepository>(protected val baseRepository: T):ViewModel(),Observable{
    private val callbacks:PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    /*动作的触发标志*/
    val refreshTrigger = MutableLiveData<Boolean>()  //是否触发刷新
    val toLogin = MutableLiveData<Boolean>()  //进行登录
    protected val page = MutableLiveData<Int>()   //当前的页面
    val refreshing = MutableLiveData<Boolean>()    // 是否正在刷新
    val moreLoading = MutableLiveData<Boolean>()   //是否正在加载更多
    val hasMore = MutableLiveData<Boolean>()     //是否有更多数据
    val autoRefresh = MutableLiveData<Boolean>()   //是否自动刷新
    val errorCode = MutableLiveData<ErrorCode>()   //错误码
    val loading = MutableLiveData<Boolean>()    //正在加载..
    val focusFriend = MutableLiveData<Boolean>()   //关注好友
    val openUserMenu = MutableLiveData<Boolean>()  //用户页右侧菜单

    val close = MutableLiveData<Boolean>()   //关闭某个页面
    val navigationTo = MutableLiveData<Boolean>()  //进行页面跳转
    val _toRegisterPage = MutableLiveData<Boolean>(false)
    val toRegister = MutableLiveData<Boolean>()
    val backTrigger = MutableLiveData<Boolean>()
    val findPassword = MutableLiveData<Boolean>()
    /*
    * errorCode =
    * */
     val localUser = baseRepository._userDao.getUser()

    val isLogin = Transformations.switchMap(localUser){
        user ->   MutableLiveData<Boolean>(user != null)
    }

    val toLogout = MutableLiveData<Boolean>()

     fun  openMenu(){
         openUserMenu.value = true
     }

    fun back(){
        backTrigger.value = true
    }

    fun toLogin(){
          navigationTo.value = true

        //toLoginPage.value = true
    }

    fun toFocus(){
        focusFriend.value  = true
    }

    fun navigationTo(){
        navigationTo.value = true
    }

    fun forgetPassword(){
        findPassword.value = true
    }

    fun login(){
        loading.value = true
        toLogin.value = true
    }

    fun destroyThisPage(){
        close.value = false
        //LogUtil.e("close")
    }

    fun logout(){
        toLogout.value = true
    }

    fun register(){  //进行注册
        toRegister.value = true
    }

    fun toRegisterPage(){  //去注册页面
        _toRegisterPage.value = true
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







    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
       callbacks.add(callback)

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
       callbacks.remove(callback)
    }

    fun notifyValueChanged(fieldId:Int){
        callbacks.notifyCallbacks(this,fieldId,null)
    }

    fun notifyChangedAll(){
        callbacks.notifyCallbacks(this,0,null)
    }

    fun init(block:()->Unit){
        Thread{
            block()
        }.start()
    }

}