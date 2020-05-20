package com.bignerdranch.travelcommunity.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseFragment
import com.bignerdranch.travelcommunity.databinding.FragmentRegisterBinding
import com.bignerdranch.travelcommunity.ui.clearAndToHome
import com.bignerdranch.travelcommunity.util.InjectorUtils
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.travelcommunity.base.BaseDialogFragment
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
class RegisterFragment : BaseDialogFragment<FragmentRegisterBinding>() {
    override val themeResId: Int = R.style.DialogFullScreen_Right
    override val needLogin: Boolean = false
    private  val _viewModel by activityViewModels<UserViewModel> {
        InjectorUtils.userViewModelFactory(requireContext())
    }

    override val layoutId: Int = R.layout.fragment_register

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

             return binding.root
         }




    override fun subscribeUi() {
        binding.viewModel = _viewModel
        with(binding.toolbar.publicToolbar) {
            setNavigationOnClickListener {
                clearAndToHome(requireContext())
            }
            titleContent.text = "手机号验证登陆"
        }
    }

    override fun subscribeListener() {

    }

    override fun subscribeObserver() {
        _viewModel.localUser.observe(viewLifecycleOwner) {
            //若注册成功，跳转到主界面，同时服务器进行登陆操作
            eee("clearAndToHome &$it")
            it?.let {
                clearAndToHome(requireContext())
            }
        }
    }


}
