package com.bignerdranch.travelcommunity.ui.dynamic

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.tclib.LogUtil.eeee
import com.bignerdranch.tclib.data.db.entity.PersonDynamic
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.TCApplication.Companion.getProxy
import com.bignerdranch.travelcommunity.adapters.Coverters
import com.bignerdranch.travelcommunity.base.BaseFragment
import com.bignerdranch.travelcommunity.base.BaseViewModel
import com.bignerdranch.travelcommunity.base.Message
import com.bignerdranch.travelcommunity.base.Message.Companion.HIDE_BOTTOM_VIEW
import com.bignerdranch.travelcommunity.base.Message.Companion.SHOW_BOTTOM_VIEW
import com.bignerdranch.travelcommunity.databinding.HomepageVideoFragmentBinding
import com.bignerdranch.travelcommunity.task.TaskServer
import com.bignerdranch.travelcommunity.tcvideoplayer.TCPlayer
import com.bignerdranch.travelcommunity.ui.adapters.DynamicAdapter
import com.bignerdranch.travelcommunity.ui.adapters.VideoViewAdapter
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel.Companion.toQueryWhat
import com.bignerdranch.travelcommunity.ui.utils.VideoPageSnapHelper
import com.bignerdranch.travelcommunity.util.InjectorUtils

import com.bignerdranch.travelcommunity.videocache.CacheListener
import java.io.File

class VideoDynamicFragment(): BaseFragment<HomepageVideoFragmentBinding>(),CacheListener{

     constructor(personDynamic: PersonDynamic,
                 mContext: Context):this(){
         this.personDynamic = personDynamic
         this.mContext = mContext
     }
     private var personDynamic:PersonDynamic? = null
     private var mContext:Context? = null
    private val dataList: MutableList<PersonDynamic> = ArrayList()
    private var videoRecyclerView: RecyclerView? = null
    private var mLayoutManager: LinearLayoutManager? = null
    private var videoViewAdapater: VideoViewAdapter? = null

    private var currentPosition = 0
    private var playProgress = 0

    private var fullScreen: RelativeLayout? = null
    private var postion = -1
    private  var currentVideoUrl = ArrayList<String>()
    private var tcPlayer: TCPlayer? = null
    private var lastView:View? = null    //保存上一次View，避免重复创建
    private var hasCreated = false      //保存上一次存储状态
    private val proxy = getProxy()
    private var pageNumber = 0



    private val _viewModel by activityViewModels<PersonDynamicViewModel> {
        InjectorUtils.personDynamicViewModelFactory(requireContext())
    }

    override val layoutId: Int = R.layout.homepage_video_fragment
    override val needLogin: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentVideoUrl.clear()


         personDynamic = DynamicAdapter.curPersonDynamic

        eeee("persondyamic $personDynamic")

        _viewModel.toQueryDynamics(0,pageNumber)
        if(lastView == null) {
            super.onCreateView(inflater, container, savedInstanceState)

         lastView = binding.root
     }
        return lastView
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
      if(!hasCreated) {
          super.onViewCreated(view, savedInstanceState)
          eee("onViewCreated")
          initViews()
         hasCreated = true

      }
    }




    override fun onConfigurationChanged(newConfig: Configuration) {

        if (tcPlayer != null) {

            eee("odfsfsdfsfsnCotcPlayernfigurationChanged")
            /**
             * 在activity中监听到横竖屏变化时调用播放器的监听方法来实现播放器大小切换
             */
            tcPlayer?.onConfigurationChanged(newConfig)
            // 竖屏
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                showActionBar()
                fullScreen?.visibility = View.GONE
                fullScreen?.removeAllViews()
                videoRecyclerView?.visibility = View.VISIBLE

               /* val frameLayout = binding.videoPlayer
                frameLayout.addView(tcPlayer)*/

                val mShowFlags = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                fullScreen?.systemUiVisibility = mShowFlags
            } else {      // 横屏
                val viewGroup = tcPlayer?.parent as ViewGroup
                hideActionBar()
                viewGroup.removeAllViews()
                fullScreen?.addView(tcPlayer)
                fullScreen?.visibility = View.VISIBLE
                videoRecyclerView?.visibility = View.GONE
                val mHideFlags = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
                fullScreen?.setSystemUiVisibility(mHideFlags)
            }
        } else {
            fullScreen?.visibility = View.GONE
        }
        super.onConfigurationChanged(newConfig)
    }




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        eee("onActivityCreated")
         // subscribeObserver()
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    protected fun initViews() {

          eee("typeScreen ${BaseViewModel.typeScreen}")
        fullScreen = binding.fullScreen

        videoRecyclerView =  binding.videoRecyclerView

        mLayoutManager = object : LinearLayoutManager(requireContext()) {
         override fun calculateExtraLayoutSpace(
                state: RecyclerView.State,
                extraLayoutSpace: IntArray
            ) {
                 super.calculateExtraLayoutSpace(state, IntArray(300))
            }
        }

        videoRecyclerView?.setItemViewCacheSize(5)

        videoRecyclerView?.layoutManager = mLayoutManager
        videoViewAdapater =
            VideoViewAdapter(
               requireContext(),
                requireActivity().supportFragmentManager,
                _viewModel
            )



        videoRecyclerView?.adapter = videoViewAdapater
        VideoPageSnapHelper()
           .attachToRecyclerView(videoRecyclerView)
           .setScrollPlayListener { position ->
               if (currentPosition != position) {

                       proxy.stopLoad(currentVideoUrl?.get(currentPosition))
                       pause(currentPosition)
                       currentPosition = position
                       proxy.tryLoad(currentVideoUrl?.get(currentPosition))
                       eeee("currentUrl ${currentVideoUrl[currentPosition]}")
                       playVideo(position, playProgress)
               }
           }


    }

    private fun playVideo(position: Int, playProgress: Int) {
        findView(position)?.play(playProgress)
    }

    override  fun subscribeObserver(){

       _viewModel.personDynamics.observe(viewLifecycleOwner) { dynamics ->
           if (dynamics.isNotEmpty()) {
               // = dynamics.map { it.videoUrl }
               val dynamicList= dynamics as MutableList<PersonDynamic>

               dynamicList.remove(personDynamic)
               dynamicList.add(0,personDynamic!!)
               videoViewAdapater?.submitList(transferToUrl(dynamicList))
           }
       }

        eee(""+_viewModel.personDynamics.value)
    }

    private fun pause(position:Int){
        findView(position)?.pause()
    }

    private  fun destory(position: Int){
        findView(position)?.onDestroy()
    }

    private fun findView(position: Int): VideoViewAdapter.VideoViewHolder? {
        val view = videoRecyclerView?.findViewHolderForAdapterPosition(position)
        videoViewAdapater?.setPlayPosition(position)?.setCurrentPage(toQueryWhat)
        eee("toQueryWhat $toQueryWhat")
        return view?.let {
           it  as VideoViewAdapter.VideoViewHolder
        }
    }

     private fun destroyAll(){
         videoViewAdapater?.clearAllView()
     }

     private fun destroyItem(){

     }


    private fun currentProgress(position: Int):Int{
        val view = binding.videoRecyclerView.findViewHolderForAdapterPosition(position)
        if (view != null) {
            return (view as VideoViewAdapter.VideoViewHolder).currentProgress()
        } else return 0
    }



    /**
     * 隐藏ActionBar
     */
    private fun hideActionBar() {
        if (requireActivity().actionBar != null) requireActivity().actionBar!!.hide()
    }

    /**
     * 显示ActionBar
     */
    private fun showActionBar() {
        if (requireActivity().actionBar != null) requireActivity().actionBar!!.show()
    }



    private fun transferToUrl(personDynamics: List<PersonDynamic>) = personDynamics.filter {    //转化为代理视频地址
        Coverters.getVideoUrl(it.videoUrl).length >3
    }
        .map {
                val urls =   Coverters.getVideoUrl(it.videoUrl)
                val  url = proxy.getProxyUrl(urls)
                proxy.registerCacheListener(this,url)
                currentVideoUrl?.add(url)
             with(it) {
                PersonDynamic(
                    id, userId,  userNickName, textContent,
                    headPortraitUrl, url, imageUrls, likesCount, commentsCount, submitsTime,
                    fullWatchCount, backWatchCount, heatDegree, privateModel)
            }
   }


    override fun onPause() {
        super.onPause()
        eeee("onPause $playProgress")
        sendMsg(Message("", SHOW_BOTTOM_VIEW))
        proxy.stopLoad(currentVideoUrl?.get(currentPosition))
        pause(currentPosition)
        playProgress = currentProgress(currentPosition)
        //获取离开前的播放进度
        }


    override fun onResume() {
        super.onResume()
        eeee("onResume $playProgress")
        sendMsg(Message("", HIDE_BOTTOM_VIEW))
        if(currentVideoUrl.isNotEmpty()) {

           // tcPlayer = findView(currentPosition)?.tcPlayer

            proxy.tryLoad(currentVideoUrl?.get(currentPosition))
            playVideo(currentPosition, playProgress)



        }
        // 进入开始播放
    }

    override fun onDestroy() {
        super.onDestroy()
        eeee("onDestroy $playProgress")
      //  DataCleanManager.clearAllCache(requireContext())

         TaskServer.execute{
             destroyAll()
         }
    }


   override fun onCacheAvailable(cacheFile: File?, url: String?, percentsAvailable: Int) {
       eee("per $percentsAvailable" )
    }

     override fun subscribeUi() {

     }

     override fun subscribeListener() {

     }


     private val handler = Handler(Looper.getMainLooper())


 }