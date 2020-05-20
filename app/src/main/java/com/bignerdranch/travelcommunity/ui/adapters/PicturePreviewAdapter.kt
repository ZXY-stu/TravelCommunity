package com.bignerdranch.travelcommunity.ui.adapters

import android.app.Activity
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.travelcommunity.databinding.*
import com.bignerdranch.travelcommunity.ui.dynamic.MyLayoutManager
import com.bignerdranch.travelcommunity.ui.utils.DRAG
import com.bignerdranch.travelcommunity.ui.utils.ImageEditor
import com.bignerdranch.travelcommunity.ui.utils.ZOOM


/**
 * @author zhongxinyu
 * @date 2020/5/18
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

class PicturePreviewAdapter(val activity: Activity,
                           val layoutManager:MyLayoutManager)
    : ListAdapter<Uri,RecyclerView.ViewHolder>(Diff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return   VideoViewHolder(ItemImageEditorBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val video = getItem(position)
        if(video!=null) {
            (holder as VideoViewHolder).bind(video)
        }
    }


    inner  class VideoViewHolder(private  val binding:ItemImageEditorBinding)
        : RecyclerView.ViewHolder(binding.root) {
         var _item:Uri? = null
        var _picture:ImageView? = null
        init {

        }

        fun  bind(item:Uri){
            with(binding) {
                uri = item
                _item = item
                _picture =  picture
              /*  ImageEditor()
                    .bindTouchEvent(_item!!,activity,_picture!!)
                    .setStateChangedListener(object :ImageEditor.StateChangeListener{
                        override fun stateChanged(state: Int) {
                            when(state){
                                ZOOM, DRAG ->  layoutManager.setScrollX(false)  //若图片处于放大，和拖动状态，禁止recycleview进行滑动
                            }
                        }
                    })*/

                executePendingBindings()
            }
        }


        }

    private class Diff : DiffUtil.ItemCallback<Uri>() {
        override fun areItemsTheSame(
            oldItem: Uri,
            newItem: Uri
        ): Boolean {
            return  oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Uri,
            newItem: Uri
        ): Boolean {
            return oldItem== newItem
        }
    }
}
