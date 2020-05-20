package com.bignerdranch.travelcommunity.ui.user.userProfile

import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.observe
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseDialogFragment
import com.bignerdranch.travelcommunity.databinding.UserInfoInputBinding
import com.bignerdranch.travelcommunity.ui.user.UserViewModel

/**
 * @author zhongxinyu
 * @date 2020/5/20
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class UserInfoInputFragment(val _viewModel:UserViewModel,
                            val title:String,
                            val subTitle:String,
                            val content:String,
                            val type:String,
                            val limitNumber: Int)
    :BaseDialogFragment<UserInfoInputBinding>() {
    override val layoutId: Int = R.layout.user_info_input
    override val needLogin: Boolean = false
    override val themeResId: Int = R.style.DialogFullScreen_Right


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)
         return binding.root
    }


    override fun subscribeUi() {
        super.subscribeUi()
        with(binding){
            viewModel = _viewModel
            toolbar.titleContent.text = title
            typeContent.text = subTitle
            toolbar.publicToolbar.setNavigationOnClickListener {
                dismiss()
            }
            toolbar.yourAction.visibility = View.VISIBLE
            toolbar.yourAction.isEnabled = false
            toolbar.yourAction.text = "保存"
            toolbar.yourAction.background = resources.getDrawable(R.color.transparent)
            toolbar.yourAction.setOnClickListener {
                when(type){
                    "nickName"->  _viewModel.toUpdateNickName()
                    "introduce" -> _viewModel.toUpDateIntroduce()
                    "address" -> _viewModel.toUpDateAddress()
                }
                 dismiss()
            }

          if(type == "introduce") {
              editor.setLines(7)
              editor.gravity = Gravity.START
              close.visibility = View.GONE
          }


            _viewModel.textContent.postValue(content)

            close.setOnClickListener {
                _viewModel.textContent.postValue("")
            }

            editor.filters = arrayOf(InputFilter.LengthFilter(limitNumber))

            _viewModel.textContent.observe(viewLifecycleOwner){
                limit.text = "${it.length}/$limitNumber"
                toolbar.yourAction.isEnabled = it != content
               with( toolbar.yourAction){
                  if(it == content) setTextColor(resources.getColor(R.color.gray))
                   else setTextColor(resources.getColor(R.color.divider))
               }
            }
        }
    }






}