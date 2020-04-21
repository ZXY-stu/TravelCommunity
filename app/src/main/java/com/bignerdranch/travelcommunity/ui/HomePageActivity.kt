package com.bignerdranch.travelcommunity.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseActivity
import com.bignerdranch.travelcommunity.databinding.ActivityHomePageBinding
import com.bignerdranch.travelcommunity.util.LogUtil
import kotlinx.android.synthetic.main.activity_home_page.view.*

class HomePageActivity() : BaseActivity<ActivityHomePageBinding>() {

    override val layoutId: Int = R.layout.activity_home_page
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            LogUtil.e("这里oncrea")
            setupBottomNavigationBar()

        } // Else, need to wait for onRestoreInstanceState

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {


        val navGraphIds = listOf(R.navigation.homepage, R.navigation.other,
            R.navigation.publish,R.navigation.message,R.navigation.mine)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = binding.bottomNav.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )



/*
*  R.anim.slide_in_right,
                            R.anim.slide_out_left,
                            R.anim.slide_in_left,
                            R.anim.slide_out_right
*
* */

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            LogUtil.e("current"+navController.toString()+"${navController.currentDestination}")
         //   setupActionBarWithNavController(navController)

        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

}