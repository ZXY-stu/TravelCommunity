package com.bignerdranch.travelcommunity.ui.user

import android.annotation.SuppressLint
import androidx.lifecycle.*
import com.bignerdranch.travelcommunity.data.db.entity.User
import com.bignerdranch.travelcommunity.data.repository.UserRepository
import com.bignerdranch.travelcommunity.base.BaseViewModel
import com.bignerdranch.travelcommunity.base.NetworkConnectChangedReceiver
import com.bignerdranch.travelcommunity.base.NetworkConnectChangedReceiver.Companion.haveNetwork
import com.bignerdranch.travelcommunity.data.ErrorCode
import com.bignerdranch.travelcommunity.data.network.model.ApiResponse
import com.bignerdranch.travelcommunity.util.LogUtil
import com.bignerdranch.travelcommunity.util.SUCCESS
import com.bignerdranch.travelcommunity.util.ToastUtil
import kotlinx.coroutines.*
import java.util.*


/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/



//处理数据层 和 UI层的交互
class UserViewModel internal constructor(private val userRepository: UserRepository) :
    BaseViewModel<UserRepository>(userRepository) {

    val account = MutableLiveData<String>()   //账号
    val password = MutableLiveData<String>()   //密码
    val code = MutableLiveData<String>()   //验证码
    private var userLogin = Transformations.switchMap(toLogin) {
        runBlocking {
            userRepository.userLogin(account.value + "", "" + password.value)
        }
    }

    val user = Transformations.map(userLogin) {
          check(it)
    }


    private val userRegister = Transformations.switchMap(toRegister) {
        runBlocking {
            val account = account.value!!
            val password = password.value!!
            userRepository.userRegister(account, password)
        }
    }

    val register = Transformations.map(userRegister) {
         check(it)
    }


    private fun <T> check(response: ApiResponse<T>): T? {
       var data:T? = null
        when (response.errorCode) {
            SUCCESS -> {
                loading.value = false
               data = response.data
            }
            else -> {
                runBlocking(Dispatchers.Main) {
                    ToastUtil.show("失败，请重试！")
                }
            }
        }
        return data
    }


        init {

            toLogout.observeForever {
                localUser.value?.let {
                    launch {
                        userRepository.userLogout(it)  //注销用户
                    }
                }
            }
        }


        fun deleteUser(user: User) {
            launch {

            }
        }



        fun insertUser(user: User) {
            launch {
                userRepository.insertUser(user)
            }
        }

        fun insert() {
            launch {
                userRepository.insertUser(
                    User(
                        2,
                        "你长的真好看呀",
                        "小宇宙",
                        20,
                        "0219",
                        "https://upload.wikimedia.org/wikipedia/commons/8/82/Hibiscus_rosa-sinensis_flower_2.JPG",
                        "https://upload.wikimedia.org/wikipedia/commons/8/82/Hibiscus_rosa-sinensis_flower_2.JPG",
                        "13245679",
                        "123456",
                        "湖南醴陵",
                        "430253156245",
                        "m",
                        "终于等到你" +
                                "我喜欢追求自由，与我一起旅行吧！",
                        "终于等到你，你这么好看还可以关注我！" +
                                "我喜欢追求自由，与我一起旅行吧 ",
                        "100w",
                        "10w",
                        "123",
                        1,
                        Date(System.currentTimeMillis()),
                        0,
                        0
                    )
                )

            }
        }

        fun getCode() {   //获取验证码

        }

        private fun launch(block: suspend () -> Unit) = viewModelScope.launch {
            try {
                block()
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }

}



