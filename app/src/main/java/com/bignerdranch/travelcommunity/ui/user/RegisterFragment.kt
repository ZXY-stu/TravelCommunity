package com.bignerdranch.travelcommunity.ui.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseFragment
import com.bignerdranch.travelcommunity.databinding.FragmentRegisterBinding
import com.bignerdranch.travelcommunity.ui.HomePageActivity
import com.bignerdranch.travelcommunity.util.InjectorUtils
import com.bignerdranch.travelcommunity.util.LogUtil
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.login_fragment.view.*
import kotlinx.android.synthetic.main.my_toolbar.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private  val _viewModel by activityViewModels<UserViewModel> {
        InjectorUtils.userViewModelFactory(requireContext())
    }

    override val layoutId: Int = R.layout.fragment_register


    override fun initImmersionBar() {
         LogUtil.e("")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
         binding.viewModel = _viewModel
         with(binding.toolbar.public_toolbar) {
             setNavigationOnClickListener {
                 it.findNavController().popBackStack(R.id.login_and_register, false)
             }
             titleContent.text = "手机号验证登陆"
         }
             subscribeObserve()
             return binding.root
         }



    private fun subscribeObserve(){
         _viewModel.register.observe(viewLifecycleOwner){
              it?.let {
                  _viewModel.insertUser(it)   //若注册成功，将user插入本地数据库，随后跳转到主界面，同时服务器进行登陆操作
                  startActivity(Intent(requireContext(),HomePageActivity::class.java))
              }
         }
    }




}
