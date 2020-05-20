package com.bignerdranch.travelcommunity.ui.dynamic

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe

import com.bignerdranch.travelcommunity.databinding.FragmentUserVideoBinding
import com.bignerdranch.travelcommunity.ui.adapters.VideoAdapter
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.util.InjectorUtils
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.LogUtil.ee
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseFragment
import kotlinx.android.synthetic.main.video_card.view.*
import java.io.IOException
import java.util.ArrayList
import java.util.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class UserPageVideo(override val layoutId: Int = R.layout.fragment_user_video
                    , override val needLogin: Boolean = false)
    : BaseFragment<FragmentUserVideoBinding>(){
    private val currentVideoIndex = 0


    private val _viewModel:PersonDynamicViewModel by viewModels {
        InjectorUtils.personDynamicViewModelFactory(requireContext())
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }




    private fun initView() {
       // surface =  binding.su
     //   surfaceHolder = surface?.holder // SurfaceHolder是SurfaceView的控制接口
       // surfaceHolder?.addCallback(this) // 因为这个类实现了SurfaceHolder.Callback接口，所以回调参数直接this
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.viewModel = _viewModel

       ee("onCreateView")
       initBindingAndAdapter()
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     //   initView()
    }



    private fun initBindingAndAdapter(){
      val myAdapter = VideoAdapter()

      binding.viewModel = _viewModel
     binding.userVideoView.adapter = myAdapter




    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
            _viewModel.loadingMore()
    }

    override fun subscribeUi() {
        _viewModel.personDynamics.observe(viewLifecycleOwner) {
            LogUtil.e("Size"+it.size)
            //  adapter.submitList(_viewModel.toVideoSimpleData(it))
        }
        _viewModel.loading.observe(viewLifecycleOwner){
            if(it){
                LogUtil.e("来饿了")

                //    _viewModel.loadingMore()
                //   _viewModel.loading.value = false
            }
        }

        _viewModel.wait.observe(viewLifecycleOwner){
            LogUtil.e("来了呀1111111111111111111111")
        }
    }

    override fun subscribeListener() {

    }

    override fun subscribeObserver() {

    }


}
