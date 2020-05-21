package com.bignerdranch.travelcommunity.util

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * @author zhongxinyu
 * @date 2020/5/21
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
object GlideUtil {

   fun preLoadImageByUrl(context: Context, url: String){
          Glide.with(context)
              .load(url)
              .preload()
   }

    fun preLoadImageByUri(context: Context,
                        uri: Uri){
        Glide.with(context)
            .load(uri)
            .preload()
    }


    fun loadImageByUrl(
        context: Context,
        url:String,
        imageView: ImageView,
        option:RequestOptions){
          Glide.with(context)
              .load(url)
              .apply(option)
              .into(imageView)
    }

     fun loadImageByUri(
        context: Context,
        uri:Uri,
        imageView: ImageView,
        option:RequestOptions){
        Glide.with(context)
            .load(uri)
            .apply(option)
            .into(imageView)
    }


}