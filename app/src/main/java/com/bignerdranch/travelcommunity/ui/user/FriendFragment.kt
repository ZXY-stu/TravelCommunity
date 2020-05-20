package com.bignerdranch.travelcommunity.ui.user

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.observe
import androidx.viewpager2.widget.ViewPager2

import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.databinding.FragmentMineBinding
import com.bignerdranch.travelcommunity.databinding.UserHeadLayoutBinding
import com.bignerdranch.travelcommunity.ui.adapters.PageViewAdapter
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel.Companion.MY_FOCUSE
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel.Companion.SYSTEM_RECOMMAND
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.travelcommunity.base.BaseDialogFragment
import com.bignerdranch.travelcommunity.ui.dynamic.HomePageDynamic
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel.Companion.USER_DYNAMIC
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel.Companion.USER_LIKE
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel.Companion.USER_WORK
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel.Companion.toQueryWhat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class FriendFragment(
                     override val themeResId: Int = R.style.DialogFullScreen_Right,
                    val _viewModel:PersonDynamicViewModel
) : BaseDialogFragment<FragmentMineBinding>() {

    override val needLogin: Boolean = false
    override val layoutId: Int  = R.layout.fragment_mine
    private lateinit var headView:UserHeadLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return    binding.root
    }


    fun setFriendId(friendId:Int):FriendFragment{
        _viewModel._friendId = friendId
        return this
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
      eee("onDismiss")
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            0 ->{
                eee("position $position")
                toQueryWhat = USER_WORK
                getString(R.string.work)
            }
            1 -> {
                toQueryWhat = USER_DYNAMIC
                getString(R.string.dynamic)
            }
            2 ->{
                toQueryWhat = USER_LIKE
                getString(R.string.like)
            }
            else -> null
        }
    }

    override fun subscribeUi() {
            binding.viewModel = _viewModel
            binding.userPageMenu.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            binding.userViewPager.adapter = PageViewAdapter(this).build(
                mapOf(
                    0 to { UserPageDynamic() },
                    1 to {UserPageDynamic()},
                    2 to {UserPageDynamic()}
                ) as MutableMap<Int, () -> Fragment>
            )



            TabLayoutMediator(binding.userTabs, binding.userViewPager) { tab, position ->
                tab.text = getTabTitle(position)
            }.attach()

    }

    override fun subscribeListener() {

    }

    override fun subscribeObserver() {
        _viewModel.currentUser.observe(viewLifecycleOwner){
                user->
            //user_head_layout 未设置
            user?.let {
                binding.user = user
            }

        }
    }
}
