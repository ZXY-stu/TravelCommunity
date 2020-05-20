package com.bignerdranch.travelcommunity.ui.dynamic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.bignerdranch.tclib.LogUtil.eee

import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseDialogFragment
import com.bignerdranch.travelcommunity.base.BaseFragment
import com.bignerdranch.travelcommunity.databinding.FragmentPrivateBinding
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import kotlinx.android.synthetic.main.mytitle.view.*


class PrivateFragment(
    val viewModel: PersonDynamicViewModel
) : BaseDialogFragment<FragmentPrivateBinding>() {

    override val themeResId: Int = R.style.DialogFullScreen_Bottom
    override val layoutId: Int = R.layout.fragment_private
    override val needLogin: Boolean = false
    companion object{
        const val OPEN = 0
        const val ONLY_FRIEND = 1
        const val ONLY_CHOOSE = 2
        const val BESIDE_CHOOSE = 3
        const val SECRET = 4
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


    override fun subscribeUi() {

    }

    override fun subscribeListener() {
        with(binding.privateToolbar){
            publicToolbar.setNavigationOnClickListener {
                dismiss()
            }
            titleContent.text = "动态权限"
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.open-> viewModel.dynamicPrivateModel = OPEN
            R.id.only_choose-> {
                eee("选中了 only_choose")
                viewModel.dynamicPrivateModel = ONLY_CHOOSE
            }
            R.id.beside_choose-> viewModel.dynamicPrivateModel = BESIDE_CHOOSE
            R.id.only_friend-> viewModel.dynamicPrivateModel = ONLY_FRIEND
            R.id.secret-> viewModel.dynamicPrivateModel = SECRET
        }
        return super.onOptionsItemSelected(item)
    }

    override fun subscribeObserver() {

    }
}
