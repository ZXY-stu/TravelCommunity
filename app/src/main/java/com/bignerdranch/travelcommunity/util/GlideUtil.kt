package com.bignerdranch.travelcommunity.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.Transformations
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.tclib.LogUtil.eeee
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.TCApplication
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.request.FutureTarget
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File
import javax.microedition.khronos.opengles.GL


/**
 * @author zhongxinyu
 * @date 2020/5/21
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
object GlideUtil {

    fun  getCircleImageOption(width:Int,height:Int) = RequestOptions().override(width,height).circleCrop()
    fun  getCircleImageOption() = RequestOptions().circleCrop()
    fun getImageOption(width: Int,height: Int) = RequestOptions().override(width, height)
    fun getImageOptionCenterCrop() = RequestOptions().dontAnimate().centerCrop()
    fun getImageOption() = RequestOptions().dontAnimate()
    fun getImageOption(cacheStrategy: DiskCacheStrategy,transformation:Transformation<Bitmap> ) =
         RequestOptions()
        .diskCacheStrategy(cacheStrategy)
             .transform(transformation)

  fun preLoadImagesByUrl(context: Context,urls:List<String>):GlideUtil{
      urls?.forEach {
          preLoadImageByUrl(context,it)
      }
      return this
  }

    fun preLoadImagesByUri(context: Context,urls:List<Uri>):GlideUtil{
        urls?.forEach {
            preLoadImageByUri(context,it)
        }
        return this
    }

   class LoadImageListener:RequestListener<Drawable>{
       override fun onLoadFailed(
           e: GlideException?,
           model: Any?,
           target: Target<Drawable>?,
           isFirstResource: Boolean
       ): Boolean {
           eeee("加载失败")
           return  true
       }

       override fun onResourceReady(
           resource: Drawable?,
           model: Any?,
           target: Target<Drawable>?,
           dataSource: DataSource?,
           isFirstResource: Boolean
       ): Boolean {
           eeee("加载成功")
           return true
       }
   }

   fun preLoadImageByUrl(context: Context,
                         url: String){

       eeee("preLoad  $url")
          Glide.with(context)
              .load(url)
              .listener(LoadImageListener())
              .preload()

   }


    fun preLoadImageByUri(context: Context,
                        uri: Uri){
        Glide.with(context)
            .load(uri)
            .listener(LoadImageListener())
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

    fun downloadImage() {
        Thread(Runnable {
            try {
                val url = "http://www.guolin.tech/book.png"
                val context: Context = TCApplication.context
                val target: FutureTarget<File> = Glide.with(context)
                    .asFile()
                    .load(url)
                    .submit()
                val imageFile: File = target.get()

                   runBlocking {
                      withContext(Dispatchers.Main) {
                          Runnable {
                              Toast.makeText(context, imageFile.getPath(), Toast.LENGTH_LONG)
                                  .show()
                          }
                      }
                   }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }).start()
    }

    fun glideWithListener(context: Context,
                          url: String,
                          onFailed:()->Boolean,
                          onReady:()->Boolean
                          , imageView: ImageView){
        Glide.with(context)
            .load(url)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                        onFailed()
                        return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                          onReady()
                          return false
                }
            })
            .into(imageView)
    }


    fun calculateInSampleSize(
        options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int
    ): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2
            while (halfHeight / inSampleSize > reqHeight
                && halfWidth / inSampleSize > reqWidth
            ) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }


    fun decodeSampledBitmapFromResource(
        res: Resources?, resId: Int,
        reqWidth: Int, reqHeight: Int
    ): Bitmap {
        val options: BitmapFactory.Options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)
    }

}


