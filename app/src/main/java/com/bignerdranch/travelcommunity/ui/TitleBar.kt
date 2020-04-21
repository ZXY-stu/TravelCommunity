package com.bignerdranch.travelcommunity.ui

import android.content.Context
import androidx.appcompat.widget.Toolbar
import androidx.databinding.adapters.ToolbarBindingAdapter
import com.bignerdranch.travelcommunity.generated.callback.OnClickListener
import kotlin.properties.Delegates

/**
 * @author zhongxinyu
 * @date 2020/4/20
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class TitleBar{
    lateinit var titleName:String
    lateinit var titleClickListener: OnClickListener
    lateinit var backClickListener:  OnClickListener
    lateinit var addClickListener:   OnClickListener
    lateinit var actionClickListener: OnClickListener

    data class Visibility(
        var title: Boolean = false,
        var backButton: Boolean = false,
        var navigateTitle: Boolean = false,
        var addButton: Boolean = false
    )
}