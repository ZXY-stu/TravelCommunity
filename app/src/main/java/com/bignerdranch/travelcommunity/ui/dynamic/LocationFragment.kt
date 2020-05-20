package com.bignerdranch.travelcommunity.ui.dynamic

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout

import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseDialogFragment
import com.bignerdranch.travelcommunity.base.BaseFragment
import com.bignerdranch.travelcommunity.databinding.FragmentLocationBinding
import com.bignerdranch.travelcommunity.tcvideoplayer.TCPlayer
import com.bignerdranch.travelcommunity.ui.HomePageActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LocationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LocationFragment(
    private val mcontext: Context,
    override val layoutId: Int = R.layout.fragment_location,
    override val needLogin: Boolean = false,
    override val themeResId: Int = R.style.DialogFullScreen
) : BaseDialogFragment<FragmentLocationBinding>() {


    private val activity = mcontext as HomePageActivity
    private lateinit var full:FrameLayout
   private lateinit var tcPlayer: TCPlayer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the layout for this fragment
        return  binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
        initVideoView()
    }
   private fun initVideoView(){
       // binding.play.visibility = View.VISIBLE
      //  binding.play.addView(tcPlayer)

       full = activity.findViewById(R.id.activityPlay)
       full.visibility = View.VISIBLE
       full.addView(tcPlayer)
        tcPlayer?.play("http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4")
    }

    private fun init(){
        tcPlayer = TCPlayer(activity)
        tcPlayer
            .setDoubleClickListener {  }
            .onPrepared { tcPlayer?.start() }
            .onComplete { tcPlayer?.play() }
            .setNetChangeListener(true)
            .setLive(false)
            .setSupportGesture(false)
            .setShowTopControl(false)
            .setFullScreenOnly(false)
    }

    override fun subscribeUi() {

    }

    override fun subscribeListener() {

    }

    override fun subscribeObserver() {

    }


}
