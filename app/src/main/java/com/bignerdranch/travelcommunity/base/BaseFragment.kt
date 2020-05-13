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

/**
 * @author zhongxinyu
 * @date 2020/4/9
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

abstract  class BaseFragment<T:ViewDataBinding>:Fragment(){

    protected   val baseViewModel by viewModels<BaseViewModel<BaseRepository>> {
             InjectorUtils.baseViewModelFactory(requireContext())
    }


    private var hasCreated = false

    abstract val dark:Boolean
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
    }

    /*  override fun onResume() {
        val layoutParams =  dialog?.window?.attributes
        layoutParams?.height = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT
        dialog?.window?.attributes = layoutParams
        super.onResume()
    }
    *
    *
    * */

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
        when(layoutId) {
            R.layout.fragment_mine -> {
                setDarkFont(false)
                BaseViewModel.isParentHaveSetFont = true
            }
            else -> {
               if(! BaseViewModel.isParentHaveSetFont ) setDarkFont(true)
               else  BaseViewModel.isParentHaveSetFont = false
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onPause() {
        super.onPause()
        eee("onPause")


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

  /*  override fun initImmersionBar() {
        //ImmersionBar.with(this).statusBarDarkFont(dark).init()
    }*/

    fun setDarkFont(isDarkFont:Boolean){
        ImmersionBar.with(this).statusBarDarkFont(isDarkFont).init()
    }

    fun getDimension(resId:Int) = requireContext().resources.getDimension(resId)
    fun getDimensionPixelSize(resId:Int) = requireContext().resources.getDimensionPixelSize(resId)
}