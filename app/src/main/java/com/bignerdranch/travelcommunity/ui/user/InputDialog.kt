package com.bignerdranch.travelcommunity.ui.user

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentManager
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseDialogFragment
import com.bignerdranch.travelcommunity.databinding.InputDialogBinding
import java.util.*

/**
 * @author zhongxinyu
 * @date 2020/5/10
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
 class InputDialog(override val layoutId: Int = R.layout.input_dialog,
                  override val needLogin: Boolean = true,
                  override val themeResId: Int = R.style.Dialog_Bottom,
                   val textContent:String
) :BaseDialogFragment<InputDialogBinding>(){
    init {
        windowHeight = 0.1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
       return  binding.root
    }

    override fun onResume() {
        super.onResume()

       with(binding.editTextContent){
           setFocusable(true);
          setFocusableInTouchMode(true);
           requestFocus();
           hint = "回复 @$textContent"

       }

    }

    override fun subscribeUi() {

    }

    override fun subscribeListener() {

    }

    override fun subscribeObserver() {

    }

}