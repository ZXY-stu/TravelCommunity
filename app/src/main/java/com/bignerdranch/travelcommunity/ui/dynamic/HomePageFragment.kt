package com.bignerdranch.travelcommunity.ui.dynamic

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseFragment
import com.bignerdranch.travelcommunity.databinding.FragmentVideoViewPageBinding
import com.bignerdranch.travelcommunity.tcvideoplayer.TCPlayer
import com.bignerdranch.travelcommunity.ui.RecyclerViewForViewPage2
import com.bignerdranch.travelcommunity.ui.adapters.PageViewAdapter
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel.Companion.MY_FOCUSE
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel.Companion.SYSTEM_RECOMMAND
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel.Companion.toQueryWhat
import com.bignerdranch.travelcommunity.ui.user.NoticeLoginDialog
import com.bignerdranch.travelcommunity.util.InjectorUtils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


/**
 * A simple [Fragment] subclass.
 * Use the [HomePageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class HomePageFragment: BaseFragment<FragmentVideoViewPageBinding>() {


    override val layoutId: Int = R.layout.fragment_video_view_page
    override val needLogin: Boolean = false
    private lateinit var tabLayout:TabLayout
    private lateinit var viewPager:ViewPager2
    private lateinit var fragment1:HomePageDynamic
    private lateinit var fragment2:HomePageDynamic
    private lateinit var tcPlayer:TCPlayer

     private val _viewModel by activityViewModels<PersonDynamicViewModel> {
         InjectorUtils.personDynamicViewModelFactory(requireContext())
     }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }


    private fun initVideoView(){
        //binding.video.visibility = View.VISIBLE
    //    binding.video.addView(tcPlayer)
      //  tcPlayer?.play("http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4")
    }

    private fun init(){
        tcPlayer = TCPlayer(requireActivity())
        tcPlayer
            .setDoubleClickListener {  }
            .onPrepared { tcPlayer?.start() }
            .onComplete { tcPlayer?.play() }
            .setNetChangeListener(true)
            .setLive(false)
            .setSupportGesture(false)
            .setShowTopControl(false)
            .setFullScreenOnly(false)
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
       if(newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
       /*     binding.viewPageCoordinatorLayout.fitsSystemWindows = true
           binding.userToolbar.visibility = View.VISIBLE
           binding.appBarLayout.visibility = View.VISIBLE
           binding.appBarLayout.fitsSystemWindows = true
           binding.tabs.visibility = View.VISIBLE*/
       }
        else //binding.viewPageCoordinatorLayout.visibility = View.GONE
       {
       /*   binding.userToolbar.visibility = View.GONE
           binding.appBarLayout.visibility = View.GONE
           binding.appBarLayout.fitsSystemWindows = false
           binding.tabs.visibility = View.GONE*/
       }
        super.onConfigurationChanged(newConfig)
    }

    override  fun subscribeUi(){
        tabLayout = binding.tabs
        viewPager = binding.viewPager
        fragment1 = HomePageDynamic().setScorllXListener(object :RecyclerViewForViewPage2.EnableScorllXListener{
            override fun enable(flag: Boolean) {
                   viewPager.isUserInputEnabled = flag
            }
        })

        fragment2 = HomePageDynamic().setScorllXListener(object :RecyclerViewForViewPage2.EnableScorllXListener{
            override fun enable(flag: Boolean) {
                viewPager.isUserInputEnabled = flag
            }
        })



            viewPager.adapter = PageViewAdapter(this).build(mapOf(
            SYSTEM_RECOMMAND to { fragment1 },
            MY_FOCUSE to { fragment2 }
        ) as MutableMap<Int, () -> Fragment>)
        // Set the icon and text for each tab
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()




        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                eee("distX $position  distY $position")
                when(position) {
                    MY_FOCUSE -> {
                        toQueryWhat = MY_FOCUSE
                        LogUtil.e("$toQueryWhat  + $MY_FOCUSE")
                    }
                    SYSTEM_RECOMMAND -> {
                        toQueryWhat = SYSTEM_RECOMMAND
                        LogUtil.e("$toQueryWhat  + $SYSTEM_RECOMMAND")
                    }
                }
            }
        })
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            MY_FOCUSE ->{
                getString(R.string.MyFocus)
            }
            SYSTEM_RECOMMAND -> {
                getString(R.string.Recommand)
            }
            else -> null
        }
    }

    override fun subscribeListener() {

    }

    override fun subscribeObserver() {

    }
}

