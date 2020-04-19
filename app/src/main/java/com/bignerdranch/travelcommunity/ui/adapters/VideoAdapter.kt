package com.bignerdranch.travelcommunity.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.travelcommunity.data.db.entity.SimpleVideoData
import com.bignerdranch.travelcommunity.databinding.RecycleviewFootStyleBinding
import com.bignerdranch.travelcommunity.databinding.VideoCardBinding
import com.bignerdranch.travelcommunity.util.LogUtil
import java.lang.IllegalStateException

/**
 * @author zhongxinyu
 * @date 2020/4/5
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

class VideoAdapter: ListAdapter<SimpleVideoData,RecyclerView.ViewHolder>(VideoDiffCallback()) {

    companion object ViewType{
        private val REFRESH_VIEW = 1
        private val VIDIEO_VIEW = 2
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIDIEO_VIEW  -> VideoViewHolder(
                VideoCardBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ))
            REFRESH_VIEW   ->{
                 RefreshViewHolder(RecycleviewFootStyleBinding.inflate(
                     LayoutInflater.from(parent.context),parent,false
                 ))
             }
            else -> throw IllegalStateException("VideoAdapter View ERROR")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val video = getItem(position)
        if(video!=null) {
            if(holder is VideoViewHolder)  holder.bind(video)
        }
        else  (holder as RefreshViewHolder).bind("正在加载...")
    }

    override fun getItemViewType(position: Int): Int {
         return if(currentList.get(position)==null) REFRESH_VIEW else VIDIEO_VIEW
    }

  class RefreshViewHolder(private val binding: RecycleviewFootStyleBinding)
      :RecyclerView.ViewHolder(binding.root){
      fun bind(item:String) {
          binding.apply {
              state = item
              executePendingBindings()
          }
      }
  }

    class VideoViewHolder(private  val binding:VideoCardBinding)
        : RecyclerView.ViewHolder(binding.root)
    {
         init {
             binding.setClickListener {
                binding.video?.let {  video ->
                       navigateToVideo(video,it)
                }
             }
         }

        fun navigateToVideo(video:SimpleVideoData,
                            view: View) {

        }
         fun  bind(itme:SimpleVideoData){
            binding.apply {
                video = itme
                executePendingBindings()
            }
        }
    }




    private class VideoDiffCallback : DiffUtil.ItemCallback<SimpleVideoData>() {

        override fun areItemsTheSame(
            oldItem: SimpleVideoData,
            newItem: SimpleVideoData
        ): Boolean {
            return  oldItem.dynamicId == newItem.dynamicId
        }

        override fun areContentsTheSame(
            oldItem: SimpleVideoData,
            newItem: SimpleVideoData
        ): Boolean {
            return oldItem== newItem
        }
    }



}
