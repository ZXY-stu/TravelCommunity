package com.bignerdranch.travelcommunity.adapters

import androidx.core.net.toUri
import androidx.databinding.InverseMethod
import androidx.room.TypeConverter
import com.bignerdranch.tclib.LogUtil.eee
import java.util.*

/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

object Coverters {

    @InverseMethod("fromStringToInt")
    @JvmStatic fun fromIntToString(value:Int?):String? {
        return value?.toString()?:""
    }

    @JvmStatic fun fromStringToInt(value:String?):Int? { return value?.toInt()?:-1}


    @JvmStatic fun fromDateToString(date: Date?): String? {
        return date.toString()
    }

    @JvmStatic fun getFristUrlfromString(imageUrls:String):String{
        if(imageUrls.length>3 && (imageUrls.contains('[') || imageUrls.contains(','))) {
            val imageUrl = imageUrls.substring(1, imageUrls.lastIndex)
            val img = imageUrl.split(",".toRegex(), 0)[0]
          //  eee("img"+imageUrls)
            return "http://lyndon.fun:81/"+img.substring(1, img.lastIndex)
        }

       return ""+imageUrls
    }

   @JvmStatic fun transferData(data:String) = "共有${data}条评论"

    @JvmStatic fun getUrlList(imageUrls: String):List<String>{
            val subStr = imageUrls.substring(1,imageUrls.lastIndex)
            val imageUrl = subStr.split(",".toRegex(),0)
              return imageUrl.map {
                  "http://lyndon.fun:81/" +  it.substring(1,it.lastIndex)
            }
    }

    @JvmStatic fun getVideoUrl(videoUrl:String) = "http://lyndon.fun:81/" + videoUrl

    @JvmStatic fun fromStringToUri(uri:String?) = uri?.toUri()?:"".toUri()

}