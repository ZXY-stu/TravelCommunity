package com.bignerdranch.tclib.data.network

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


/**
 * @author zhongxinyu
 * @date 2020/4/20
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
object Util {
    fun filesToMultipartBody(files: List<File>): MultipartBody? {
        val builder = MultipartBody.Builder()
        for (file in files) { // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/png"), file)
            builder.addFormDataPart("file", file.getName(), requestBody)
        }
        builder.setType(MultipartBody.FORM)
        return builder.build()
    }


    fun filesToMultipartBodyParts(files: List<File>): List<MultipartBody.Part>? {
        val parts: MutableList<MultipartBody.Part> = ArrayList(files.size)
        for (file in files) { // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            val requestBody =
                RequestBody.create(MediaType.parse("image/png"), file)
            val part =
                MultipartBody.Part.createFormData("file", file.name, requestBody)
            parts.add(part)
        }
        return parts
    }


}