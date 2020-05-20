package com.bignerdranch.travelcommunity.base

import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.tclib.data.db.entity.CommentsMsg
import com.bignerdranch.tclib.data.repository.BaseRepository
import com.bignerdranch.tclib.utils.DeviceUtils
import com.bignerdranch.travelcommunity.LoadingObserver
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.ui.user.NoticeLoginDialog
import com.bignerdranch.travelcommunity.ui.utils.StatusBarUtil
import com.bignerdranch.travelcommunity.util.InjectorUtils
import com.bignerdranch.travelcommunity.util.ToastUtil
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.components.SimpleImmersionFragment
import java.util.*

/**
 * @author zhongxinyu
 * @date 2020/5/9
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

abstract  class BaseDialogFragment<T: ViewDataBinding>: DialogFragment(){


    private lateinit var inputMethodManager: InputMethodManager
    private var hasCreated = false
    abstract  val layoutId:Int
    abstract val needLogin:Boolean
    lateinit var binding:T
    open var windowHeight:Double = -0.1
    open var  windowWidth:Double = -0.1
    abstract  val  themeResId: Int
    private var isShow = false
    private var lastView:View? = null
    protected var userId:Int? = -1
    protected var friendAccount:String=""
    private var deviceHeight  = 0
    private var deviceWidth = 0
   private lateinit var noticeLoginDialog:NoticeLoginDialog

    private fun checkLogin(){

        if(!BaseViewModel.userIsLogin && needLogin){
            LogUtil.eee("checkLoginYes")

            noticeLoginDialog.show(requireActivity().supportFragmentManager,"BaseDialogFragment")
             onPause()
            eee("checkLoginYes22")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置状态栏字体颜色为深色
        deviceHeight = DeviceUtils.deviceHeight(requireContext())
        deviceWidth = DeviceUtils.deviceWidth(requireContext())
        noticeLoginDialog = NoticeLoginDialog()
        setStyle(STYLE_NO_TITLE,themeResId)

    }

    override fun onPause() {
        super.onPause()
       eee("onPause")
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        checkLogin()
        super.onCreateView(inflater, container, savedInstanceState)
        eee("onCreateView")
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = this
        subscribeUi()
        subscribeListener()
        subscribeObserver()
        binding.executePendingBindings()
        lastView = binding.root

         return lastView
    }

    open fun subscribeUi(){}
    open fun subscribeListener(){}
    open fun subscribeObserver(){
    }

    override fun onResume() {

        super.onResume()
        eee("onResume")

            when (layoutId) {
                R.layout.fragment_mine -> {
                    //  setDarkFont(false)
                    eee("fragment_mine")
                }
                R.layout.input_dialog,R.layout.user_info_editor -> {
                    //这个不设置状态栏的字体 ,否则会导致输入法无法顶起布局
                    // 因为ImmersionBar函数内部设置了沉浸式，具体原因不太清楚
                }
                else -> setDarkFont(true)
            }


    }


    override fun onStart() {

        super.onStart()
eee("onStart")

            val dialog = dialog
            if (dialog != null) {
                val dialogWindow = dialog.window
                val p = dialogWindow!!.attributes
                if (windowHeight > 0) p.height = (windowHeight * deviceHeight).toInt()
                else p.height = WindowManager.LayoutParams.MATCH_PARENT
                if(windowWidth>0) {
                    p.width = (windowWidth * deviceWidth).toInt()
                    p.gravity = Gravity.CENTER
                }
                else{
                    p.width = WindowManager.LayoutParams.MATCH_PARENT
                    p.gravity = Gravity.START or Gravity.BOTTOM
                }

                dialogWindow.attributes = p
            }
        }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }


    fun openInputMethod() {
        checkLogin()
        if (BaseViewModel.userIsLogin) {
            val time = Timer()

            time.schedule(object : TimerTask() {
                override fun run() {
                    inputMethodManager.showSoftInput(requireView(), InputMethodManager.SHOW_FORCED)
                    inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }, 200)
        }
    }

    fun closeInputMethod(){
        inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    fun getInputMethodStatus() = inputMethodManager.isActive

    fun setFirstFriendInfo(viewModel:PersonDynamicViewModel,dynamicId:Int){
        //初始化第一次评论数据，将commentGroupId置为0，表示评论动态发表人
        viewModel.toSetFriendCommentsInfo(
            CommentsMsg(commentGroupId = 0,dynamicId = dynamicId,friendNickName = "",userNickName = "")
        )
    }

    fun setDarkFont(isDarkFont:Boolean){
        ImmersionBar.with(this)
            .fitsSystemWindows(false).statusBarDarkFont(isDarkFont).init()
    }

    fun getDimension(resId:Int) = requireContext().resources.getDimension(resId)
    fun getDimensionPixelSize(resId:Int) = requireContext().resources.getDimensionPixelSize(resId)
}