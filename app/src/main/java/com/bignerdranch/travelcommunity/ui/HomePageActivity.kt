package com.bignerdranch.travelcommunity.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.View.NO_ID
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.add
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.bignerdranch.tclib.data.repository.BaseRepository
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseActivity
import com.bignerdranch.travelcommunity.base.BaseViewModel
import com.bignerdranch.travelcommunity.databinding.ActivityHomePageBinding
import com.bignerdranch.travelcommunity.util.InjectorUtils
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.tclib.utils.DeviceUtils
import com.bignerdranch.travelcommunity.base.Check
import com.bignerdranch.travelcommunity.base.Message
import com.bignerdranch.travelcommunity.tcvideoplayer.TCPlayer
import com.bignerdranch.travelcommunity.ui.dynamic.LocationFragment
import com.bignerdranch.travelcommunity.ui.dynamic.OPEN_ALBUM
import com.bignerdranch.travelcommunity.ui.dynamic.PublishFragment
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.ui.utils.AndroidWorkaround
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class HomePageActivity() : BaseActivity<ActivityHomePageBinding>() {

    override val layoutId: Int = R.layout.activity_home_page
    private var currentNavController: LiveData<NavController>? = null
    private val  baseDialogFragment = PublishFragment()
    private var type = Message.SHOW_BOTTOM_VIEW
    private var screenType = -1

    private  val viewModel  by viewModels<PersonDynamicViewModel>{
       InjectorUtils.personDynamicViewModelFactory(this)
   }


    override fun onConfigurationChanged(newConfig: Configuration) {
        eee("oddfdsfsdfnConfigurationChanged")
        if(newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
           // binding.bottomNav.visibility = View.VISIBLE
        }else{
           // binding.bottomNav.visibility = View.GONE
        /*    val attrs = window.getAttributes();
            attrs.flags =  attrs.flags or  WindowManager.LayoutParams.FLAG_FULLSCREEN;
            window.setAttributes(attrs);
            window.addFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
*/
        }
        super.onConfigurationChanged(newConfig)
    }


//   网易云音乐在视频播放优化上的实践 - [已重置]的文章 - 知乎
//https://zhuanlan.zhihu.com/p/56086941

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            LogUtil.e("第一次执行呢")


            setupBottomNavigationBar(this)
            //设置底部导航栏颜色
          setContainerHeight(type)



         //   window.navigationBarColor = ContextCompat.getColor(this, R.color.white);

           binding.viewModel = viewModel
            eee(""+ DeviceUtils.deviceHeight(this))
         viewModel.toPublishPage.observeForever {
              if(it){
                  eee("show $it")
                //  sendMsg("hello fragment")
                 baseDialogFragment.show(supportFragmentManager,"HomePageActivity")
            }
          }

        } // Else, need to wait for onRestoreInstanceState
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReceiveMessage(msg:Message){
        when(msg.type){
           Message.HIDE_BOTTOM_VIEW ->{
                type = Message.HIDE_BOTTOM_VIEW
               setContainerHeight(type)
               binding.bottomNav.visibility = View.GONE
           }
            Message.SHOW_BOTTOM_VIEW ->{
                type = Message.SHOW_BOTTOM_VIEW
                setContainerHeight(type)
                binding.bottomNav.visibility = View.VISIBLE
            }
        }
    }

    fun sendMsg(msg: Message){
        EventBus.getDefault().post(msg)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onResume() {
        super.onResume()
        //重新设置容器的高度，避免报错
       // window.navigationBarColor = ContextCompat.getColor(this, R.color.white);
        //initVideoView()
          //ImageEditor(this).show(supportFragmentManager,"")

        setContainerHeight(type)

    }



    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        LogUtil.e("第二次执行哟")
       setupBottomNavigationBar(this)
    }




    /**
     * Called on first creation and when restoring state.
     */

    private fun setupBottomNavigationBar(activity: HomePageActivity) {


        val navGraphIds = listOf(R.navigation.homepage,R.navigation.other,
           R.navigation.message,R.navigation.mine)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = binding.bottomNav.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )
        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            LogUtil.e("current"+"${navController.currentDestination}")
          // setupActionBarWithNavController(navController)
              val desId = navController.currentDestination?.id
            LogUtil.e(""+desId + "mine"+R.id.userFragment)

        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    fun setContainerHeight(type:Int) {
        val view = binding.navHostContainer.layoutParams
        if (Check.isSystemUiVisible(window)?.get(1)!!) {

            screenType = Message.NOT_FULL_SCREEN
            eee("发送 NOT_FULL_SCREEN")

            when(type) {
             Message.SHOW_BOTTOM_VIEW -> view.height = DeviceUtils.deviceHeight(applicationContext)
             Message.HIDE_BOTTOM_VIEW ->  view.height = WindowManager.LayoutParams.MATCH_PARENT
           }
        }else{
            eee("发送 FULL_SCREEN")
            screenType = Message.FULL_SCREEN
            when(type) {
                Message.SHOW_BOTTOM_VIEW -> view.height = DeviceUtils.deviceHeight(applicationContext) + Check.getNavigationHeight(this)
                Message.HIDE_BOTTOM_VIEW ->  view.height = WindowManager.LayoutParams.MATCH_PARENT
            }
        }
        sendMsg(Message("HomePageActivity",screenType))
        binding.navHostContainer.layoutParams = view
    }

    fun resetContainerHeight(){
        val view = binding.navHostContainer.layoutParams
        view.height = WindowManager.LayoutParams.MATCH_PARENT
        binding.navHostContainer.layoutParams = view
    }








}