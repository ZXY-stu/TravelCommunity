package com.bignerdranch.travelcommunity.ui.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.databinding.FragmentVideoPlayerpageBinding
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.data.db.entity.PersonDynamic
import com.bignerdranch.travelcommunity.databinding.DynamicStyleUserpageBinding
import com.bignerdranch.travelcommunity.databinding.ItemDynamicBinding
import com.bignerdranch.travelcommunity.ui.dynamic.DynamicDetails
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.ui.user.FriendFragment
import com.bignerdranch.travelcommunity.ui.utils.VideoPageSnapHelper
import com.bignerdranch.travelcommunity.util.ToastUtil

/**
 * @author zhongxinyu
 * @date 2020/4/5
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class DynamicAdapter(val viewModel: PersonDynamicViewModel,
                     val framentManage:FragmentManager
):ListAdapter<PersonDynamic,RecyclerView.ViewHolder>(PersonDynamicDiff()) {




    val dynamicDetail = DynamicDetails(_viewModel = viewModel)



    inner class  DynamicViewHolder(private val binding: ItemDynamicBinding)
        :RecyclerView.ViewHolder(binding.root){
         var dynamicId = -1
        init {
            with(binding){
                 dynamicLayout.setOnClickListener {
                     val bundle = bundleOf("dynamicId" to dynamicId)
                       dynamicDetail.arguments?.putAll(bundle)
                      dynamicDetail.show(framentManage,"DynamicAdapter")
                 }


                 headUrl.setOnClickListener {

                     FriendFragment(_viewModel = viewModel).show(framentManage,"DynamicAdapter")
                 }
                 like.setOnClickListener {  }

            }
        }

        fun bind(item:PersonDynamic){
            with(binding){
                personDynamic = item
                dynamicId = item.id
                executePendingBindings()
            }
        }


    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dynamic = getItem(position)
        LogUtil.w("绑定")
        (holder as DynamicViewHolder).bind(dynamic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        LogUtil.w("创建")
        return DynamicViewHolder(ItemDynamicBinding.inflate(
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