package com.bignerdranch.travelcommunity.base

/**
 * @author zhongxinyu
 * @date 2020/4/4
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
import android.annotation.SuppressLint
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.travelcommunity.LoadingObserver
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.data.repository.BaseRepository
import com.bignerdranch.travelcommunity.util.InjectorUtils
import com.bignerdranch.travelcommunity.util.LogUtil
import com.bignerdranch.travelcommunity.util.NetUtil
import com.bignerdranch.travelcommunity.util.ToastUtil

/*Redeclaration*/
abstract class BaseActivity<T:ViewDataBinding> : AppCompatActivity()
  {
    protected val baseViewModel by viewModels<BaseViewModel<BaseRepository>> {
        InjectorUtils.baseViewModelFactory(this)
    }

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

}