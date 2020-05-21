package com.bignerdranch.travelcommunity.base

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
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
import com.bignerdranch.travelcommunity.ui.utils.AndroidWorkaround
import com.bignerdranch.travelcommunity.util.ToastUtil
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.components.SimpleImmersionFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author zhongxinyu
 * @date 2020/4/9
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

abstract  class BaseFragment<T:ViewDataBinding>:Fragment(){
    private val baseViewModel by viewModels<BaseViewModel<BaseRepository>> {
             InjectorUtils.baseViewModelFactory(requireContext())
    }
    private var hasCreated = false

    abstract  val layoutId:Int
    abstract val needLogin:Boolean

    open fun subscribeUi(){}
    open fun subscribeListener(){}
    open fun subscribeObserver(){}
    lateinit var binding:T

    var loadingState = MutableLiveData<Boolean>()
    val close = MutableLiveData<Boolean>()
    var show = false

    private fun checkLogin(){
        if(baseViewModel.isLogin.value == false && needLogin){
            findNavController().navigate(R.id.login_and_register)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
           super.onCreateView(inflater, container, savedInstanceState)
            checkLogin()
            binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
            binding.lifecycleOwner = this
            subscribeUi()
            subscribeObserver()
            subscribeListener()
            binding.executePendingBindings()
            return binding.root
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    fun sendMsg(msg: Message){
        EventBus.getDefault().post(msg)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReceiveMessage(msg:Message){
        when(msg.type){
            Message.FULL_SCREEN ->  {
                eee("收到  FULL_SCREEN")
                BaseViewModel.typeScreen = msg.type
            }
            Message.NOT_FULL_SCREEN -> {
                eee("收到 NOT_FULL_SCREEN")
                BaseViewModel.typeScreen = msg.type
            }
        }

    }


    override fun onResume() {
        super.onResume()
        when(layoutId) {
            R.layout.fragment_mine -> {
                setDarkFont(false,false)
                BaseViewModel.isParentHaveSetFont = true
            }
            R.layout.homepage_video_fragment->{
                setDarkFont(false,true)
            }
            else -> {
               if(! BaseViewModel.isParentHaveSetFont ) setDarkFont(true,false)
               else  BaseViewModel.isParentHaveSetFont = false
            }
        }
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadingState.observe(viewLifecycleOwner,LoadingObserver(requireContext()))
        close.observe(viewLifecycleOwner){
             ToastUtil.test("关闭当前Fragment")
        }

    }

    fun setDarkFont(isDarkFont:Boolean,isDarkNavigator:Boolean){

        if(isDarkNavigator){
            ImmersionBar.with(this).statusBarDarkFont(isDarkFont)
                .navigationBarColor(R.color.black)
                .init()
        }else{
            ImmersionBar.with(this).statusBarDarkFont(isDarkFont)
                .navigationBarColor(R.color.white)
                .init()
        }
    }



    fun getDimension(resId:Int) = requireContext().resources.getDimension(resId)
    fun getDimensionPixelSize(resId:Int) = requireContext().resources.getDimensionPixelSize(resId)
}