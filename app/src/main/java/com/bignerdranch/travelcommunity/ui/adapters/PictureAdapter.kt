package com.bignerdranch.travelcommunity.ui.adapters

import android.graphics.Matrix
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.adapters.ViewBindingAdapter.setClickListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.tclib.data.db.entity.PersonDynamic
import com.bignerdranch.tclib.data.db.entity.SimpleVideoData
import com.bignerdranch.travelcommunity.adapters.Coverters
import com.bignerdranch.travelcommunity.databinding.ImageitemBinding
import com.bignerdranch.travelcommunity.databinding.VideoCardBinding
import com.bignerdranch.travelcommunity.ui.utils.ImageEditor

/**
 * @author zhongxinyu
 * @date 2020/4/5
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

class PictureAdapter(): ListAdapter<String,RecyclerView.ViewHolder>(VideoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return   VideoViewHolder(ImageitemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
        /*  return when (viewType) {
              VIDEO_VIEW  -> VideoViewHolder(VideoCardBinding.inflate(
                       LayoutInflater.from(parent.context), parent, false
                  ))
              REFRESH_VIEW   ->{
                   RefreshViewHolder(RecycleviewFootStyleBinding.inflate(
                       LayoutInflater.from(parent.context),parent,false
                   ))
               }
              else -> throw IllegalStateException("VideoAdapter View ERROR")
          }*/
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val video = getItem(position)
        if(video!=null) {
            (holder as VideoViewHolder).bind(video)
        }
        //  else  (holder as RefreshViewHolder).bind("正在加载...")
    }

    /*override fun getItemViewType(position: Int): Int {
         return if(currentList.get(position)==null) REFRESH_VIEW else VIDEO_VIEW
    }*/

    /*class RefreshViewHolder(private val binding: RecycleviewFootStyleBinding)
        :RecyclerView.ViewHolder(binding.root){

        fun bind(item:String) {
            binding.apply {
                state = item
                executePendingBindings()
            }
        }
    }*/

     inner  class VideoViewHolder(private  val binding:ImageitemBinding)
        : RecyclerView.ViewHolder(binding.root) {
         val l = Matrix()
        init {

            with(binding) {

            }
        }

        fun  bind(item:String){
            binding.apply {
                url = item

                executePendingBindings()
            }
        }
    }

    private class VideoDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return  oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem== newItem
        }
    }
}
