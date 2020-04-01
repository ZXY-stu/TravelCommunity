package com.bignerdranch.travelcommunity.adapters

import androidx.databinding.InverseMethod
import com.bignerdranch.travelcommunity.data.model.LoginInfo

object Coverters {

    @InverseMethod("fromStringToLogin")
    @JvmStatic fun fromLoginToString(loginInfo: LoginInfo):String{
        return loginInfo.account
    }

    @JvmStatic fun fromStringToLogin(value:String):LoginInfo{
        return LoginInfo(value,"")
    }

}