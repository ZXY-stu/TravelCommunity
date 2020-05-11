package com.bignerdranch.travelcommunity.ui.user

import android.os.Bundle
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
import kotlinx.android.synthetic.main.my_toolbar.view.*

class LoginFragment(override val needLogin: Boolean = false) : BaseFragment<LoginFragmentBinding>() {
    override val layoutId: Int = R.layout.login_fragment

    private var returnToHome = false
    private val _viewModel by viewModels<UserViewModel> {
        InjectorUtils.userViewModelFactory(requireContext())
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeObserve()
    }



    private  fun subscribeObserve(){
        binding.viewModel = _viewModel

       // _viewModel.attachLoading(loadingState)
       // _viewModel.attachClose(close)

        _viewModel.isLogin.observe(viewLifecycleOwner){
            if(it) {
                if (returnToHome)
                    clearAndToHome(requireContext())
                else
                    with(findNavController()) {
                        popBackStack(graph.startDestination,true)
                    }
            }
        }

        _viewModel.toRegisterPage.observe(viewLifecycleOwner){
            if(it){
                eee("toRegisterPage")
                 //findNavController().popBackStack(R.id.action_loginFragment_to_registerFragment,true)
                  val ac = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                  findNavController().navigate(ac)
                //跳转到注册界面
            }
        }

       /*
        测试之前的结果是否OK，暂时注释
       _viewModel.user.observe(viewLifecycleOwner){
                user ->
            //成功登录后，把user保持至本地
            if(user!=null) {

                _viewModel.insertUser(user)
                LogUtil.e("插入中")
            }else{
                LogUtil.e("null")
            }
        }*/

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
    override fun initImmersionBar() {

    }

}