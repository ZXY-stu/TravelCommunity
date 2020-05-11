package com.bignerdranch.travelcommunity.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.tclib.data.repository.UserRepository


/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

class UserViewModelFactory(private val userRepository: UserRepository):
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(
            userRepository
        ) as T
    }
}