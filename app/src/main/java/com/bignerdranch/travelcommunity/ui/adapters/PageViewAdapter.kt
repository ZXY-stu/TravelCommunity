package com.bignerdranch.travelcommunity.ui.adapters

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bignerdranch.tclib.LogUtil


/**
 * @author zhongxinyu
 * @date 2020/4/6
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class PageViewAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */

    private lateinit  var _tabFragmentsCreators: MutableMap<Int, () -> Fragment>

    fun addElement(key:Int,value:()->Fragment){
         _tabFragmentsCreators.put(key,value)
    }

    fun addElements(tabs:MutableMap<Int,()->Fragment>){
        _tabFragmentsCreators.putAll(tabs)
    }

    fun build(tabFragmentCreators:MutableMap<Int,()->Fragment>):PageViewAdapter{
        _tabFragmentsCreators = tabFragmentCreators
        return this
    }

    override fun getItemCount() = _tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        LogUtil.e("zhixingle $position")
        return _tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }




}