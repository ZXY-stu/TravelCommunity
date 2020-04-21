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
            val a = userRepository.toUserLogin("" + account.value, "" + password.value)
            LogUtil.e("${a.value?.data}")
            a
        }
    }

    val user = Transformations.map(userLogin) {
       check(it)
    }


    private val userRegister = Transformations.switchMap(toRegister) {
        runBlocking {
            val account = account.value!!
            val password = password.value!!
            userRepository.toUserRegister(account, password)
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

                LogUtil.e("失败，请重试"+response.errorCode)
            }
        }
        return data
    }


        init {

            toLogout.observeForever {
                localUser.value?.let {
                    launch {
                        userRepository.toUserLogout(it)  //注销用户
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
                userRepository.toInsertUserLocal(user)
            }
        }


        private fun launch(block: suspend () -> Unit) = viewModelScope.launch {
            try {
                block()
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }

}



