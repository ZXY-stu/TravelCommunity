package com.bignerdranch.travelcommunity.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.travelcommunity.data.repository.UserRepository

class UserLoginViewModelFactory(private val userRepository: UserRepository):
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserLoginViewModel(userRepository) as T
    }
}