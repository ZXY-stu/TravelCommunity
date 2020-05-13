package com.bignerdranch.travelcommunity.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.View.NO_ID
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
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
import com.bignerdranch.travelcommunity.ui.dynamic.OPEN_ALBUM
import com.bignerdranch.travelcommunity.ui.dynamic.PublishFragment
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.ui.utils.AndroidWorkaround
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine


class HomePageActivity() : BaseActivity<ActivityHomePageBinding>() {

    override val layoutId: Int = R.layout.activity_home_page
    private var currentNavController: LiveData<NavController>? = null
    private val  baseDialogFragment = PublishFragment()


   private  val viewModel  by viewModels<PersonDynamicViewModel>{
       InjectorUtils.personDynamicViewModelFactory(this)
   }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            LogUtil.e("第一次执行呢")

            setupBottomNavigationBar()
            //设置底部导航栏颜色

         //   window.navigationBarColor = ContextCompat.getColor(this, R.color.white);
            setContainerHeight()
            binding.viewModel = viewModel
            eee(""+ DeviceUtils.deviceHeight(this))
          viewModel.toPublishPage.observeForever {
              if(it){
                  eee("show $it")
                 baseDialogFragment.show(supportFragmentManager,"HomePageActivity")
            }
          }


        } // Else, need to wait for onRestoreInstanceState

    }

    override fun onResume() {
        super.onResume()
        //重新设置容器的高度，避免报错
       // window.navigationBarColor = ContextCompat.getColor(this, R.color.white);
        setContainerHeight()
    }



    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        LogUtil.e("第二次执行哟")
        setupBottomNavigationBar()
    }




    /**
     * Called on first creation and when restoring state.
     */

    private fun setupBottomNavigationBar() {


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

    fun setContainerHeight() {
        val view = binding.navHostContainer.layoutParams
        if (Check.isSystemUiVisible(window)?.get(1)!!) {

            view.height = DeviceUtils.deviceHeight(applicationContext)
            eee("have"+view.height)

        }else{
            view.height = DeviceUtils.deviceHeight(applicationContext) + Check.getNavigationHeight(this)
            eee("not have"+view.height)
        }
        binding.navHostContainer.layoutParams = view
    }








}