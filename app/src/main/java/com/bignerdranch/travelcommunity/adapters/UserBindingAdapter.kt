package com.bignerdranch.travelcommunity.adapters

import android.os.Build
import android.text.Html
import android.util.Log
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
import com.bignerdranch.tclib.data.db.entity.CommentsMsg
import com.bignerdranch.travelcommunity.util.HtmlTextStyleUtil
import com.bumptech.glide.Glide


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
            Glide.with(view.context)
                .load(imageUrl)
                .dontAnimate()
                .into(view)
            Log.w("MainAc",imageUrl)
            LogUtil.e("${view.javaClass} + $imageUrl")
        }
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
        when(sex){
            "m" -> view.setImageResource(R.drawable.man)
            "w" -> view.setImageResource(R.drawable.woman)
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




}