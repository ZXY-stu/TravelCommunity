package com.bignerdranch.travelcommunity.ui.dynamic

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe

import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.util.InjectorUtils
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.tclib.data.db.entity.PersonDynamic
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.adapters.Coverters
import com.bignerdranch.travelcommunity.base.BaseFragment
import com.bignerdranch.travelcommunity.databinding.DynamicRecycleviewBinding
import com.bignerdranch.travelcommunity.task.RenderImage
import com.bignerdranch.travelcommunity.ui.RecyclerViewForViewPage2
import com.bignerdranch.travelcommunity.ui.adapters.DynamicAdapter
import com.bignerdranch.travelcommunity.ui.adapters.VideoViewAdapter
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel.Companion.toQueryWhat
import com.bignerdranch.travelcommunity.util.GlideUtil
import java.util.concurrent.Executors


class HomePageDynamic(override val layoutId: Int = R.layout.dynamic_recycleview
                               , override val needLogin: Boolean = false)
    :BaseFragment<DynamicRecycleviewBinding>(),RecyclerViewForViewPage2.EnableScorllXListener {

    private  var currentPageNum = 0

    private lateinit var mPersonDynamics:List<PersonDynamic>

    private lateinit var adapter:DynamicAdapter
     private lateinit var recycleView:RecyclerViewForViewPage2

    private var scorllXListener:RecyclerViewForViewPage2.EnableScorllXListener? = null

    private  val _viewModel: PersonDynamicViewModel by viewModels{
        InjectorUtils.personDynamicViewModelFactory(requireContext())
    }

    private var lastView:View? = null
    private var hasCreated = false

    fun  setScorllXListener(scorllXListener:RecyclerViewForViewPage2.EnableScorllXListener):HomePageDynamic{
        this.scorllXListener = scorllXListener
        return this
    }

/* 查询动态
    *  name = userId 用户id
    *  name = queryId 查询类型
    *  0 系统自动推荐用户动态
    *  1 查询userId用户的好友动态
    *  2 查询userId用户发表过的动态
    *  pageNumber 当前页数
    * */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _viewModel.toQueryDynamics(toQueryWhat,currentPageNum)
      if(lastView==null){
          super.onCreateView(inflater, container, savedInstanceState)
          lastView = binding.root
      }
        return lastView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       if(!hasCreated) {
           super.onViewCreated(view, savedInstanceState)
         hasCreated = true
       }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {

        if(newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            //   binding.dynamicRecycleview.visibility = View.VISIBLE
        }else{
            //binding.dynamicRecycleview.visibility = View.GONE
        }
        super.onConfigurationChanged(newConfig)
        eee("onConfigurationChanged")
    }



    override  fun subscribeUi(){
        adapter  = DynamicAdapter(_viewModel,requireActivity().supportFragmentManager,requireActivity())
        recycleView = binding.dynamicRecycleview
        recycleView.setUnableScorllXListener(this)
        recycleView.setMaxDisY(10)
        binding.viewModel = _viewModel
        recycleView.adapter = adapter
    }

    private fun addView(position: Int){

    }

    private fun findView(position: Int): DynamicAdapter.DynamicViewHolder? {
        val view = recycleView.findViewHolderForAdapterPosition(position)
        return view?.let {
            it  as DynamicAdapter.DynamicViewHolder
        }
    }

     override fun subscribeListener() {

     }

     override fun subscribeObserver() {
         _viewModel.personDynamics.observe(viewLifecycleOwner){

             adapter.submitList(it)
         }
     }

     override fun enable(flag: Boolean) {
         scorllXListener?.enable(flag)
     }
 }




