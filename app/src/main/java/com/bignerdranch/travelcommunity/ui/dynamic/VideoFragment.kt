package com.bignerdranch.travelcommunity.ui.dynamic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.bignerdranch.travelcommunity.LoadingObserver
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseFragment

import com.bignerdranch.travelcommunity.data.network.api.PersonDynamicService
import com.bignerdranch.travelcommunity.databinding.VideoplayerviewBinding
import com.bignerdranch.travelcommunity.ui.adapters.HomePageVideoAdapter
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.ui.listener.TCRecycleViewListener
import com.bignerdranch.travelcommunity.util.InjectorUtils
import com.bignerdranch.travelcommunity.util.LogUtil

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VideoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VideoFragment : BaseFragment<VideoplayerviewBinding>() {
    // TODO: Rename and change types of parameters

    private  val _viewModel: PersonDynamicViewModel by viewModels{
        InjectorUtils.personDynamicViewModelFactory(requireContext())
    }
    override val layoutId: Int = R.layout.videoplayerview

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val adapter  = HomePageVideoAdapter()

        binding.viewModel = _viewModel
        _viewModel.attachLoading(loadingState)
        binding.homeVideoView.adapter = adapter

        binding.homeVideoView.addOnScrollListener(TCRecycleViewListener().setViewModel(_viewModel))
        subscribeUi(adapter)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogUtil.e("执行OnActiv")

    }

    override fun initImmersionBar() {

    }


    private  fun subscribeUi(adapter: HomePageVideoAdapter){

        _viewModel?.personDynamics?.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        _viewModel.loading.observe(viewLifecycleOwner){
            if(it){
                  _viewModel.loadingMore()
                _viewModel.toAddHomeVideoPage()
                  _viewModel.loading.value = false
            }
        }
        //viewModel .observe(viewLifecycleOwner, LoadingObserver(activity!!))
    }
}




