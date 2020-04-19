package com.bignerdranch.travelcommunity.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.data.db.entity.CommentsMsg
import com.bignerdranch.travelcommunity.databinding.CommentsBinding

/**
 * @author zhongxinyu
 * @date 2020/4/6
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class CommentsAdapter : ListAdapter<CommentsMsg,RecyclerView.ViewHolder>(CommentsDiff()) {

    class CommentsViewHolder(private val binding:CommentsBinding)
        :RecyclerView.ViewHolder(binding.root){
         init {
          //设置监听事件
         }

        fun bind(commentsMsg: CommentsMsg){
              with(binding){
                  this.comments = commentsMsg
                  executePendingBindings()
              }
        }

        fun navigateTo(){

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val comment = getItem(position)
        (holder as CommentsViewHolder).bind(comment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return  CommentsViewHolder(CommentsBinding.
           inflate(LayoutInflater.from(parent.context),parent,false)
       )
    }

    class CommentsDiff:DiffUtil.ItemCallback<CommentsMsg>(){
        override fun areContentsTheSame(oldItem: CommentsMsg, newItem: CommentsMsg): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: CommentsMsg, newItem: CommentsMsg): Boolean {
           return oldItem.id == newItem.id
        }
    }
}