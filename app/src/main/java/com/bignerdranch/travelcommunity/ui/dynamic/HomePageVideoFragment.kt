package com.bignerdranch.travelcommunity.ui.dynamic

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.LogUtil.ee
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.tclib.data.db.entity.PersonDynamic
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.TCApplication.Companion.getProxy
import com.bignerdranch.travelcommunity.base.BaseFragment
import com.bignerdranch.travelcommunity.databinding.HomepageVideoFragmentBinding
import com.bignerdranch.travelcommunity.tcvideoplayer.TCPlayer
import com.bignerdranch.travelcommunity.ui.adapters.VideoViewAdapter
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel.Companion.toQueryWhat
import com.bignerdranch.travelcommunity.ui.utils.VideoPageSnapHelper
import com.bignerdranch.travelcommunity.util.DataCleanManager
import com.bignerdranch.travelcommunity.util.InjectorUtils

import com.bignerdranch.travelcommunity.videocache.CacheListener
import java.io.File

class HomePageVideoFragment : BaseFragment<HomepageVideoFragmentBinding>(),CacheListener{
    private val dataList: MutableList<PersonDynamic> = ArrayList()
    private var videoRecyclerView: RecyclerView? = null
    private var mLayoutManager: LinearLayoutManager? = null
    private var videoViewAdapater: VideoViewAdapter? = null

    private var currentPosition = 0
    private var playProgress = 0

    private val fullScreen: RelativeLayout? = null
    private var postion = -1
    private  var currentVideoUrl:List<String>? = null
    private var tcPlayer: TCPlayer? = null
    private var lastView:View? = null    //保存上一次View，避免重复创建
    private var hasCreated = false      //保存上一次存储状态
    private val proxy = getProxy()

    private val _viewModel by viewModels<PersonDynamicViewModel> {
        InjectorUtils.personDynamicViewModelFactory(requireContext())
    }

    override val dark: Boolean = false
    override val layoutId: Int = R.layout.homepage_video_fragment
    override val needLogin: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        if(lastView == null) {
            super.onCreateView(inflater, container, savedInstanceState)
         binding = HomepageVideoFragmentBinding.inflate(inflater, container, false)
         subscribeObserver()
         binding.executePendingBindings()
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
          initViews(view)
         hasCreated = true

      }
    }



    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        eee("onActivityCreated")
         // subscribeObserver()
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    protected fun initViews(contentView: View?) {
        videoRecyclerView =  binding.videoRecyclerView
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
                   playVideo(position, playProgress)
               }
           }
        initListeners()
    }

    private fun playVideo(position: Int, playProgress: Int) {
        findView(position)?.play(playProgress)
    }

    private  fun subscribeObserver(){

       _viewModel.personDynamics.observe(viewLifecycleOwner) {
             dynamics->
             eee("it${dynamics.size}")
              currentVideoUrl = dynamics.map { it.videoUrl }
              videoViewAdapater?.submitList(transferToUrl(dynamics))
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

    private fun currentProgress(position: Int):Int{
        val view = binding.videoRecyclerView.findViewHolderForAdapterPosition(position)
        if (view != null) {
            return (view as VideoViewAdapter.VideoViewHolder).currentProgress()
        } else return 0
    }

    protected fun initListeners() {

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

    /**
     * 添加测试数据
     * @return
     */
    private fun setData() {
        dataList.clear()
        dataList.add(PersonDynamic(videoUrl = "http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4"))
        dataList.add(PersonDynamic(videoUrl = "http://vjs.zencdn.net/v/oceans.mp4"))
        dataList.add(PersonDynamic(videoUrl = "https://media.w3.org/2010/05/sintel/trailer.mp4"))
        dataList.add(PersonDynamic(videoUrl = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))

        currentVideoUrl = dataList.map { it.videoUrl }
    }


    private fun transferToUrl(personDynamics: List<PersonDynamic>) = personDynamics.map {
            val  url =  if(it.videoUrl.contentEquals("http"))
                         proxy.getProxyUrl(it.videoUrl)  else it.videoUrl
            // proxy.registerCacheListener(this,url)
             with(it) {
                PersonDynamic(
                    id, userId,  userNickName, textContent,
                    headPortraitUrl, url, imageUrls, likesCount, commentsCount, submitsTime,
                    location, fullWatchCount, backWatchCount, heatDegree, privateModel)
            }
   }


    override fun onPause() {
        super.onPause()
        eee("onPause $playProgress")
        proxy.stopLoad(currentVideoUrl?.get(currentPosition))
        pause(currentPosition)
        playProgress = currentProgress(currentPosition)
        //获取离开前的播放进度
        }


    override fun onResume() {
        super.onResume()
        eee("HomePageVideoFragment")
        if(currentVideoUrl != null) {
            proxy.tryLoad(currentVideoUrl?.get(currentPosition))
            playVideo(currentPosition, playProgress)
        }
        // 进入开始播放
    }

    override fun onDestroy() {
        super.onDestroy()
        eee("onDestroy")
      //  DataCleanManager.clearAllCache(requireContext())
        destory(currentPosition)
    }


   override fun onCacheAvailable(cacheFile: File?, url: String?, percentsAvailable: Int) {
       eee("per $percentsAvailable" )
    }


}