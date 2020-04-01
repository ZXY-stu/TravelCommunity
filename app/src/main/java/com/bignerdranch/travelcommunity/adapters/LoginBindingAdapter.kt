package com.bignerdranch.travelcommunity.adapters

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.util.LogUtil

object LoginBindingAdapter{
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




}