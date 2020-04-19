package com.bignerdranch.travelcommunity.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.travelcommunity.data.repository.BaseRepository

/**
 * @author zhongxinyu
 * @date 2020/4/10
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class BaseViewModelFactory(private val baseRepository: BaseRepository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
         return BaseViewModel(baseRepository) as T
    }
}