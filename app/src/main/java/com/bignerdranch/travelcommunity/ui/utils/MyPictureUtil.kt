package com.bignerdranch.travelcommunity.ui.utils

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.FileProvider
import okhttp3.RequestBody
import java.io.File


/**
 * @author zhongxinyu
 * @date 2020/4/23
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

 lateinit var  imageUrl: Uri
 lateinit var outputImage: File
 lateinit var uploadRequestMap: Map<String, RequestBody>

 fun  takePhoto(context: Context,activity: Activity){   //拍照
    outputImage = File(context.externalCacheDir, "out.jpg")
    if (outputImage.exists()) outputImage.delete()
    outputImage.createNewFile()
    imageUrl = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        FileProvider.getUriForFile(context, "com.bignerdranch.travelcommunity.ui.HomePageActivity",
            outputImage
        )
    } else {
        Uri.fromFile(outputImage)
    }
    val intent = Intent("android.media.action.IMAGE_CAPTURE")
    intent.putExtra(MediaStore.EXTRA_OUTPUT,
        imageUrl
    )
    activity.startActivityForResult(intent, com.bignerdranch.travelcommunity.ui.dynamic.takePhoto)
}

 fun open(activity: Activity){   //打开相册
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    intent.type = "image/*"
    activity.startActivityForResult(intent,2)
 }


 fun getBitMapFromUri(uri:Uri,context: Context) = context.contentResolver.openFileDescriptor(uri,"r")
    ?.use {
        BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
    }


private  fun rotateIfRequired(bitmap: Bitmap): Bitmap {    //设置旋转
    val exif = ExifInterface(outputImage.path)
    val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
    return when(orientation){
        ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitMap(
            bitmap,
            90
        )
        ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitMap(
            bitmap,
            180
        )
        ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitMap(
            bitmap,
            270
        )
        else -> bitmap
    }
}

private fun rotateBitMap(bitmap: Bitmap, degree: Int): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(degree.toFloat())
    val rotatedBitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.width,bitmap.height,matrix,true)
    bitmap.recycle()
    return  rotatedBitmap
}

fun getUriFromDrawableRes(context:Context,id:Int):Uri {
    val resources = context.resources
    val path = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+
            resources.getResourcePackageName(id) + "/"+
            resources.getResourceTypeName(id) + "/"+
            resources.getResourceEntryName(id);
    return Uri.parse(path);
}


        /**
         *
         * 获取视频文件截图
         *
         *
         *
         * @param path 视频文件的路径
         *
         * @return Bitmap 返回获取的Bitmap
         */
fun getVideoThumb(path: String?): Bitmap? {
    val media = MediaMetadataRetriever()
    media.setDataSource(path)
    return media.frameAtTime
}

/**
 * 服务器返回url，通过url去获取视频的第一帧
 * Android 原生给我们提供了一个MediaMetadataRetriever类
 * 提供了获取url视频第一帧的方法,返回Bitmap对象
 *
 * @param videoUrl
 * @return
 */
fun getNetVideoBitmap(videoUrl: String?): Bitmap? {
    var bitmap: Bitmap? = null
    val retriever = MediaMetadataRetriever()
    try { //根据url获取缩略图
        retriever.setDataSource(videoUrl, HashMap())
        //获得第一帧图片
        bitmap = retriever.frameAtTime
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    } finally {
        retriever.release()
    }
    return bitmap
}