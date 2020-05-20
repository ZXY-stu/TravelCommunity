package com.bignerdranch.travelcommunity.ui.user.userProfile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.AlertDialogFragment
import com.bignerdranch.travelcommunity.base.BaseDialogFragment
import com.bignerdranch.travelcommunity.databinding.UserInfoEditorBinding
import com.bignerdranch.travelcommunity.ui.dynamic.ImageEditorDialog
import com.bignerdranch.travelcommunity.ui.user.UserViewModel
import com.bignerdranch.travelcommunity.ui.utils.Utils
import com.bignerdranch.travelcommunity.ui.utils.open
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * @author zhongxinyu
 * @date 2020/5/20
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class UserInfoEditorDialog(val _viewModel: UserViewModel,
                           val type:Int
) :BaseDialogFragment<UserInfoEditorBinding>() {
    override val layoutId: Int = R.layout.user_info_editor
    override val needLogin: Boolean = false
    override val themeResId: Int = R.style.Dialog_Bottom
    override var windowHeight: Double =  0.2
    override var windowWidth: Double = 0.5



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
            editor.setText("")
            _viewModel.textContent.value = ""
            when(type){
                EDITOR_INTRODUCE->  setDialogInfo("介绍下自己吧","提交","取消")
                EDITOR_USER_NICKNAME-> setDialogInfo("更改昵称","提交","取消")
                EDITOR_SEX -> {
                    setDialogInfo("选择性别","男","女")
                     editor.hint = ""
                      editor.isEnabled = false
                }
                else -> throw IllegalStateException("")
            }
        }

    }

    fun setDialogInfo(title:String,subText:String,otherActionText:String){
        with(binding){
            submit.setText(subText)

            titleContent.text = title

            otherAction.setText(otherActionText)
            submit.setOnClickListener {
               when(type){
                   EDITOR_USER_NICKNAME -> _viewModel.toUpdateNickName()
                   EDITOR_INTRODUCE -> _viewModel.toUpDateIntroduce()
                   EDITOR_SEX -> _viewModel.toUpDateSex("man")
               }
                dismiss()
            }

            otherAction.setOnClickListener {
                if(type == EDITOR_SEX) _viewModel.toUpDateSex("woman")
                dismiss()
            }

            close.setOnClickListener {
                dismiss()
            }
        }
    }


   companion object{
       const val EDITOR_HEAD = 0
       const val EDITOR_BACKGROUND = 1
       const val EDITOR_USER_NICKNAME = 2
       const val EDITOR_INTRODUCE = 3
       const val EDITOR_SEX = 4
   }


}