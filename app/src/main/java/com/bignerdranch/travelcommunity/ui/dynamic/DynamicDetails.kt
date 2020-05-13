package com.bignerdranch.travelcommunity.ui.dynamic

import android.app.StatusBarManager
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.lifecycle.observe
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.tclib.utils.DeviceUtils
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseDialogFragment
import com.bignerdranch.travelcommunity.base.BaseViewModel
import com.bignerdranch.travelcommunity.base.Check
import com.bignerdranch.travelcommunity.databinding.DynamicStyleUserpageBinding
import com.bignerdranch.travelcommunity.ui.adapters.PictureAdapter
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.ui.user.FriendFragment
import com.bignerdranch.travelcommunity.ui.user.InputDialog
import com.bignerdranch.travelcommunity.ui.user.UserFragment
import com.bignerdranch.travelcommunity.ui.utils.VideoPageSnapHelper
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.hideStatusBar
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * @author zhongxinyu
 * @date 2020/5/10
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class DynamicDetails(
    override val layoutId: Int = R.layout.dynamic_style_userpage,
    override val needLogin: Boolean = true,
    override val themeResId: Int = R.style.DialogFullScreen_Right,
    val _viewModel:PersonDynamicViewModel
) :BaseDialogFragment<DynamicStyleUserpageBinding>(){


    val adapter = PictureAdapter()
    val commentsDialog = CommentsDialog(_viewModel)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        subscribeUi()
        subscribeListener()
        subscribeObserver()
        return  binding.root
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        eee("onDismiss")
    }




    fun subscribeListener(){
          with(binding){
              like.setOnClickListener { _viewModel.toAddLike(userId,0)  }
              comments.setOnClickListener {  showCommentsDialog() }
              toComments.setOnClickListener {

                     InputDialog().show(requireActivity().supportFragmentManager,"")
              }

              //设置NetedScorllView为当前设备的高度，就可以解决NetedScorllView下方的View被顶到屏幕底下
             val layoutParams = netScorllView.layoutParams
     //    if(Check.isSystemUiVisible(requireActivity().window)?.get(1)!!) {

         //     layoutParams.height = ((DeviceUtils.deviceHeight(context)) -
          //           getDimension(R.dimen.action_pic_size) - getDimension(R.dimen.action_padding)).toInt()

        //  }else{
             layoutParams.height = ((DeviceUtils.deviceHeight(context)) ).toInt()
       //  }
            netScorllView.layoutParams = layoutParams
              focus.setOnClickListener { _viewModel.toAddFriend(userId!!)  }
              dismiss.setOnClickListener {  dismiss()}
              headUrl.setOnClickListener {
                  showFriendDialog()
              }
              share.setOnClickListener {  }
                  nickName.setOnClickListener {
                  showFriendDialog()
              }
          }
    }

    fun subscribeUi(){
        with(binding){

             imageMatrix.adapter = adapter
            VideoPageSnapHelper().attachToRecyclerView(imageMatrix)
             setFirstFriendInfo(_viewModel)  // 第一次初始化friend信息


        }
    }


    fun subscribeObserver(){
       with(_viewModel){
            personDynamics.observe(viewLifecycleOwner){
                adapter.submitList(it.map { it.imageUrls })
            }
       }
    }

    fun showFriendDialog(){
            FriendFragment(_viewModel = _viewModel)
            .setFriendId(userId!!)
            .show(requireActivity().supportFragmentManager,"DynamicDetails")
    }

    fun showCommentsDialog(){
         val bundle = bundleOf("dynamicId" to dynamicId)
        commentsDialog.arguments?.putAll(bundle)
        commentsDialog.show(requireActivity().supportFragmentManager,"DynamicDetails")
    }
}