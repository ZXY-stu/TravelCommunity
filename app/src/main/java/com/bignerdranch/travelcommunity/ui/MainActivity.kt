package com.bignerdranch.travelcommunity.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.databinding.ActivityMainBinding
import com.bignerdranch.travelcommunity.viewmodels.UserLoginViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProviders.of(this)[UserLoginViewModel::class.java]
        val bind:ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        bind.viewModel = viewModel
    }


}
