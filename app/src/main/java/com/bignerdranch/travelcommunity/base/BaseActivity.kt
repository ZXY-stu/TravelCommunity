package com.bignerdranch.travelcommunity.base

/**
 * @author zhongxinyu
 * @date 2020/4/4
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.tclib.data.repository.BaseRepository
import com.bignerdranch.tclib.utils.DeviceUtils
import com.bignerdranch.travelcommunity.ui.utils.AndroidWorkaround


import com.bignerdranch.travelcommunity.util.InjectorUtils
import com.gyf.immersionbar.ImmersionBar

/*Redeclaration*/
abstract class BaseActivity<T:ViewDataBinding> : AppCompatActivity()
  {


    abstract val layoutId:Int
    lateinit var binding:T
    var loadingState = MutableLiveData<Boolean>()


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = DataBindingUtil.setContentView(this,layoutId)
        binding.lifecycleOwner = this
    }


      override fun onDestroy() {
          super.onDestroy()

      }



}