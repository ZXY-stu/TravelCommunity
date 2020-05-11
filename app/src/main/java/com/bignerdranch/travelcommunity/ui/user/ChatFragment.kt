package com.bignerdranch.travelcommunity.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseFragment
import com.bignerdranch.travelcommunity.databinding.FragmentChatBinding


class ChatFragment(override val layoutId: Int = R.layout.fragment_chat,
                   override val needLogin: Boolean = true) : BaseFragment<FragmentChatBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun initImmersionBar() {

    }

}
