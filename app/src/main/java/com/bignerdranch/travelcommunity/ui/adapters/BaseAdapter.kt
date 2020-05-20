package com.bignerdranch.travelcommunity.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.tclib.data.db.entity.Base

/**
 * @author zhongxinyu
 * @date 2020/5/12
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

abstract class BaseAdapter<R:ViewDataBinding,T:Base> :ListAdapter<T,RecyclerView.ViewHolder>(Diff<T>()){
    abstract val itemLayoutId:Int

    class Diff<T:Base>:DiffUtil.ItemCallback<T>(){
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.equals(newItem)
        }

    }

}