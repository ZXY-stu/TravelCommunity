package com.bignerdranch.travelcommunity.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.databinding.FragmentVideoPlayerpageBinding
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.data.db.entity.PersonDynamic
import com.bignerdranch.travelcommunity.databinding.DynamicImagestyleUserpageBinding
import com.bignerdranch.travelcommunity.ui.utils.VideoPageSnapHelper
import com.bignerdranch.travelcommunity.util.ToastUtil

/**
 * @author zhongxinyu
 * @date 2020/4/5
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class HomePageVideoAdapter:ListAdapter<PersonDynamic,RecyclerView.ViewHolder>(PersonDynamicDiff()) {
    val images = listOf(
        "https://upload.wikimedia.org/wikipedia/commons/6/67/Mangos_criollos_y_pera.JPG","https://upload.wikimedia.org/wikipedia/commons/0/03/Grape_Plant_and_grapes9.jpg",
        "https://upload.wikimedia.org/wikipedia/commons/2/22/Apfelsinenbaum--Orange_tree.jpg", "https://upload.wikimedia.org/wikipedia/commons/a/aa/Sunflowers_in_field_flower.jpg",
       "https://upload.wikimedia.org/wikipedia/commons/a/ab/Cypripedium_reginae_Orchi_004.jpg"
    )
    private val adapter = VideoAdapter()
   inner class HomePagevideoViewHolder(private val binding: DynamicImagestyleUserpageBinding)
        :RecyclerView.ViewHolder(binding.root){

        init {
            with(binding){
                   imageMatrix.adapter = adapter
                   VideoPageSnapHelper().attachToRecyclerView(imageMatrix)
            }
        }

        fun bind(item:PersonDynamic){
            with(binding){
                personDynamic = item
                adapter.submitList(images)
                executePendingBindings()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dynamic = getItem(position)
        LogUtil.w("绑定")
        (holder as HomePagevideoViewHolder).bind(dynamic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        LogUtil.w("创建")
         return HomePagevideoViewHolder(DynamicImagestyleUserpageBinding.inflate(
             LayoutInflater.from(parent.context),parent,false))
    }

    class PersonDynamicDiff :DiffUtil.ItemCallback<PersonDynamic>(){
        override fun areContentsTheSame(oldItem: PersonDynamic, newItem: PersonDynamic): Boolean {
            LogUtil.w("比较")
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: PersonDynamic, newItem: PersonDynamic): Boolean {
            LogUtil.w("比较")
            return oldItem.id == newItem.id
        }
    }

}