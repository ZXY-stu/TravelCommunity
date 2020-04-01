package com.bignerdranch.travelcommunity.viewmodels

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.bignerdranch.travelcommunity.BR
import com.bignerdranch.travelcommunity.data.db.entity.User
import com.bignerdranch.travelcommunity.data.model.LoginInfo
import com.bignerdranch.travelcommunity.data.repository.UserRepository
import com.bignerdranch.travelcommunity.util.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.lang.Exception

class UserLoginViewModel internal constructor(val userRepository: UserRepository) :BaseViewModel(){

    private var _loginInfo: LoginInfo = LoginInfo()


    private fun checkError(account:String, password:String):Boolean{
        return false
    }

    private fun checkLogin():Boolean?{
        return userRepository.getUser().map { it.phoneNumber.isEmpty() }.value
    }

    fun login():String{
          return ""
     }

    private fun toLogin(loginInfo: LoginInfo) = runBlocking{
        //与服务器通信

    }


}