package com.bignerdranch.travelcommunity.base

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.bignerdranch.travelcommunity.LoadingObserver
import com.bignerdranch.travelcommunity.util.InjectorUtils
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.tclib.data.repository.BaseRepository
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.ui.dynamic.PublishFragment
import com.bignerdranch.travelcommunity.ui.utils.StatusBarUtil
import com.bignerdranch.travelcommunity.ui.utils.StatusBarUtil.getStatusBarHeight
import com.bignerdranch.travelcommunity.util.ToastUtil
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.gyf.immersionbar.components.SimpleImmersionFragment

/**
 * @author zhongxinyu
 * @date 2020/4/9
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

abstract  class BaseFragment<T:ViewDataBinding>:SimpleImmersionFragment(){

    protected   val baseViewModel by viewModels<BaseViewModel<BaseRepository>> {
             InjectorUtils.baseViewModelFactory(requireContext())
    }


    private var hasCreated = false

    abstract  val layoutId:Int
    abstract val needLogin:Boolean
    lateinit var binding:T
    //companion object val baseDialogFragment  = PublishFragment()
    val toPublishPage = MutableLiveData<Boolean>()
    var loadingState = MutableLiveData<Boolean>()
    val close = MutableLiveData<Boolean>()
    var show = false

    private fun checkLogin(){
     //   eee("checkLogin")
        if(baseViewModel.isLogin.value == false && needLogin){
            eee("checkLoginYes")
            findNavController().navigate(R.id.login_and_register)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置状态栏字体颜色为深色
        StatusBarUtil.transparencyBar(requireActivity())

        StatusBarUtil.setLightStatusBar(requireActivity(),true,true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
            super.onCreateView(inflater, container, savedInstanceState)


            checkLogin()
            binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
            binding.lifecycleOwner = this
            binding.executePendingBindings()
            return binding.root
    }

    override fun onResume() {
        super.onResume()

    }





  /*  fun show(){
        eee("调用show $baseDialogFragment")
        baseDialogFragment.show(requireActivity().supportFragmentManager,"")
    }

    fun dismiss(){
        eee("调用dismiss")
        baseDialogFragment.dismiss()
    }*/

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadingState.observe(viewLifecycleOwner,LoadingObserver(requireContext()))
        close.observe(viewLifecycleOwner){
             ToastUtil.test("关闭当前Fragment")
        }


    }





}