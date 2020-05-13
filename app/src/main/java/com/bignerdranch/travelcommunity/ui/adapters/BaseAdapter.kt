package com.bignerdranch.travelcommunity.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * @author zhongxinyu
 * @date 2020/5/12
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
/*
class BaseAdapter<T> :ListAdapter<T,RecyclerView.ViewHolder>(Diff()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }


    inner class Diff():DiffUtil.ItemCallback<T>(){
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return  oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return  true
        }

    }

}*/