package com.bignerdranch.travelcommunity.ui.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.tclib.data.db.entity.PersonDynamic
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.databinding.ItemMyDynamicBinding
import com.bignerdranch.travelcommunity.ui.dynamic.ImageDynamicFragment
import com.bignerdranch.travelcommunity.ui.dynamic.VideoDynamicFragment
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel


/**
 * @author zhongxinyu
 * @date 2020/5/17
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class MyDynamicAdapter(
                      val mContext:Context,
                       val fragmentManager: FragmentManager,
                       val viewModel:PersonDynamicViewModel
) :BaseAdapter<ItemMyDynamicBinding,PersonDynamic>() {

    val TAG = "MyDynamicAdapter"
    override val itemLayoutId: Int = R.layout.item_my_dynamic

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
          val item = getItem(position)
        (holder as MyDynamicViewHolder).bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      return MyDynamicViewHolder(ItemMyDynamicBinding.inflate(LayoutInflater.from(mContext),parent,false))
    }

    inner class MyDynamicViewHolder(private val binding:ItemMyDynamicBinding):RecyclerView.ViewHolder(binding.root){
        private lateinit var mPersonDynamic: PersonDynamic

        init{
            binding.dynamicCard.setOnClickListener {
                var showVideo = false
                   mPersonDynamic.videoUrl?.let {
                       if (it.length >= 5) {
                           VideoDynamicFragment(
                               personDynamic = mPersonDynamic,
                               mContext = mContext
                           ).show(fragmentManager, TAG)
                           showVideo = true
                       }
                   }

                if(!showVideo)
                    ImageDynamicFragment(_viewModel = viewModel,mPersonDynamic = mPersonDynamic,activity = mContext as Activity).show(fragmentManager,TAG)
            }
        }
         fun bind(item:PersonDynamic){
             with(binding){
              personDynamic = item
              mPersonDynamic = item
                 executePendingBindings()
             }
         }
    }
}