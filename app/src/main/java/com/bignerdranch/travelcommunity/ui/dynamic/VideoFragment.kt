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

import com.bignerdranch.travelcommunity.data.network.api.PersonDynamicService
import com.bignerdranch.travelcommunity.databinding.VideoplayerviewBinding
import com.bignerdranch.travelcommunity.ui.adapters.HomePageVideoAdapter
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
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
class VideoFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private  val viewModel: PersonDynamicViewModel by viewModels{
        InjectorUtils.personDynamicViewModelFactory(requireContext())
    }
    private lateinit var  binding:VideoplayerviewBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = VideoplayerviewBinding.inflate(inflater,container,false)

        val adapter  = HomePageVideoAdapter()

        binding.viewModel = viewModel
        binding.homeVideoView.adapter = adapter


        subscribeUi(adapter)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogUtil.e("执行OnActiv")
        viewModel.initHomePageVideo()

    }


    private  fun subscribeUi(adapter: HomePageVideoAdapter){

      viewModel?.personDynamics?.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        //viewModel .observe(viewLifecycleOwner, LoadingObserver(activity!!))
    }
}




