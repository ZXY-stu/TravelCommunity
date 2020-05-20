package com.bignerdranch.travelcommunity.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseFragment
import com.bignerdranch.travelcommunity.databinding.FragmentOtherBinding
import com.bignerdranch.travelcommunity.util.InjectorUtils

class OtherFragment(override val layoutId: Int = R.layout.fragment_other,
                    override val needLogin: Boolean = true)
    : BaseFragment<FragmentOtherBinding>() {
    private val _viewModel by activityViewModels<UserViewModel> {
        InjectorUtils.userViewModelFactory(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_other, container, false)
    }


}
