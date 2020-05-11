package com.bignerdranch.tclib

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import com.bignerdranch.tclib.data.db.TCDataBases
import com.bignerdranch.tclib.data.db.entity.Chat
import com.bignerdranch.tclib.data.db.entity.User
import com.bignerdranch.tclib.data.network.Network
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

/*
        Matisse.from(this)
            .choose(MimeType.allOf()) // 选择 mime 的类型
            .countable(true)
            .maxSelectable(9) // 图片选择的最多数量
            .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
            .thumbnailScale(0.85f) // 缩略图的比例
            .imageEngine(GlideEngine()) // 使用的图片加载引擎
            .forResult(2); // 设置作为标记的请求码

*/







    }



}
