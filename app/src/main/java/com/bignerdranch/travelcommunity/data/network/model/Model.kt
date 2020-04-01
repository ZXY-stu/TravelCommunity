package com.bignerdranch.travelcommunity.data.network.model

object Model {
    data class UserLogin(val account:String, val password:String, val backInfo:String)
    data class BackInfo(val describe:String,val backInfo: String)
    data class UserRegister(
        val account: String,
        val password: String,
        val sex:Char,
        val career:String,
        val describe:String,
        val hobby:String
    )
    data class UserInfo(
        val nickname:String,
        val age:Int,          //年龄
        val birthday:String,  //出生日期
        val headPortraitUrl:String,  //头像图片地址
        val sex:String,        //性别
        val hobby:String,    //兴趣爱好
        val introduce:String,
        val backInfo:String
    )

   fun  <T,R> toEncrypt(data:R):T{
       return data as T
   }

    fun <T,R> toDecode(data:T):R{
        return data as R
    }
}