package com.bignerdranch.travelcommunity.ui.user

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.GravityCompat
import androidx.fragment.app.activityViewModels
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
import com.bignerdranch.travelcommunity.base.BaseViewModel
import com.bignerdranch.travelcommunity.ui.dynamic.HomePageDynamic
import com.bignerdranch.travelcommunity.ui.dynamic.HomePageVideoFragment
import com.bignerdranch.travelcommunity.ui.utils.StatusBarUtil
import com.bignerdranch.travelcommunity.util.DataCleanManager
import com.bignerdranch.travelcommunity.util.ToastUtil
import com.google.android.material.appbar.CollapsingToolbarLayout
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
class UserFragment() : BaseFragment<FragmentMineBinding>() {

    private val _viewModel by activityViewModels<UserViewModel> {
        InjectorUtils.userViewModelFactory(requireContext())
    }
    override val needLogin: Boolean = true
    override val layoutId: Int  = R.layout.fragment_mine
    override val dark: Boolean = false
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
        subscribeUi() //
        setItemClickAction(binding.menuNav)   //处理菜单事件

        return    binding.root
    }

    private fun subscribeUi(){

    }
    private  fun subscribeObserve( inflater: LayoutInflater){
        binding.viewModel = _viewModel


        _viewModel.localUser.observe(viewLifecycleOwner){
            user->
           //user_head_layout 未设置
          user?.let {

              binding.user = user
          }

         }



        /*_viewModel.isLogin.observe(viewLifecycleOwner){
           subscribeLogin(it)
        }*/

        _viewModel.toOpenUserMenu.observe(viewLifecycleOwner){
             binding.userPageMenu.openDrawer(GravityCompat.END)
        }






    }

    private fun subscribeLogin(isLogin:Boolean) {
        if (!isLogin) {
            findNavController().navigate(R.id.login_and_register)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
        }
        return  true
    }





    private fun subscribeViewPage(viewPager: ViewPager2,tabLayout:TabLayout){
        viewPager.adapter = PageViewAdapter(this).build(
            mapOf(
                MY_FOCUSE to { HomePageDynamic() },
                SYSTEM_RECOMMAND to { HomePageVideoFragment() }
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
                  R.id.clean -> DataCleanManager.clearAllCache(requireContext())
                  R.id.logout -> {
                      ToastUtil.test("用户注销")
                      _viewModel.toLogout()
                      clearAndToHome(requireContext())
                  }
                  else -> throw IllegalStateException()
              }
              true
          }


    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
      eee("UserFragment ${   BaseViewModel.isParentHaveSetFont}")
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
