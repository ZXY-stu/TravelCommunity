package com.bignerdranch.travelcommunity.ui.dynamic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe

import com.bignerdranch.travelcommunity.databinding.VideoplayerviewBinding
import com.bignerdranch.travelcommunity.ui.adapters.HomePageVideoAdapter
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.ui.listener.HomePageViewListener
import com.bignerdranch.travelcommunity.util.InjectorUtils
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseFragment



class HomePageVideo(override val layoutId: Int = R.layout.videoplayerview
                    , override val needLogin: Boolean = false)
    :BaseFragment<VideoplayerviewBinding>() {

    private  val _viewModel: PersonDynamicViewModel by viewModels{
        InjectorUtils.personDynamicViewModelFactory(requireContext())
    }

    private lateinit var adapter:HomePageVideoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogUtil.e("执行OnActiv")

    }




    override fun subscribeUi() {
        adapter  = HomePageVideoAdapter()
        binding.viewModel = _viewModel
        binding.homeVideoView.adapter = adapter
    }

    override fun subscribeListener() {

    }

    override fun subscribeObserver() {
        _viewModel.personDynamics.observe(viewLifecycleOwner){
            LogUtil.e("size2020 ${it.size}")
            adapter.submitList(it)

        }

        _viewModel.wait.observe(viewLifecycleOwner){
            LogUtil.e("wait....")
        }

        _viewModel.loading.observe(viewLifecycleOwner){
            if(it){
                with(_viewModel){
                    _viewModel.loadingMore()
                    LogUtil.e("正在...")
                    // toAddHomeVideoPage()
                    loading.value = false
                }
            }
        }

    }
}




