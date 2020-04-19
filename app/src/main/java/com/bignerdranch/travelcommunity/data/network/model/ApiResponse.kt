package com.bignerdranch.travelcommunity.data.network.model

import androidx.annotation.Keep



@Keep
data class ApiResponse<T>(
    var data: T?,
    var errorCode: Int,    /*0成功 1失败*/
    var errorMsg: String
)




