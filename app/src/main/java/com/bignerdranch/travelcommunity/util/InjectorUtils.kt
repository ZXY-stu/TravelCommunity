package com.bignerdranch.travelcommunity.util

import android.content.Context
import com.bignerdranch.travelcommunity.data.db.TCDataBases
import com.bignerdranch.travelcommunity.data.network.Network
import com.bignerdranch.travelcommunity.data.repository.CommentsMsgRepository
import com.bignerdranch.travelcommunity.data.repository.PersonDynamicRepository
import com.bignerdranch.travelcommunity.data.repository.UserRepository
import com.bignerdranch.travelcommunity.viewmodels.UserLoginViewModel
import com.bignerdranch.travelcommunity.viewmodels.UserLoginViewModelFactory
/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

object InjectorUtils{
/*
    private fun getCommentsMsgRepository(context: Context):CommentsMsgRepository?{
         return CommentsMsgRepository.getInstance(TCDataBases.getInstance(context.applicationContext)
            .commentsMsgDao(),Network.getInstance())
         }
*/

   private fun getUserRepository(context: Context): UserRepository{
       return UserRepository.getInstance(TCDataBases.getInstance(context.applicationContext)
           .userDao(), Network.getInstance())
    }

    private fun getPersonDynamicRepository(context: Context): PersonDynamicRepository?{
        return  PersonDynamicRepository.getInstance(TCDataBases.getInstance(context.applicationContext)
            .personDynamicDao(), Network.getInstance())

    }

    fun  userLoginViewModelFactory(context: Context):UserLoginViewModelFactory{
        return  UserLoginViewModelFactory(getUserRepository(context))
    }





}