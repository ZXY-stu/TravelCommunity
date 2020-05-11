package com.bignerdranch.travelcommunity.ui.dynamic.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.tclib.data.repository.PersonDynamicRepository

/**
 * @author zhongxinyu
 * @date 2020/4/3
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

class PersonDynamicViewModelFactory(private val personDynamicRepository: PersonDynamicRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PersonDynamicViewModel(
            personDynamicRepository
        ) as T
    }
}