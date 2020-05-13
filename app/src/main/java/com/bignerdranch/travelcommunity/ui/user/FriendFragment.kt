package com.bignerdranch.travelcommunity.ui.user

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2

import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseFragment
import com.bignerdranch.travelcommunity.databinding.FragmentMineBinding
import com.bignerdranch.travelcommunity.databinding.UserHeadLayoutBinding
import com.bignerdranch.travelcommunity.ui.adapters.PageViewAdapter
import com.bignerdranch.travelcommunity.ui.clearAndToHome
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel.Companion.MY_FOCUSE
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel.Companion.SYSTEM_RECOMMAND
import com.bignerdranch.travelcommunity.util.InjectorUtils
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.travelcommunity.base.BaseDialogFragment
import com.bignerdranch.travelcommunity.ui.dynamic.HomePageDynamic
import com.bignerdranch.travelcommunity.ui.dynamic.HomePageVideo
import com.bignerdranch.travelcommunity.ui.dynamic.HomePageVideoFragment
import com.bignerdranch.travelcommunity.util.DataCleanManager
import com.bignerdranch.travelcommunity.util.ToastUtil
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.gyf.immersionbar.ImmersionBar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FriendFragment(
                     override val themeResId: Int = R.style.DialogFullScreen_Right,
                    val _viewModel:PersonDynamicViewModel
) : BaseDialogFragment<FragmentMineBinding>() {


    override val needLogin: Boolean = false
    override val layoutId: Int  = R.layout.fragment_mine
    private lateinit var headView:UserHeadLayoutBinding
    private val frangment1 = HomePageDynamic()
    private val fragment2 = NoticeLoginFragment()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreateView(inflater, container, savedInstanceState)
        val viewPager = binding.userViewPager
        val tabLayout = binding.userTabs
        subscribeObserve(inflater)    //订阅观察者
        subscribeViewPage(viewPager,tabLayout)  //处理viewPage


        return    binding.root
    }
    private  fun subscribeObserve( inflater: LayoutInflater){
        binding.viewModel = _viewModel


        _viewModel.currentUser.observe(viewLifecycleOwner){
                user->
            //user_head_layout 未设置
            user?.let {
                eee("user"+user)
                binding.user = user
            }

        }
    }



    fun setFriendId(friendId:Int):FriendFragment{
        _viewModel._friendId = friendId
        return this
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
      eee("onDismiss")
    }


    private fun subscribeViewPage(viewPager: ViewPager2,tabLayout:TabLayout){

        binding.userPageMenu.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        viewPager.adapter = PageViewAdapter(this).build(
            mapOf(
                MY_FOCUSE to {
                    HomePageDynamic() },
                SYSTEM_RECOMMAND to { NoticeLoginFragment()}
            ) as MutableMap<Int, () -> Fragment>
        )

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }



    private fun getTabTitle(position: Int): String? {
        return when (position) {
            MY_FOCUSE ->{
                PersonDynamicViewModel.toQueryWhat = PersonDynamicViewModel.MY_FOCUSE
                getString(R.string.MyFocus)
            }
            SYSTEM_RECOMMAND -> {
                PersonDynamicViewModel.toQueryWhat = PersonDynamicViewModel.SYSTEM_RECOMMAND
                getString(R.string.Recommand)
            }
            else -> null
        }
    }
}
