package com.bignerdranch.travelcommunity.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseFragment
import com.bignerdranch.travelcommunity.databinding.UserDynamicFragmentBinding
import com.bignerdranch.travelcommunity.ui.adapters.MyDynamicAdapter
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.util.InjectorUtils

/**
 * @author zhongxinyu
 * @date 2020/5/17
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class UserPageDynamic(
    override val layoutId: Int = R.layout.user_dynamic_fragment,
    override val needLogin: Boolean = false
) :BaseFragment<UserDynamicFragmentBinding>(){

    private val _viewModel by activityViewModels<PersonDynamicViewModel> {
        InjectorUtils.personDynamicViewModelFactory(requireContext())
    }
    private lateinit var adapter:MyDynamicAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        adapter = MyDynamicAdapter(requireContext(),requireActivity().supportFragmentManager,_viewModel)
        binding.myDynamicRecycleview.adapter = adapter
        _viewModel.userPersonDynamic.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        return binding.root
    }


}