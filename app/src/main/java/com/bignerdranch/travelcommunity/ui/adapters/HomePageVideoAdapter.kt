package com.bignerdranch.travelcommunity.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.travelcommunity.data.db.entity.PersonDynamic
import com.bignerdranch.travelcommunity.databinding.FragmentVideoPlayerpageBinding
import com.bignerdranch.travelcommunity.util.LogUtil

/**
 * @author zhongxinyu
 * @date 2020/4/5
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class HomePageVideoAdapter:ListAdapter<PersonDynamic,RecyclerView.ViewHolder>(PersonDynamicDiff()) {

    init {
        LogUtil.w("在HomePage")
    }

    class HomePagevideoViewHolder(private val binding: FragmentVideoPlayerpageBinding)
        :RecyclerView.ViewHolder(binding.root){

        init {
            LogUtil.w("创建")
            binding.setClickListener {
                binding.personDynamic?.let{ dynamic ->
                    //跳转处理
                    navigateTo(dynamic.id)
                }
            }
        }

        fun bind(item:PersonDynamic){
            with(binding){
                personDynamic = item
                LogUtil.w("绑定"+personDynamic.toString())
                executePendingBindings()
            }
        }

        private fun navigateTo(dynamicId:Int){

        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dynamic = getItem(position)
        LogUtil.w("绑定")
        (holder as HomePagevideoViewHolder).bind(dynamic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        LogUtil.w("创建")
         return HomePagevideoViewHolder(FragmentVideoPlayerpageBinding.inflate(
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