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
    abstract val windowHeight:Double
    abstract  val  themeResId: Int
    private var isShow = false
    private var lastView:View? = null
    protected var userId:Int? = -1
    protected var dynamicId:Int? = -1
    protected var authorUserId:Int? = -1
    protected var friendAccount:String=""



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
        StatusBarUtil.setLightStatusBar(requireActivity(),true,true)
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

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        isShow = false
    }



    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val dialogWindow =  dialog.window
            val p = dialogWindow!!.attributes
            val m = dialogWindow.windowManager
            val d = m.defaultDisplay
            dialogWindow.attributes = p

            p.height = ((d.height + DeviceUtils.dp2px(requireContext(),
               requireContext().resources.getDimension(R.dimen.toolbarHeight)
            )) * windowHeight).toInt()

            p.width = d.width
            eee("height"+p.height + "width" + p.width )
            p.gravity = Gravity.START or Gravity.BOTTOM
            dialogWindow.attributes = p
        }
    }

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

}