package com.bignerdranch.travelcommunity.util

import android.content.Context
import com.bignerdranch.travelcommunity.data.db.TCDataBases
import com.bignerdranch.travelcommunity.data.repository.CommentsMsgRepository
import com.bignerdranch.travelcommunity.data.repository.PersonDynamicRepository
import com.bignerdranch.travelcommunity.data.repository.UserRepository

object InjectorUtils{

    private fun getCommentsMsgRepository(context: Context):CommentsMsgRepository?{
         return TCDataBases.getInstance(context.applicationContext)
             ?.commentsMsgDao()?.let {
             CommentsMsgRepository.getInstance(it)
         }
     }

   private fun getUserRepository(context: Context): UserRepository?{
        return TCDataBases.getInstance(context.applicationContext)
            ?.userDao()?.let {
                UserRepository.getInstance(it)
            }
    }

    private fun getPersonDynamicRepository(context: Context): PersonDynamicRepository?{
        return TCDataBases.getInstance(context.applicationContext)
            ?.personDynamicDao()?.let {
                PersonDynamicRepository.getInstance(it)
            }
    }



}