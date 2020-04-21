package com.bignerdranch.travelcommunity.ui.dynamic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController

import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseFragment
import com.bignerdranch.travelcommunity.databinding.FragmentPrivateBinding
import kotlinx.android.synthetic.main.mytitle.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PrivateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PrivateFragment: BaseFragment<FragmentPrivateBinding>() {


    override val layoutId: Int = R.layout.fragment_private


    override fun initImmersionBar() {

    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (enter) {
            return AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);
        } else {
            return AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_right);
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
             super.onCreateView(inflater, container, savedInstanceState)

            return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.privateToolbar.publicToolbar.setNavigationOnClickListener {
            findNavController().popBackStack(R.id.publishFragment,false)
        }
    }


}
