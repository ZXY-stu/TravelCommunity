package com.bignerdranch.travelcommunity.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseFragment
import com.bignerdranch.travelcommunity.databinding.LoginFragmentBinding
import com.bignerdranch.travelcommunity.ui.clearAndToHome
import com.bignerdranch.travelcommunity.util.InjectorUtils
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.travelcommunity.base.BaseDialogFragment
import kotlinx.android.synthetic.main.my_toolbar.view.*

class LoginFragment(override val needLogin: Boolean = false,
                    override val themeResId: Int = R.style.DialogFullScreen_Bottom
) : BaseDialogFragment<LoginFragmentBinding>() {
    override val layoutId: Int = R.layout.login_fragment
    val dark: Boolean = false
    private var returnToHome = false
    private val _viewModel by viewModels<UserViewModel> {
        InjectorUtils.userViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }



    override  fun subscribeObserver(){
        binding.viewModel = _viewModel
        _viewModel.isLogin.observe(viewLifecycleOwner){
            if(it) {
              // dismiss()
               clearAndToHome(requireContext())
             /*   if (returnToHome)
                    clearAndToHome(requireContext())
                else
                    with(findNavController()) {
                        popBackStack(graph.startDestination,true)
                    }*/
            }
        }

        _viewModel.toRegisterPage.observe(viewLifecycleOwner){
            if(it){
                eee("toRegisterPage")
                 //findNavController().popBackStack(R.id.action_loginFragment_to_registerFragment,true)
                 RegisterFragment().show(requireActivity().supportFragmentManager,"LoginFragment")
                //跳转到注册界面
            }
        }

        with(binding.toolbar.publicToolbar) {
            setNavigationOnClickListener {
                clearAndToHome(requireContext())
            }
              titleContent.text="登陆"
        }
    }


    fun setReturnToHome(isTo:Boolean):LoginFragment{
        returnToHome = isTo
        return this
    }

    override fun subscribeUi() {

    }

    override fun subscribeListener() {

    }


}