package com.bignerdranch.travelcommunity.ui.user

import android.app.Activity
import android.content.Intent
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.core.view.GravityCompat
import androidx.core.view.drawToBitmap
import androidx.databinding.DataBindingUtil
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
import com.bignerdranch.travelcommunity.base.AlertDialogFragment
import com.bignerdranch.travelcommunity.base.BaseViewModel
import com.bignerdranch.travelcommunity.ui.dynamic.HomePageDynamic
import com.bignerdranch.travelcommunity.ui.dynamic.ImageEditorDialog
import com.bignerdranch.travelcommunity.ui.dynamic.OPEN_ALBUM
import com.bignerdranch.travelcommunity.ui.dynamic.VideoDynamicFragment
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel.Companion.USER_DYNAMIC
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel.Companion.USER_LIKE
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel.Companion.USER_WORK
import com.bignerdranch.travelcommunity.ui.listener.AppBarStateChangeListener
import com.bignerdranch.travelcommunity.ui.user.userProfile.UserInfoEditorDialog
import com.bignerdranch.travelcommunity.ui.user.userProfile.UserInfoEditorFragment
import com.bignerdranch.travelcommunity.ui.utils.ImageEditor
import com.bignerdranch.travelcommunity.ui.utils.PermissionAsk
import com.bignerdranch.travelcommunity.ui.utils.Utils
import com.bignerdranch.travelcommunity.ui.utils.openAlbum
import com.bignerdranch.travelcommunity.util.DataCleanManager
import com.bignerdranch.travelcommunity.util.ToastUtil
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zhihu.matisse.Matisse
import kotlinx.android.synthetic.main.user_head_layout.view.*


const val HEAD = 0
const val BACKGROUND = 1
class UserFragment() : BaseFragment<FragmentMineBinding>() {

    private val _viewModel by activityViewModels<UserViewModel> {
        InjectorUtils.userViewModelFactory(requireContext())
    }

    override val needLogin: Boolean = true
    override val layoutId: Int  = R.layout.fragment_mine
    private lateinit var headView:UserHeadLayoutBinding
    private val   max = Matrix()
    private var scale = 0.8f
    private val fileList = ArrayList<Uri>()
    private lateinit var  userInfoEditorFragment: UserInfoEditorFragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreateView(inflater, container, savedInstanceState)
        val viewPager = binding.userViewPager
        val tabLayout = binding.userTabs

        subscribeViewPage(viewPager,tabLayout)  //处理viewPage
        setItemClickAction(binding.menuNav)   //处理菜单事件

        return    binding.root
    }

    override fun subscribeUi(){
        userInfoEditorFragment = UserInfoEditorFragment(_viewModel).setOnItemClickListener(
            object :UserInfoEditorFragment.OnItemClickListener{
                override fun itemClick(type: Int) {
                    PermissionAsk.checkPermission(requireActivity(),
               android.Manifest.permission.READ_EXTERNAL_STORAGE, HEAD){
               openAlbum(HEAD)
           }
                }

            })
          binding.userAppbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
              override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                   when(state){
                       State.COLLAPSED->{
                           setDarkFont(true,false)
                           binding.topNickName.setTextColor(requireActivity().resources.getColor(R.color.black))
                       }
                       State.EXPANDED->{
                           setDarkFont(false,false)
                           binding.topNickName.setTextColor(requireActivity().resources.getColor(R.color.white))
                       }
                   }
              }

              override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
                  super.onOffsetChanged(appBarLayout, i)
                  val dd =   Math.abs(i).toFloat()/662.0
                  eee(""+dd)


              }

          })



        binding.userHeadPortraitUrl.setOnClickListener {

        }

        binding.backgroundImageUrl.setOnClickListener {

        }

        binding.setInformation.setOnClickListener {
            fileList?.clear()
         userInfoEditorFragment.show(requireActivity().supportFragmentManager,"")
        }

        binding.introduce.setOnClickListener {
            openDialog(UserInfoEditorDialog.EDITOR_INTRODUCE)
        }
        binding.nickName.setOnClickListener {
            openDialog(UserInfoEditorDialog.EDITOR_USER_NICKNAME)
        }
        binding.sex.setOnClickListener {
            openDialog(UserInfoEditorDialog.EDITOR_SEX)
        }

        headView = UserHeadLayoutBinding.inflate(LayoutInflater.from(requireContext()))
        binding.menuNav.addHeaderView(headView.root)
    }

    fun openDialog(type:Int){
        UserInfoEditorDialog(_viewModel = _viewModel,type = type).show(requireActivity().supportFragmentManager
            ,"UserFragment")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            HEAD-> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    fileList.addAll(Matisse.obtainResult(data))
                    binding.userHeadPortraitUrl.setImageURI(fileList[0])
                    _viewModel.toUpdateHeadUrl(fileList[0].toString())
                }
            }
            BACKGROUND->{
                if (resultCode == Activity.RESULT_OK && data != null) {
                    fileList.addAll(Matisse.obtainResult(data))
                   // binding.backgroundImageUrl.setImageURI(fileList[0])
                    _viewModel.toUpdateBackGroundUrl(fileList[0].toString())
                }
            }

        }
    }

    private fun openAlbum(requestCode:Int){
        Utils.build(this).openAlbum(requestCode)
    }

    override fun subscribeObserver() {
        super.subscribeObserver()
        binding.viewModel = _viewModel


        _viewModel.localUser.observe(viewLifecycleOwner){
                user->
            //user_head_layout 未设置
            user?.let {

                binding.user = user
                headView.userData = user
            }

        }



        _viewModel.toOpenUserMenu.observe(viewLifecycleOwner){
            binding.userPageMenu.openDrawer(GravityCompat.END)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
        }
        return  true
    }



    private fun subscribeViewPage(viewPager: ViewPager2,tabLayout:TabLayout){
        viewPager.adapter = PageViewAdapter(this).build(
            mapOf(
                0 to { UserPageDynamic() },
                1 to {HomePageDynamic()},
                2 to {UserPageDynamic()}
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



    private fun getTabTitle(position: Int): String? {
            return when (position) {
                0 ->{
                    PersonDynamicViewModel.toQueryWhat = PersonDynamicViewModel.USER_WORK
                    getString(R.string.work)
                }
               1 -> {
                    PersonDynamicViewModel.toQueryWhat = PersonDynamicViewModel.USER_DYNAMIC
                    getString(R.string.dynamic)
                }
               2 ->{
                    PersonDynamicViewModel.toQueryWhat = PersonDynamicViewModel.USER_LIKE
                    getString(R.string.like)
                }
                else -> null
            }
    }
}
