package com.bignerdranch.travelcommunity.ui.utils

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.tclib.utils.StringUtils
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.ui.dynamic.OPEN_ALBUM
import com.bignerdranch.travelcommunity.util.MediaFileUtil
import com.bignerdranch.travelcommunity.util.PathUtils
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.SelectionCreator
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.entity.CaptureStrategy
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import java.util.*
import kotlin.collections.HashMap

/**
 * @author zhongxinyu
 * @date 2020/4/23
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/


object Utils {

      //图片选择器
       fun build(fragment:Fragment,type:MimeType):SelectionCreator{
        return    Matisse.from(fragment)
                .choose(MimeType.ofAll())
            .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(fragment.getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
       }

       fun build(activity: Activity,type: MimeType):SelectionCreator{
           return Matisse.from(activity)

               .choose(MimeType.of(type))
               .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))

               .gridExpectedSize(activity.getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
       }

      fun build(activity: Activity):SelectionCreator{
          return Matisse.from(activity)
              .choose(MimeType.ofAll())
              .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
              .gridExpectedSize(activity.getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
      }

     fun  build(fragment:Fragment):SelectionCreator{
        return Matisse.from(fragment)
            .choose(MimeType.ofAll())
            .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
            .gridExpectedSize(fragment.getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
    }



     //转化请求Map
    fun  getUploadRequestMap(uris:List<Uri>?, context: Context, textContent:String):HashMap<String, RequestBody>{
        val requestMap = HashMap<String, RequestBody>()
        var i=0
         eee("uri" + uris)
        if(uris!=null) {
            for (uri in uris) {
                val path = PathUtils.getPath(context, uri)
                LogUtil.e(path)
                val file = File(path)
               val type = if(MediaFileUtil.isVideoFileType(path)) "video"
               else  "imgs"
                requestMap.put(type + "\";filename=\"" + file.name, toRequestBodyFile(file))
            }
        }
         requestMap["textContent"] = toRequestBodyText(textContent)
         requestMap["submitsTime"] = toRequestBodyText(StringUtils.getDateTime())
        return requestMap
    }

    //获取图片请求Map
    fun  getImageRequestMap(uris:List<Uri>?, context: Context):HashMap<String, RequestBody>{
        val requestMap = HashMap<String, RequestBody>()
        if(uris!=null) {
            for (uri in uris) {
                val file = File(PathUtils.getPath(context, uri))
                requestMap.put("imgs" + "\";filename=\"" + file.name, toRequestBodyFile(file))
            }
        }
        return requestMap
    }


    fun toRequestBodyText(value:String) = RequestBody.create(MediaType.parse("text/plain"),value)

    fun toRequestBodyFile(file: File) = RequestBody.create(MediaType.parse("multipart/form-data"),file)
}



fun SelectionCreator.openAlbum(requestCode:Int){
    countable(true)
        .maxSelectable(9) // 图片选择的最多数量
        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
        .captureStrategy(CaptureStrategy(true,"com.bignerdranch.travelcommunity.ui.HomePageActivity"))
        .capture(true)
        .thumbnailScale(0.85f) // 缩略图的比例
        .theme( R.style.Matisse_Zhihu)
        .imageEngine(GlideEngine()) // 使用的图片加载引擎
        .forResult(requestCode); // 设置作为标记的请求码
}





