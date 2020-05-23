package com.bignerdranch.travelcommunity.adapters

import android.annotation.TargetApi
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.os.Build
import android.provider.MediaStore
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseMethod
import androidx.room.TypeConverter
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.tclib.data.db.entity.PersonDynamic
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.FutureTask

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
        if(imageUrls!=null && imageUrls.length>3 && (imageUrls.contains('[') || imageUrls.contains(','))) {
            val imageUrl = imageUrls.substring(1, imageUrls.lastIndex)
            val img = imageUrl.split(",".toRegex(), 0)[0]
          //  eee("img"+imageUrls)
            return "http://lyndon.fun:81/"+img.substring(1, img.lastIndex)
        }

       return ""+imageUrls
    }

   @JvmStatic fun transferData(data:String) = "共有${data}条评论"

    @JvmStatic fun getUrlList(imageUrls: String):List<String>?{
        if(imageUrls.length>3 && (imageUrls.contains('[') || imageUrls.contains(','))) {
            val subStr = imageUrls.substring(1, imageUrls.lastIndex)
            val imageUrl = subStr.split(",".toRegex(), 0)
            return imageUrl.map {
                "http://lyndon.fun:81/" + it.substring(1, it.lastIndex)
            }
        }
        return null
    }

    @JvmStatic fun getImageUrl(imageUrl:String?) = imageUrl?.let {
     if(it.length>3)   "http://lyndon.fun:81/" + imageUrl else ""
    }?:""

    @JvmStatic fun getVideoUrl(videoUrl:String?) = videoUrl?.let {
      if(it.length>3 && !it.contains("null"))    "http://lyndon.fun:81/" + videoUrl   else ""
    }?:""

    @JvmStatic fun fromStringToUri(uri:String?) = uri?.toUri()?:"".toUri()


    @JvmStatic
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    //获取视频缩略图
    fun createVideoThumbnail(videoUrl: String, width: Int, height: Int): Bitmap? {
        var bitmap: Bitmap? = null

        val retriever = MediaMetadataRetriever()
        val kind = MediaStore.Video.Thumbnails.MINI_KIND
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(videoUrl, HashMap())
            } else {
                retriever.setDataSource(videoUrl)
            }
            bitmap = retriever.frameAtTime
        } catch (ex: java.lang.IllegalArgumentException) { // Assume this is a corrupt video file
            ex.printStackTrace()
        } catch (ex: RuntimeException) { // Assume this is a corrupt video file.
            ex.printStackTrace()
        } finally {
            try {
                retriever.release()
            } catch (ex: RuntimeException) { // Ignore failures while cleaning up.
                ex.printStackTrace()
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(
                bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT
            )
        }
     eee("bitmapsss$bitmap")
        return bitmap
    }

}