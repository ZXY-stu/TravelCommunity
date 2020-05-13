package com.bignerdranch.travelcommunity.base

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
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

    protected   val baseViewModel by viewModels<BaseViewModel<BaseRepository>> {
        InjectorUtils.baseViewModelFactory(requireContext())
    }

    private lateinit var inputMethodManager: InputMethodManager
    private var hasCreated = false
    abstract  val layoutId:Int
    abstract val needLogin:Boolean
    lateinit var binding:T
    protected var windowHeight:Double = -0.1
    abstract  val  themeResId: Int
    private var isShow = false
    private var lastView:View? = null
    protected var userId:Int? = -1
    protected var dynamicId:Int? = -1
    protected var friendAccount:String=""
    private var deviceHeight  = 0


    private fun checkLogin(){
     //   LogUtil.eee("checkLogin")
        if(baseViewModel.isLogin.value == false && needLogin){
            LogUtil.eee("checkLoginYes")
            findNavController().navigate(R.id.login_and_register)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置状态栏字体颜色为深色
        deviceHeight = DeviceUtils.deviceHeight(requireContext())

        setStyle(STYLE_NO_TITLE,themeResId)

    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
          super.onCreateView(inflater, container, savedInstanceState)

          checkLogin()
          dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE);
          inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
          binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
          binding.lifecycleOwner = this
          binding.executePendingBindings()


         lastView = binding.root

         return lastView
    }


    override fun onResume() {
        super.onResume()
        when(layoutId) {
            R.layout.fragment_mine ->{
                setDarkFont(false)
                eee("fragment_mine")
            }
            R.layout.input_dialog->{
                   //这个不设置状态栏的字体 ,否则会导致输入法无法顶起布局
                // 因为ImmersionBar函数内部设置了沉浸式，具体原因不太清楚
            }
            else -> setDarkFont(true)
        }

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

    }


    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val dialogWindow =  dialog.window
            val p = dialogWindow!!.attributes
            if(windowHeight>0) p.height = (windowHeight * deviceHeight).toInt()
            else  p.height =  WindowManager.LayoutParams.MATCH_PARENT
            p.width = WindowManager.LayoutParams.MATCH_PARENT
            p.gravity = Gravity.START or Gravity.BOTTOM
            dialogWindow.attributes = p
        }
    }

    /*override fun onResume() {

        if(height>0) {
            dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,height)
            dialog?.window?.setGravity(Gravity.CENTER )
        }else{
           val layoutParams =  dialog?.window?.attributes
            layoutParams?.height = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT
            dialog?.window?.attributes = layoutParams
        }
        super.onResume()
    }*/


    fun openInputMethod(){
        val time = Timer()
        time.schedule(object : TimerTask() {
            override fun run() {
                inputMethodManager.showSoftInput(requireView(), InputMethodManager.SHOW_FORCED)
                inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        },200)
    }

    fun closeInputMethod(){
        inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    fun getInputMethodStatus() = inputMethodManager.isActive

    fun setFirstFriendInfo(viewModel:PersonDynamicViewModel){
     //   authorUserId = arguments?.getInt("authorUserId")?:-1
        dynamicId = arguments?.getInt("dynamicId")?:-1
       // friendAccount = arguments?.getString("friendAccount")?:""


        //初始化第一次评论数据，将commentGroupId置为0，表示评论动态发表人
        viewModel.toSetFriendCommentsInfo(
            CommentsMsg(dynamicId = dynamicId!!,
                commentGroupId = 0,friendNickName = "",userNickName = "")
        )
    }

    fun setDarkFont(isDarkFont:Boolean){
        ImmersionBar.with(this).statusBarDarkFont(isDarkFont).init()
    }

    fun getDimension(resId:Int) = requireContext().resources.getDimension(resId)
    fun getDimensionPixelSize(resId:Int) = requireContext().resources.getDimensionPixelSize(resId)
}