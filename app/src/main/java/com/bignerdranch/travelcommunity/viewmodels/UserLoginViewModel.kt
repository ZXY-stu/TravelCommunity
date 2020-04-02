package com.bignerdranch.travelcommunity.viewmodels

import androidx.lifecycle.*
import com.bignerdranch.travelcommunity.data.db.entity.User
import com.bignerdranch.travelcommunity.data.repository.UserRepository
import kotlinx.coroutines.launch

//处理数据层 和 UI层的交互
class UserLoginViewModel internal constructor(val userRepository: UserRepository) :BaseViewModel(){

    var user = MutableLiveData<User>()
     var  use: User? = null

    var isLogin = MutableLiveData<Boolean>()


   fun login(account: String, password: String) {
       launch {
           userRepository.loginToNetwork(account, password)
       }
   }

    fun logout(account: String) {
        launch {
            userRepository.logoutFromNetwork(account)
        }
    }
    fun register(user: User) {
        launch {
            userRepository.registerToNetwork(user)
        }
    }

    fun  insertUser(user: User) {
        launch {
            userRepository.insertUserToRoom(user)
        }
    }
     fun  deleteLocalUser(user: User){
         launch {
             userRepository.deleteUserFromRoom(user)
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




