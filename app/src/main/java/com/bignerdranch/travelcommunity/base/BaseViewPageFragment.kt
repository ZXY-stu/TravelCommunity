package com.bignerdranch.travelcommunity.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bignerdranch.travelcommunity.databinding.FragmentVideoViewPageBinding
import com.bignerdranch.travelcommunity.ui.adapters.PageViewAdapter
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @author zhongxinyu
 * @date 2020/4/14
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
abstract  class BaseViewPageFragment: Fragment(){



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding =   FragmentVideoViewPageBinding.inflate(inflater,container,false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager


      //  viewPager.adapter = PageViewAdapter(this)

   /*     // Set the icon and text for each tab
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()*/
      return  binding.root
    }

    abstract fun  getTabIcon(position:Int):Int
    abstract fun getTabTitle(position: Int):String?
}