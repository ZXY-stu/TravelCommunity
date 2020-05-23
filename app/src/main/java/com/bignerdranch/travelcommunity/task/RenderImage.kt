package com.bignerdranch.travelcommunity.task

import android.content.Context
import android.content.IntentFilter
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.net.ConnectivityManager
import android.os.Build
import android.provider.MediaStore
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.LogUtil.eeee
import com.bignerdranch.travelcommunity.base.BaseViewModel.Companion.recycle
import com.bignerdranch.travelcommunity.base.NetworkChangeListener
import com.bignerdranch.travelcommunity.base.NetworkConnectChangedReceiver
import java.util.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorCompletionService
import java.util.concurrent.ExecutorService

/**
 * @author zhongxinyu
 * @date 2020/5/22
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class RenderImage(val execute:ExecutorService,val activity: Context){

    var width = 300
    var height = 500


    fun getRecycle() = recycle

    private   var progress = 0

    private lateinit var infoList:List<String>
    private var shutDown = false
    private var haveNetWork = true
    private val lock = Any()

    private  var networkChangeListener: NetworkChangeListener? = null
    private  var networkConnectChangedReceiver: NetworkConnectChangedReceiver? = null
    private var onItemCompletedListener:OnItemCompletedListener? = null

    fun setNetWorkChanged(networkChangeListener: NetworkChangeListener){
        this.networkChangeListener = networkChangeListener
    }

   init{
       networkConnectChangedReceiver = NetworkConnectChangedReceiver().setNetworkChangeListener(
           object :NetworkChangeListener{
               override fun onConnected() {
                   if(!haveNetWork) {
                       execute.submit {
                           preload()
                       }
                       haveNetWork = true
                   }
                   shutDown = false
                   networkChangeListener?.onConnected()
               }

               override fun onDisconnected() {
                       shutDown = true
                   haveNetWork = false
                   eeee("没有网络连接")
                   networkChangeListener?.onDisconnected()
               }

               override fun onNetWorkChanged(type: Int) {
                   networkChangeListener?.onNetWorkChanged(type)
               }
           }

       )

       val filter = IntentFilter(
           ConnectivityManager.CONNECTIVITY_ACTION
       )
       activity.registerReceiver(networkConnectChangedReceiver,filter)
   }




    fun setListener(onItemCompletedListener:OnItemCompletedListener):RenderImage{
        this.onItemCompletedListener = onItemCompletedListener
        return this
    }



    fun setList(list:List<String>){
        infoList = list
    }

    interface OnItemCompletedListener{
        fun itemCompleted(url:String,bitmap: Bitmap)
    }

    private val completionService =  ExecutorCompletionService<Bitmap>(execute)

    fun setSize(width: Int,height: Int):RenderImage{
        this.width = width
        this.height = height
        return this
    }


     fun preload(){
       if(shutDown){
           shutDown = false
           return
       }
         eeee("加载... "+progress)
         eeee("加载... "+infoList)
         for (i:Int in progress..infoList.size)
            completionService.submit {
                   createVideoThumbnail(infoList[i],width,height)
            }
        try {
           for (i:Int in progress..infoList.size){
               eeee("加载... "+infoList[i])
                val take = completionService.take()

                val bitmap = take.get()

               if(shutDown){
                   eeee("关闭")
                   shutDown = false
                   return
               }
                eeee("bitmap $bitmap url ${infoList[i]} position $i")
               if(bitmap!=null) {
                   recycle[infoList[i]] = bitmap
                   progress = i
                   onItemCompletedListener?.itemCompleted(infoList[i], bitmap)
               }
            }
        } catch (interrupt: InterruptedException) {
            Thread.currentThread().interrupt()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }
    }

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
        LogUtil.eee("bitmapsss$bitmap")
        return bitmap

    }
}
