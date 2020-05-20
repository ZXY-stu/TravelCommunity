package com.bignerdranch.travelcommunity.ui.adapters

import android.app.Activity
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.travelcommunity.databinding.ImageitemBinding
import com.bignerdranch.travelcommunity.databinding.ItemPictureBinding
import com.bignerdranch.travelcommunity.ui.dynamic.ImageEditorDialog

/**
 * @author zhongxinyu
 * @date 2020/5/17
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

const val minSize = 1  //最小的图片数量，当前数量为1，表示仅有一张提示添加图片的按钮
const val maxSize = 10 //最大的图片数量，最大能够选择的图片数量为9张，第十张图片默认不显示，为提示图片

class ChoosePictureAdapter(val activity:Activity,
                           val fragmentManager: FragmentManager
):ListAdapter<Uri,RecyclerView.ViewHolder>(Diff()){

    interface ItemClickListener{
        fun onItemClick(position: Int)
    }

     private var onItemChangedListener:ImageEditorDialog.OnItemChangedListener? = null
    private var firstEnter = true

    private var itemClickListener:ItemClickListener? = null

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    fun setOnItemChangedListener(onItemChangedListener:ImageEditorDialog.OnItemChangedListener):ChoosePictureAdapter{
        this.onItemChangedListener = onItemChangedListener
        return this
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
      val item = getItem(position)
        (holder as ChoosePictureViewHolder).bind(item,position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      return ChoosePictureViewHolder(ItemPictureBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    inner class ChoosePictureViewHolder(private val binding:ItemPictureBinding):
            RecyclerView.ViewHolder(binding.root){

        var curPosition = -1

        init {
            with(binding){
                  picture.setOnClickListener {
                      if( !firstEnter  &&  curPosition < itemCount-1  ){
                          //点击了添加图片
                          eee("curPosition $curPosition itemCount $itemCount")
                          ImageEditorDialog(activity,currentList.subList(0,currentList.lastIndex),
                              curPosition).setOnItemChangedListener(object :ImageEditorDialog.OnItemChangedListener{
                              override fun itemChanged(position: Int) {
                                  onItemChangedListener?.itemChanged(position)
                              }
                          })
                              .show(fragmentManager,"")
                      }else {
                          itemClickListener?.onItemClick(curPosition)
                          firstEnter = false
                      }

                  }
            }
        }

        fun bind(item:Uri,position: Int){
            curPosition = position
            binding.url = item

            binding.executePendingBindings()
        }

    }


    class  Diff(): DiffUtil.ItemCallback<Uri>() {
        override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
         return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }
    }

}