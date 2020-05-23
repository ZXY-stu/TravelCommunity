package com.bignerdranch.travelcommunity.adapters

import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.tclib.LogUtil.eeee
import com.bignerdranch.tclib.data.db.entity.CommentsMsg
import com.bignerdranch.tclib.data.db.entity.PersonDynamic
import com.bignerdranch.travelcommunity.util.HtmlTextStyleUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

object UserBindingAdapter{

    @BindingAdapter("saveLoginInfo")
    @JvmStatic  fun setInfo(info:EditText,value:String){
        LogUtil.w(value+"set")
        info.setText(value)
    }

    @InverseBindingAdapter(attribute = "saveLoginInfo")
    @JvmStatic fun getInfo(info: EditText):String{
        LogUtil.w(info.text.toString()+"get")
        return info.text.toString()
    }

    @BindingAdapter("saveLoginInfoAttrChanged")
    @JvmStatic fun setListener(info:EditText,listener: InverseBindingListener){
        info.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            val view = v as TextView
            if(hasFocus) {
                view.text = ""
            LogUtil.w(view.text.toString())
            }
            else {
                LogUtil.w(view.text.toString())
                listener?.onChange()
            }
        }
    }

    @BindingAdapter("onFocusClearAndSave")
    @JvmStatic fun onFocusClearAndSave(view : EditText,listener:View.OnFocusChangeListener?){
        view.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            val textView = v as TextView
            if(hasFocus){
                textView.setTag(R.id.preValue,textView.text.toString())
                textView.text = ""
                LogUtil.w("isFocusAndClear"+textView.id+hasFocus)
                println("")
            }else{
                if(textView.text.isEmpty())
                textView.setText(textView.getTag(R.id.preValue).toString())
                LogUtil.w("isUnFocus"+textView.text)
                println("")
                listener?.onFocusChange(v,hasFocus)
            }
        }
    }

    @BindingAdapter("isOnFocusClearAndSave")
    @JvmStatic fun  EditText.isOnFocusClearAndSave(enable:Boolean){
        if(enable) onFocusClearAndSave(this,null)
        else this.onFocusChangeListener = null
    }


    @BindingAdapter("imageFromUrl")
    @JvmStatic fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()) {
           val option = RequestOptions()
               .placeholder(R.drawable.abc)
               .dontAnimate()

            Glide.with(view.context)
                .load(imageUrl)
                .apply(option)
                .into(view)
            eee("imageUrl"+imageUrl)
           // Log.w("MainAc",imageUrl)
            //LogUtil.e("${view.javaClass} + $imageUrl")
        }
    }


    @BindingAdapter("imageFromUrlWhen")
    @JvmStatic fun imageFromUrlWhen(view: ImageView, personDynamic: PersonDynamic) {

        val imageUrl = Coverters.getFristUrlfromString(""+personDynamic.imageUrls)
        val videoUrl = Coverters.getVideoUrl(""+personDynamic.videoUrl)

       if(videoUrl.length<3) {
           val option = RequestOptions()
               .placeholder(R.drawable.abc)
               .dontAnimate()

           Glide.with(view.context)
               .load(imageUrl)
               .apply(option)
               .into(view)
           eeee("imageUrl" + videoUrl)
       }

        eeee("imageUrl" + imageUrl)
        eeee("video" + videoUrl)
            // Log.w("MainAc",imageUrl)
            //LogUtil.e("${view.javaClass} + $imageUrl")

    }


    @BindingAdapter("imageFromUrlCircle")
    @JvmStatic fun imageFromUrlCircle(view: ImageView, imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()) {
            val option = RequestOptions()
                .placeholder(R.drawable.abc)
                .circleCrop()
                .dontAnimate()


            Glide.with(view.context)
                .load(imageUrl)
                .apply(option)
                .into(view)
            eee("imageUrl"+imageUrl)
            // Log.w("MainAc",imageUrl)
            //LogUtil.e("${view.javaClass} + $imageUrl")
        }
    }



    @JvmStatic
    @BindingAdapter("videoThumbnail")
    fun videoThumbnail(view: ImageView, personDynamic: PersonDynamic){
        val imageUrl = Coverters.getFristUrlfromString(""+personDynamic.imageUrls)
        val videoUrl = Coverters.getVideoUrl(personDynamic.videoUrl)
        if(imageUrl.length>3 && videoUrl.length<3){
            bindImageFromUrl(view, imageUrl)
        }else{
           // FutureTaskServer.renderPageAsync(videoUrl,view)
        }
    }


    @BindingAdapter("imageFromUri")
    @JvmStatic fun bindImageUri(view: ImageView, imageUrl: Uri?) {

        val option = RequestOptions()
            .placeholder(R.drawable.abc)
            .dontAnimate()

            Glide.with(view.context)
                .load(imageUrl)
                .apply(option)
                .into(view)
            eee("imageUrl"+imageUrl)
            // Log.w("MainAc",imageUrl)
            //LogUtil.e("${view.javaClass} + $imageUrl")

    }

    @BindingAdapter("imageUrl")
    @JvmStatic fun bindImageUrl(view: ImageView,imageUrl: String){
        Glide.with(view.context).load(String).into(view)
    }


    @BindingAdapter("onDownPullRefresh")
    @JvmStatic fun bindRefresh(view:SwipeRefreshLayout,block:()->Unit){
             view.setOnRefreshListener {
                 block()
                 view.isRefreshing = false
             }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @BindingAdapter("onUpPullRefresh")
    @JvmStatic fun bindOnRefresh(view:RecyclerView,block:()->Unit){


    }

    @BindingAdapter("calcSex")
    @JvmStatic fun calcSex(view: ImageView,sex:String?){
        when(sex?.contains("w")){
            false -> view.setImageResource(R.drawable.man)
            true -> view.setImageResource(R.drawable.woman)
        }
    }

    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("content")
    fun setText(view: TextView, message:CommentsMsg) {
        val style = HtmlTextStyleUtil.getInstance()
            .setFontSize("8")
            .setText(message.msg)
            .setSubText(message.times)

        if(message.friendNickName.isNotEmpty()){
           view.text =  style.setNickName(message.friendNickName).buildStyle(HtmlTextStyleUtil.REPLAY_WITH_TIME)
        }else{
            view.text = style.buildStyle(HtmlTextStyleUtil.NOREPLAY_WITH_TIME)
        }
    }


      @JvmStatic
      @BindingAdapter("checkError")
      fun checkError(view:View,toCheck:Boolean){
          if(toCheck){

          }
      }


    @JvmStatic
    @BindingAdapter("bottomAction")
    fun bottomAction(view:ImageView,isUser:Boolean){
        if(isUser) view.setImageResource(R.drawable.delete_1)
        else view.setImageResource(R.drawable.share_1)
    }

    @JvmStatic
    @BindingAdapter("topAction")
    fun topAction(view:ImageView,isUser:Boolean){
        eee("isUser$isUser")
        if(isUser) {
            val lay = view.layoutParams
            lay.width = view.resources.getDimensionPixelSize(R.dimen.action_pic_size)
            lay.height = view.resources.getDimensionPixelSize(R.dimen.action_pic_size)
            view.layoutParams = lay
            view.setImageResource(R.drawable.share_1)

        }
        else view.setImageResource(R.drawable.focus)
    }






}