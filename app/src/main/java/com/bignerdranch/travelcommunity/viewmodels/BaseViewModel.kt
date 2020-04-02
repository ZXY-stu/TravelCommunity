package com.bignerdranch.travelcommunity.viewmodels

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel
/**
 * @author zhongxinyu
 * @date 2020/4/2
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

//可实现双向绑定的ViewModel
open class BaseViewModel:ViewModel(),Observable{

    private val callbacks:PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
       callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
       callbacks.remove(callback)
    }

    fun notifyValueChanged(fieldId:Int){
        callbacks.notifyCallbacks(this,fieldId,null)
    }

    fun notifyChangedAll(){
        callbacks.notifyCallbacks(this,0,null)
    }
}