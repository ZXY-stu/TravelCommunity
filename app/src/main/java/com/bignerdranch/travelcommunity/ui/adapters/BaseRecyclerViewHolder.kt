package com.bignerdranch.travelcommunity.ui.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewHolder<T:ViewDataBinding,R>(private val binding:T) :
    RecyclerView.ViewHolder(binding.root) {

    abstract fun bindData(item:R)
    abstract fun initListener()
}