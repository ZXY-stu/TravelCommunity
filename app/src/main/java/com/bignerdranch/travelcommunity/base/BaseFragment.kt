package com.bignerdranch.travelcommunity.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import com.bignerdranch.travelcommunity.LoadingObserver
import com.bignerdranch.travelcommunity.data.repository.BaseRepository
import com.bignerdranch.travelcommunity.util.InjectorUtils
import com.bignerdranch.travelcommunity.util.LogUtil
import com.bignerdranch.travelcommunity.util.ToastUtil
import com.gyf.immersionbar.components.SimpleImmersionFragment

/**
 * @author zhongxinyu
 * @date 2020/4/9
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

abstract  class BaseFragment<T:ViewDataBinding>:SimpleImmersionFragment(){
    protected val baseViewModel  by viewModels<BaseViewModel<BaseRepository>> {
        InjectorUtils.baseViewModelFactory(requireContext())
    }


    abstract  val layoutId:Int
    lateinit var binding:T
    var loadingState = MutableLiveData<Boolean>()
    val close = MutableLiveData<Boolean>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,layoutId,container,false)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadingState.observe(viewLifecycleOwner,LoadingObserver(requireContext()))
        close.observe(viewLifecycleOwner){
             ToastUtil.test("关闭当前Fragment")
        }
    }


}