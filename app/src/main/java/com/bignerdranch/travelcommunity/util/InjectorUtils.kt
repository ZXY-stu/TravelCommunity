package com.bignerdranch.travelcommunity.util

import android.content.Context
import com.bignerdranch.tclib.data.db.TCDataBases
import com.bignerdranch.tclib.data.repository.BaseRepository
import com.bignerdranch.tclib.data.repository.PersonDynamicRepository
import com.bignerdranch.tclib.data.repository.UserRepository
import com.bignerdranch.travelcommunity.base.BaseViewModelFactory
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModelFactory
import com.bignerdranch.travelcommunity.ui.user.UserViewModelFactory
/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

object InjectorUtils{


   private fun getUserRepository(context: Context): UserRepository {
       return UserRepository.getInstance(TCDataBases.getInstance(context).userDao(),context)
    }

    private fun getBaseRepository(context: Context): BaseRepository {
        return BaseRepository.getInstance(TCDataBases.getInstance(context).userDao(),context)
    }

    private fun getPersonDynamicRepository(context: Context): PersonDynamicRepository {
        val instance = TCDataBases.getInstance(context)
        return  PersonDynamicRepository.getInstance(
            instance.personDynamicDao(),
            instance.commentsMsgDao(),
            instance.likeDao(),
            instance.userDao(),
            context
        )
    }

    fun  userViewModelFactory(context: Context): UserViewModelFactory {
        return UserViewModelFactory(getUserRepository(context))
    }

    fun personDynamicViewModelFactory(context: Context): PersonDynamicViewModelFactory {
        return PersonDynamicViewModelFactory(
            getPersonDynamicRepository(context)
        )
    }

    fun baseViewModelFactory(context: Context):BaseViewModelFactory{
        return BaseViewModelFactory(getBaseRepository(context))
    }


}