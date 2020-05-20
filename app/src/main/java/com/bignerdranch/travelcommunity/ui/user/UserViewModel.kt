package com.bignerdranch.travelcommunity.ui.user

import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.lifecycle.*
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.tclib.data.db.entity.User
import com.bignerdranch.tclib.data.network.model.ApiResponse
import com.bignerdranch.tclib.data.repository.UserRepository
import com.bignerdranch.travelcommunity.base.BaseViewModel
import com.bignerdranch.travelcommunity.util.FAILUER
import com.bignerdranch.travelcommunity.util.SERVER_OR_NETWORK_ERROR
import com.bignerdranch.travelcommunity.util.SUCCESS
import com.bignerdranch.travelcommunity.util.ToastUtil
import kotlinx.coroutines.*


/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

//处理数据层 和 UI层的交互
class UserViewModel internal constructor(private val userRepository: UserRepository) :
    BaseViewModel<UserRepository>(userRepository) {

     //用户登录
    private val waitUserLogin = executeRequest(toLogin) {
          userRepository.toUserLogin("" + account.value, "" + password.value)
    }

    private val userLoginResult = waitResponseResult(waitUserLogin){
        with(it){
         /*   val user = User(1,nickName,account,age,birthday, headPortraitUrl, backgroundImageUrl,
                phoneNumber, password, address, identifyNumber, sex, hobby, introduce,
                stat, lastLoginTime, isMember, privateModel, likeTotal, fansTotal, focusTotal)*/
      eee(""+it)
               val user = User(1,nickName,account,age,birthday, headPortraitUrl,
               phoneNumber, password, identifyNumber, sex, hobby, introduce,
               stat, lastLoginTime, isMember, privateModel)
            userRepository.toInsertUserLocal(user)
        }
    }

    //用户注册
    private val waitUserRegister = executeRequest(toRegister) {
            val account = account?.value
            val password = password?.value
            userRepository.toUserRegister(""+account, ""+password)
    }

    private val userRegisterResult =  waitResponseResult(waitUserRegister){
        with(it){
           /* val user = User(1,nickName,account,age,birthday, headPortraitUrl, backgroundImageUrl,
                phoneNumber, password, address, identifyNumber, sex, hobby, introduce,
                stat, lastLoginTime, isMember, privateModel, likeTotal, fansTotal, focusTotal)*/

            val user = User(1,nickName,account,age,birthday, headPortraitUrl,
                phoneNumber, password, identifyNumber, sex, hobby, introduce,
                stat, lastLoginTime, isMember, privateModel)
            userRepository.toInsertUserLocal(user)
        }
    }

    //用户注销
    private val waitUserLogout = executeRequest(toLogout){
        userRepository.toUserLogout(localUser.value!!)
    }

    private val  userLogoutResult = waitResponseResult(waitUserLogout){
        eee("userLogoutResult")
    }

    //搜索好友
    private val waitQueryFriend = executeRequest(toQueryFriendByUserInfo){
        userRepository.toQueryFriend(_userInfo)
    }

    private val queryFriendResult =  waitResponseResult(waitQueryFriend){
           userRepository.toInsertUserAllLocal(it)
    }

    //查询好友信息
    private val waitQueryFriendInfo = executeRequest(toQueryFriendById){
        userRepository.toQueryFriend(_friendId)
    }

    private val queryFriendInfoResult =  waitResponseResult(waitQueryFriendInfo){
        userRepository.toInsertUserLocal(it)
    }

    //查询好友关系表
    private val waitQueryFriendList = executeRequest(toQueryFriendList){
        userRepository.toQueryFriendList(getUserId())
    }

    private val queryFriendListResult = waitResponseResult(waitQueryFriendList){
        userRepository.toInsertRelationsLocal(it)
    }

    //添加或者关注朋友
    private val waitAddFriend  = executeRequest(toAddFriend){
        userRepository.toAddFriend(requestArgs)
    }

    private val addFriendResult = waitResponseResult(waitAddFriend){
        eee("addFriendResult")
    }

    //删除或者取消关注朋友
    private val waitDeleteFriend = executeRequest(toDeleteFriend){
        userRepository.toDeleteFriend(_friendId)
    }

    private val deleteFriendResult = waitResponseResult(waitDeleteFriend){
        eee("deleteFriendResult")
    }

    //更新用户基本信息
    // contentsArgs
    // name = backgroundImageUrl  用户背景图片
    // name = headPortraitUrl  用户头像
    // user实体类  用户其它文字信息
    private val waitUpdateUser = executeRequest(toUpdateUser){
        userRepository.toInsertUserLocal(editorUser!!)
        userRepository.toUpdateUserInfo(contentsArgs)
    }

    private val updateUserResult = waitResponseResult(waitUpdateUser){
        userRepository.toInsertUserLocal(it)
    }



    fun getWrapUser(){

    }

        init {



            deleteFriendResult.observeForever {
                executeResult(it,"取消关注")
            }

            addFriendResult.observeForever {
                executeResult(it,"关注")
            }

            queryFriendListResult.observeForever {
                executeResult(it,"")
            }

            queryFriendResult.observeForever {
                executeResult(it,"")
            }

            queryFriendInfoResult.observeForever {
                executeResult(it,"")
            }

            userRegisterResult.observeForever {
                executeResult(it,"")
            }

            userLoginResult.observeForever {
                executeResult(it,"")
            }

            userLogoutResult.observeForever {
                executeResult(it,"")
            }

            updateUserResult.observeForever {
                executeResult(it,"")
            }
        }

        fun insertUser(user: User) {
            launch {
                userRepository.toInsertUserLocal(user)
            }
        }



}



