package com.bignerdranch.travelcommunity.ui.dynamic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe

import com.bignerdranch.travelcommunity.data.db.TCDataBases
import com.bignerdranch.travelcommunity.databinding.FragmentUserVideoBinding
import com.bignerdranch.travelcommunity.ui.adapters.VideoAdapter
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.ui.listener.TCRecycleViewListener
import com.bignerdranch.travelcommunity.util.InjectorUtils
import com.bignerdranch.travelcommunity.util.LogUtil
import kotlinx.coroutines.runBlocking

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserVideoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class UserVideoFragment : Fragment() {

    private val _viewModel:PersonDynamicViewModel by viewModels {
        InjectorUtils.personDynamicViewModelFactory(requireContext())
    }

    private lateinit var binding:FragmentUserVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserVideoBinding.inflate(inflater, container, false)



       initBindingAndAdapter()
        return binding.root
    }


   private fun initBindingAndAdapter(){
            val adapter = VideoAdapter()
            with(binding) {
                userVideoView.adapter = adapter
                viewModel = _viewModel
                subscribeUi(adapter, binding)

                userVideoView.addOnScrollListener(TCRecycleViewListener().setViewModel(_viewModel))

            }



    }
     private  fun subscribeUi(adapter: VideoAdapter,
                              binding: FragmentUserVideoBinding){

        _viewModel.personDynamics?.observe(viewLifecycleOwner) {
            LogUtil.e(it.toString())
               adapter.submitList(_viewModel.toVideoSimpleData(it))
           }
        /*   _viewModel.isLoading.observe(viewLifecycleOwner){
               if(it) _viewModel.initHomePageVideo()
           }*/
     }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        _viewModel.initHomePageVideo()
    }

}
