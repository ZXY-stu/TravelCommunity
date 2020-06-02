package com.bignerdranch.travelcommunity.ui.dynamic

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.tclib.data.db.entity.PersonDynamic
import com.bignerdranch.tclib.utils.DeviceUtils
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.adapters.Coverters
import com.bignerdranch.travelcommunity.base.BaseDialogFragment
import com.bignerdranch.travelcommunity.databinding.DynamicImagestyleUserpageBinding
import com.bignerdranch.travelcommunity.task.TaskServer
import com.bignerdranch.travelcommunity.tcvideoplayer.TCPlayer
import com.bignerdranch.travelcommunity.ui.HomePageActivity
import com.bignerdranch.travelcommunity.ui.adapters.CommentsAdapter
import com.bignerdranch.travelcommunity.ui.adapters.PictureAdapter
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.ui.user.FriendFragment
import com.bignerdranch.travelcommunity.ui.user.InputDialog
import com.bignerdranch.travelcommunity.ui.utils.VideoPageSnapHelper
import java.util.*

/**
 * @author zhongxinyu
 * @date 2020/5/10
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class ImageDynamicFragment(
    override val layoutId: Int = R.layout.dynamic_imagestyle_userpage,
    override val needLogin: Boolean = false,
    override val themeResId: Int = R.style.DialogFullScreen_Right,
    val _viewModel:PersonDynamicViewModel,
    val mPersonDynamic: PersonDynamic,
    val activity:Activity
) :BaseDialogFragment<DynamicImagestyleUserpageBinding>(){

    private val adapter = PictureAdapter()
    private  val commentsDialog = CommentsDialog(_viewModel,dynamicId = mPersonDynamic.id)
    private  lateinit var commentsAdapter:CommentsAdapter
    private var imagesPosition = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return  binding.root
    }



    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        eee("onDismiss")

    }




   override fun subscribeListener(){
          with(binding){
              like.setOnClickListener { _viewModel.toAddLike(userId,0)  }
              toCommentsList.setOnClickListener {  binding.netScorllView.scrollTo(commentsRecycle.x.toInt(),commentsRecycle.y.toInt()) }

              comments1.setOnClickListener {
                  openInputMethod()
                  showCommentsDialog("")
              }
              comments2.setOnClickListener {
                  openInputMethod()
                  showCommentsDialog("")
              }


              topAction.setOnClickListener {
                  if(_viewModel.getUserId()  == mPersonDynamic?.userId){
                      //分享给别人
                  }
                  else _viewModel.toAddFriend(mPersonDynamic?.userId)
              }
              dismiss.setOnClickListener {  dismiss()}
              headUrl.setOnClickListener {
                  showFriendDialog()
              }
              bottomAction.setOnClickListener {
                  if(_viewModel.getUserId() == mPersonDynamic?.userId){
                      _viewModel.toDeleteDynamic(mPersonDynamic.id)
                      dismiss()
                  }
                  else {
                      // 分享给朋友
                  }
              }
              nickName.setOnClickListener { showFriendDialog() }
          }
    }



    override fun onConfigurationChanged(newConfig: Configuration) {
        eee("onConfigurationChanged")
      /*  if (tcPlayer != null) {
            /**
             * 在activity中监听到横竖屏变化时调用播放器的监听方法来实现播放器大小切换
             */
            tcPlayer?.onConfigurationChanged(newConfig)
              // 竖屏
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
               setDarkFont(true)
                showActionBar()
                fullScreen.visibility = View.GONE
                fullScreen.removeAllViews()
                val frameLayout = binding.videoPlayer
                frameLayout.addView(tcPlayer)
/*
                val mShowFlags = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                fullScreen.systemUiVisibility = mShowFlags*/
            } else {      // 横屏

               val viewGroup = tcPlayer?.parent as ViewGroup
                hideActionBar()
                viewGroup.removeAllViews()
                fullScreen.addView(tcPlayer)
                fullScreen.setBackgroundColor(R.color.black)

                fullScreen.visibility = View.VISIBLE
            /*    val mHideFlags = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
                fullScreen.setSystemUiVisibility(mHideFlags)*/
            }
        } else {
            fullScreen.visibility = View.GONE
        }*/
        super.onConfigurationChanged(newConfig)
    }

    override fun onPause() {
        super.onPause()

       eee("onPause ImageDialog")
    }





    override  fun subscribeUi(){
        binding.viewModel = _viewModel
        commentsAdapter = CommentsAdapter(_viewModel = _viewModel,fragmentManager = (activity as HomePageActivity).supportFragmentManager,
            fragment = this
        ).setCommentsLinkListener(object :CommentsAdapter.CommentsLinkListener{
            override fun linkName(name: String) {
              showCommentsDialog(name)
            }
        })
        setFirstFriendInfo(_viewModel,mPersonDynamic.id)  // 第一次初始化friend信息
        with(binding){
             imageMatrix.adapter = adapter
            personDynamic = mPersonDynamic
            commentsRecycle.adapter = commentsAdapter
            VideoPageSnapHelper().attachToRecyclerView(imageMatrix).setScrollPlayListener {
                imagesPosition = it+1
            }

            imageMatrix.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                position = "${imagesPosition}/${imageMatrix.layoutManager?.itemCount}"
                showDeley(imagePosition,3500)
            }







        }

        adapter.submitList(Coverters.getUrlList(mPersonDynamic?.imageUrls!!))

    }


     fun showDeley(textView: TextView,deley:Long){
         val time = Timer()
         textView.visibility = View.VISIBLE
         time.schedule(object :TimerTask(){
             override fun run() {
                 TaskServer.ktxRunOnUi {
                     textView.visibility = View.GONE
                 }
             }
         },deley)
     }

    override   fun subscribeObserver(){
       _viewModel.commentsMsgLocal.observe(viewLifecycleOwner){
              commentsAdapter.submitList(it)
       }
    }

    fun showFriendDialog(){
            FriendFragment(_viewModel = _viewModel)
            .setFriendId(userId!!)
            .show(requireActivity().supportFragmentManager,"DynamicDetails")
             onPause()
    }

    fun showCommentsDialog(text:String){

        InputDialog(textContent = text).show(requireActivity().supportFragmentManager,"")
    }
}