package com.bignerdranch.travelcommunity.ui.dynamic

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2

import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseFragment
import com.bignerdranch.travelcommunity.databinding.FragmentVideoViewPageBinding
import com.bignerdranch.travelcommunity.ui.adapters.PageViewAdapter
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel.Companion.MY_FOCUSE
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel.Companion.SYSTEM_RECOMMAND
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel.Companion.USER_DYNAMIC
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel.Companion.toQueryWhat
import com.bignerdranch.travelcommunity.ui.listener.TCRecycleViewListener
import com.bignerdranch.travelcommunity.ui.user.LoginFragment
import com.bignerdranch.travelcommunity.util.LogUtil
import com.bignerdranch.travelcommunity.util.ToastUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.components.SimpleImmersionFragment


/**
 * A simple [Fragment] subclass.
 * Use the [HomePageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class HomePageFragment : BaseFragment<FragmentVideoViewPageBinding>() {
    override val layoutId: Int = R.layout.fragment_video_view_page

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        subscribeViewPage()   //初始化viewPage

        return binding.root
    }




    private  fun subscribeViewPage(){
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = PageViewAdapter(this).build(mapOf(
            SYSTEM_RECOMMAND to {VideoFragment() },
            MY_FOCUSE to { UserVideoFragment()}
        ) as MutableMap<Int, () -> Fragment>)

        // Set the icon and text for each tab
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
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

       // viewPager.setCurrentItem(SYSTEM_RECOMMAND)


    }
    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .transparentStatusBar()
            .transparentNavigationBar()
            .transparentBar()
            .barAlpha(0.0f)
            .init()
    }

    private fun getTabIcon(position: Int): Int {
        LogUtil.e(""+position)
        return when (position) {
            MY_FOCUSE -> R.drawable.garden_tab_selector
            SYSTEM_RECOMMAND -> R.drawable.plant_list_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
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
}

