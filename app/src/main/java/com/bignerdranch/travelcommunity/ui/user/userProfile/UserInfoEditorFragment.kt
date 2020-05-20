package com.bignerdranch.travelcommunity.ui.user.userProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseDialogFragment
import com.bignerdranch.travelcommunity.databinding.UserInfoEditorFragmentBinding
import com.bignerdranch.travelcommunity.ui.user.UserViewModel
import com.bignerdranch.travelcommunity.ui.user.userProfile.UserInfoEditorDialog.Companion.EDITOR_HEAD
import com.bignerdranch.travelcommunity.ui.user.userProfile.UserInfoEditorDialog.Companion.EDITOR_SEX
import kotlinx.android.synthetic.main.my_toolbar.*

/**
 * @author zhongxinyu
 * @date 2020/5/20
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class UserInfoEditorFragment(val _viewModel: UserViewModel)
    :BaseDialogFragment<UserInfoEditorFragmentBinding>() {
    override val layoutId: Int = R.layout.user_info_editor_fragment
    override val needLogin: Boolean = false
    override val themeResId: Int = R.style.DialogFullScreen_Right

    interface OnItemClickListener{
        fun itemClick(type: Int)
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener):UserInfoEditorFragment{
        this.onItemClickListener = onItemClickListener
        return this
    }

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
            toolbar.titleContent.text = "编辑资料"
            toolbar.publicToolbar.setNavigationOnClickListener {
                dismiss()
            }

            replaceHead.setOnClickListener {
                 onItemClickListener?.itemClick(EDITOR_HEAD)
            }

            setSex.setOnClickListener {
               UserInfoEditorDialog(_viewModel,EDITOR_SEX).show(requireActivity().supportFragmentManager,"")
            }

            setIntroduce.setOnClickListener {
                showDialog("修改简介","你的简介",""+_viewModel.localUser.value?.introduce,"introduce",150)
            }

            setNickName.setOnClickListener {
                showDialog("修改昵称","你的昵称",""+_viewModel.localUser.value?.nickName,"nickName",20)
            }
        }
    }

    fun showDialog(title:String,subTitle:String,content:String,type:String,limitNumber:Int){
        UserInfoInputFragment(_viewModel,title,subTitle,content,
            type,limitNumber).show(requireActivity().supportFragmentManager,"")
    }
}