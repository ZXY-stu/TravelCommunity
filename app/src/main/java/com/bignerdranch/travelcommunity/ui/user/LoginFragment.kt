package com.bignerdranch.travelcommunity.ui.user

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.get
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseFragment
import com.bignerdranch.travelcommunity.databinding.LoginFragmentBinding
import com.bignerdranch.travelcommunity.ui.HomePageActivity
import com.bignerdranch.travelcommunity.util.InjectorUtils
import com.bignerdranch.travelcommunity.util.LogUtil
import com.bignerdranch.travelcommunity.util.ToastUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.my_toolbar.view.*

class LoginFragment : BaseFragment<LoginFragmentBinding>() {
    override val layoutId: Int = R.layout.login_fragment
    private val _viewModel by viewModels<UserViewModel> {
        InjectorUtils.userViewModelFactory(requireContext())
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeObserve()
    }

    private  fun subscribeObserve(){
        binding.viewModel = _viewModel

        _viewModel.attachLoading(loadingState)
        _viewModel.attachClose(close)

        _viewModel.isLogin.observe(viewLifecycleOwner){
            if(it) {
                startActivity(Intent(requireContext(),HomePageActivity::class.java))  //若登录成功，直接回到主界面
                _viewModel.loading.value = false
            }
        }

        _viewModel.toRegisterPage.observe(viewLifecycleOwner){
            if(it){
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment) //跳转到注册界面
            }
        }

        _viewModel.user.observe(viewLifecycleOwner){
                user ->
            //成功登录后，把user保持至本地
            if(user!=null) {

                //_viewModel.insertUser(user)
                LogUtil.e("插入中")
            }else{
                LogUtil.e("null")
            }
        }

        with(binding.toolbar.publicToolbar) {
            setNavigationOnClickListener {
             startActivity(Intent(requireContext(),HomePageActivity::class.java))
            }
              titleContent.text="登陆"
        }
    }

    override fun initImmersionBar() {

    }

}