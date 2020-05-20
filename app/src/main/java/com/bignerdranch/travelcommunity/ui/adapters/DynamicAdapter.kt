package com.bignerdranch.travelcommunity.ui.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.data.db.entity.PersonDynamic
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.databinding.ItemDynamicBinding
import com.bignerdranch.travelcommunity.ui.dynamic.ImageDynamicFragment
import com.bignerdranch.travelcommunity.ui.dynamic.VideoDynamicFragment
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.ui.user.FriendFragment
import com.bignerdranch.travelcommunity.ui.user.NoticeLoginDialog

/**
 * @author zhongxinyu
 * @date 2020/4/5
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class DynamicAdapter(val viewModel: PersonDynamicViewModel,
                     val framentManage:FragmentManager,
                     val context: Context
):ListAdapter<PersonDynamic,RecyclerView.ViewHolder>(PersonDynamicDiff()) {

    private val TAG = "DynamicAdapter"
    inner class  DynamicViewHolder(private val binding: ItemDynamicBinding)
        :RecyclerView.ViewHolder(binding.root){
         var   mPersonDynamic:PersonDynamic? = null
        var isLike = false
        init {
            with(binding){
                 dynamicLayout.setOnClickListener {
                     var showVideo = false
                     mPersonDynamic?.videoUrl?.let {
                         if(it.length>4){
                             VideoDynamicFragment(mContext = context,personDynamic = mPersonDynamic!!).show(framentManage,TAG)
                             showVideo = true
                         }
                     }
                   if(!showVideo) ImageDynamicFragment(_viewModel = viewModel,mPersonDynamic = mPersonDynamic!!,
                       activity = context as Activity).show(framentManage,TAG)
                 }


                 headUrl.setOnClickListener {
                     val friendFragment1 =  FriendFragment(_viewModel = viewModel)
                     friendFragment1.setFriendId(mPersonDynamic!!.userId)
                     viewModel.toQueryFriendById(mPersonDynamic!!.userId)
                     friendFragment1.show(framentManage,TAG)
                 }

                 like.setOnClickListener {
                     checkLogin {
                         if (!isLike) {
                             like.setBackgroundResource(R.drawable.like_active)
                             viewModel.toAddLike(mPersonDynamic!!.id, 0)
                             isLike = true
                         } else {
                             isLike = false
                             like.setBackgroundResource(R.drawable.like)
                             viewModel.toDeleteLike(mPersonDynamic!!.id, 0)
                         }
                     }
                 }

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


    fun checkLogin(doWorkOk:()->Unit){
        if(viewModel.isLogin.value == false){
            NoticeLoginDialog().show(framentManage,"CommentsAdapter")

        }else{
            doWorkOk()
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