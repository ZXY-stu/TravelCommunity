package com.bignerdranch.travelcommunity.data.repository

import com.bignerdranch.travelcommunity.data.db.daos.PersonDynamicDao
import com.bignerdranch.travelcommunity.data.db.entity.PersonDynamic
import com.bignerdranch.travelcommunity.data.network.Network

class PersonDynamicRepository private constructor(private val personDynamicDao: PersonDynamicDao,private  val network: Network){

    fun getPersonDynamics() = personDynamicDao.getPersonDynamics()
    fun getUserId(personDynamicId:String) = personDynamicDao.getUserId(personDynamicId)
    fun getDynamicId(userId:String) = personDynamicDao.getDynamicId(userId)
    suspend fun insertPersonDynamic(personDynamic: PersonDynamic) {
        personDynamicDao.insertPersonDynamic(personDynamic)
    }
    suspend fun insertAll(personDynamics:List<PersonDynamic>){
         personDynamicDao.insertAll(personDynamics)
    }
    suspend fun deletePersonDynamic(dynamicId:String){
        personDynamicDao.deletePersonDynamic(dynamicId)
    }

    companion object{
        @Volatile private  var instance:PersonDynamicRepository? = null
        fun getInstance(personDynamicDao: PersonDynamicDao,network: Network):PersonDynamicRepository{
            return instance?: synchronized(this){
                instance?: PersonDynamicRepository(personDynamicDao,network)
                    .also { instance = it }
            }
        }
    }
}