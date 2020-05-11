package com.bignerdranch.travelcommunity.ui

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerViewHolder(private val binding:ViewDataBinding ) :
    RecyclerView.ViewHolder(binding.root) {
}