package com.bignerdranch.travelcommunity.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController

import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseFragment
import com.bignerdranch.travelcommunity.databinding.FragmentNoticeLoginBinding
import com.bignerdranch.travelcommunity.util.InjectorUtils
import com.bignerdranch.tclib.LogUtil

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NoticeLoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NoticeLoginFragment : BaseFragment<FragmentNoticeLoginBinding>() {

    override val layoutId: Int = R.layout.fragment_notice_login
    override val needLogin = false
   private val _viewModel by activityViewModels<UserViewModel> {
       InjectorUtils.userViewModelFactory(requireContext())
   }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)

         binding.viewModel = _viewModel
         _viewModel.toLoginPage.observe(viewLifecycleOwner){
             LogUtil.eee("在 notice")
             if(it)
             LoginFragment().show(requireActivity().supportFragmentManager,"NoticeLoginFragment")
         }
        return binding.root
    }

    override fun subscribeUi() {

    }

    override fun subscribeListener() {

    }

    override fun subscribeObserver() {

    }
}
