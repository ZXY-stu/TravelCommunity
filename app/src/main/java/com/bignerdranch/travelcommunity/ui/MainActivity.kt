package com.bignerdranch.travelcommunity.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.data.db.entity.User
import com.bignerdranch.travelcommunity.data.network.Network
import com.bignerdranch.travelcommunity.data.network.model.Model
import com.bignerdranch.travelcommunity.databinding.ActivityMainBinding
import com.bignerdranch.travelcommunity.util.LogUtil
import com.bignerdranch.travelcommunity.viewmodels.UserLoginViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProviders.of(this)[UserLoginViewModel::class.java]
        val bind: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        bind.viewModel = viewModel

    }

    }



