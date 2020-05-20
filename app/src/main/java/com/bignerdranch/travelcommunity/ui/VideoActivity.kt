package com.bignerdranch.travelcommunity.ui


/**
 * @author zhongxinyu
 * @date 2020/4/23
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
import android.app.Activity
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Toast
import com.bignerdranch.travelcommunity.R
import okhttp3.internal.cache.CacheStrategy
import java.io.IOException
import java.util.*


class VideoActivity : Activity(), SurfaceHolder.Callback {
    private lateinit var cacheStrategy:CacheStrategy

    private var firstPlayer: MediaPlayer? = null
    //负责播放进入视频播放界面后的第一段视频
    private var nextMediaPlayer: MediaPlayer? = null
    //负责一段视频播放结束后，播放下一段视频
    private var cachePlayer: MediaPlayer? = null
    //负责setNextMediaPlayer的player缓存对象
    private var currentPlayer //负责当前播放视频段落的player对象
            : MediaPlayer? = null
    private var surface: SurfaceView? = null
    private var surfaceHolder: SurfaceHolder? = null
    private val VideoListQueue =
        ArrayList<String>()
    private val playersCache =
        HashMap<String, MediaPlayer>()


    private val currentVideoIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (firstPlayer != null) {
            if (firstPlayer!!.isPlaying) {
                firstPlayer!!.stop()
            }
            firstPlayer!!.release()
        }
        if (nextMediaPlayer != null) {
            if (nextMediaPlayer!!.isPlaying) {
                nextMediaPlayer!!.stop()
            }
            nextMediaPlayer!!.release()
        }
        if (currentPlayer != null) {
            if (currentPlayer!!.isPlaying) {
                currentPlayer!!.stop()
            }
            currentPlayer!!.release()
        }
        currentPlayer = null
    }

    private fun initView() {
     // surface = findViewById<View>(R.id.surfaceView) as SurfaceView
        surfaceHolder = surface!!.holder // SurfaceHolder是SurfaceView的控制接口
        surfaceHolder?.addCallback(this) // 因为这个类实现了SurfaceHolder.Callback接口，所以回调参数直接this
    }

    override fun surfaceChanged(
        arg0: SurfaceHolder,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) { // TODO 自动生成的方法存根
    }

    override fun surfaceCreated(arg0: SurfaceHolder) { //surfaceView创建完毕后，首先获取该直播间所有视频分段的url
        videoUrls
        //然后初始化播放手段视频的player对象
        initFirstPlayer()
    }

    override fun surfaceDestroyed(arg0: SurfaceHolder) {


    }
    private fun initFirstPlayer() {
        firstPlayer = MediaPlayer()
        firstPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        firstPlayer!!.setDisplay(surfaceHolder)
        firstPlayer!!
            .setOnCompletionListener { mp -> onVideoPlayCompleted(mp) }
        cachePlayer = firstPlayer
        initNexttPlayer()
        startPlayFirstVideo()
    }

    private fun startPlayFirstVideo() {
        try {
            firstPlayer!!.setDataSource(VideoListQueue[currentVideoIndex])
            firstPlayer!!.prepare()
            firstPlayer!!.start()
        } catch (e: IOException) { // TODO 自动生成的 catch 块
            e.printStackTrace()
        }
    }

    private fun initNexttPlayer() {
        Thread(Runnable {
            for (i in 1 until VideoListQueue.size) {
                nextMediaPlayer = MediaPlayer()
                nextMediaPlayer!!
                    .setAudioStreamType(AudioManager.STREAM_MUSIC)
                nextMediaPlayer!!
                    .setOnCompletionListener { mp -> onVideoPlayCompleted(mp) }
                try {
                    nextMediaPlayer!!.setDataSource(VideoListQueue[i])
                    nextMediaPlayer!!.prepare()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                cachePlayer!!.setNextMediaPlayer(nextMediaPlayer)
                cachePlayer = nextMediaPlayer
                playersCache[i.toString()] = nextMediaPlayer!!
            }
        }).start()
    }

    private fun onVideoPlayCompleted(mp: MediaPlayer) {
        Log.e("cdl", "============" + currentVideoIndex + "/" + VideoListQueue.size)
        mp.setDisplay(null)
        currentVideoIndex.plus(1)
        currentPlayer = playersCache[(currentVideoIndex).toString()]
        if (currentPlayer != null) {
            currentPlayer!!.setDisplay(surfaceHolder)
        } else {
            Toast.makeText(this@VideoActivity, "视频播放完毕..", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private val videoUrls: Unit
        get() {
            val url = "/sdcard/bb.mp4"
            VideoListQueue.add(url)
            VideoListQueue.add("/sdcard/bb.mp4")
            VideoListQueue.add(url)
            VideoListQueue.add(url)
            VideoListQueue.add(url)
            VideoListQueue.add(url)
            VideoListQueue.add(url)
            VideoListQueue.add(url)
            VideoListQueue.add(url)
            VideoListQueue.add(url)
        }
}