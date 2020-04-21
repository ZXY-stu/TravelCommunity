package com.bignerdranch.travelcommunity.ui.user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Transformations
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2

import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseFragment
import com.bignerdranch.travelcommunity.data.db.entity.User
import com.bignerdranch.travelcommunity.databinding.FragmentMineBinding
import com.bignerdranch.travelcommunity.databinding.FragmentUserVideoBinding
import com.bignerdranch.travelcommunity.databinding.UserHeadLayoutBinding
import com.bignerdranch.travelcommunity.ui.HomePageActivity
import com.bignerdranch.travelcommunity.ui.adapters.PageViewAdapter
import com.bignerdranch.travelcommunity.ui.dynamic.VideoFragment
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel.Companion.MY_FOCUSE
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel.Companion.SYSTEM_RECOMMAND
import com.bignerdranch.travelcommunity.util.InjectorUtils
import com.bignerdranch.travelcommunity.util.LogUtil
import com.bignerdranch.travelcommunity.util.ToastUtil
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.user_head_layout.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragment() : BaseFragment<FragmentMineBinding>() {

    private val _viewModel by viewModels<UserViewModel> {
        InjectorUtils.userViewModelFactory(requireContext())
    }
    override val layoutId: Int  = R.layout.fragment_mine
    private lateinit var headView:UserHeadLayoutBinding
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
        setItemClickAction(binding.menuNav)   //处理菜单事件

        return    binding.root
    }
    private  fun subscribeObserve( inflater: LayoutInflater){
        binding.viewModel = _viewModel
        _viewModel.localUser.observe(viewLifecycleOwner){
            user->
           //user_head_layout 未设置
            binding.user = user

         }

        _viewModel.isLogin.observe(viewLifecycleOwner){
           subscribeLogin(it)
        }

        _viewModel.toOpenUserMenu.observe(viewLifecycleOwner){
             binding.userPageMenu.openDrawer(GravityCompat.END)
        }






    }

    private fun subscribeLogin(isLogin:Boolean){
        if(!isLogin)
            findNavController().navigate(R.id.login_and_register)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
        }
        return  true
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .transparentBar()
            .transparentNavigationBar()
            .transparentStatusBar()
            .navigationBarAlpha(0.0F)
            .init()
    }



    private fun subscribeViewPage(viewPager: ViewPager2,tabLayout:TabLayout){
        viewPager.adapter = PageViewAdapter(this).build(
            mapOf(
                MY_FOCUSE to { VideoFragment() },
                SYSTEM_RECOMMAND to { VideoFragment() }
            ) as MutableMap<Int, () -> Fragment>
        )

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }


    private fun setItemClickAction(view:NavigationView){
          view.setNavigationItemSelectedListener {
              when(it.itemId){
                  R.id.user_info ->  ToastUtil.test("去基本信息界面")
                  R.id.user_code -> ToastUtil.test("去我的二维码")
                  R.id.user_wallet -> ToastUtil.test("去我的钱包")
                  R.id.setting -> ToastUtil.test("去设置")
                  R.id.logout -> {
                      ToastUtil.test("用户注销")
                      _viewModel.toLogout()
                      startActivity(Intent(requireContext(),HomePageActivity::class.java))
                  }
                  else -> throw IllegalStateException()
              }
              true
          }


    }


    private fun getTabIcon(position: Int): Int {
        LogUtil.e(""+position)
        return when (position) {
            PersonDynamicViewModel.MY_FOCUSE -> R.drawable.garden_tab_selector
            PersonDynamicViewModel.SYSTEM_RECOMMAND -> R.drawable.plant_list_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
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
