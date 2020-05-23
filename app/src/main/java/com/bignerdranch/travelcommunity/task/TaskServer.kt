package com.bignerdranch.travelcommunity.task

import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.travelcommunity.adapters.Coverters
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorCompletionService
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * @author zhongxinyu
 * @date 2020/5/22
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

object TaskServer {

    private val handler = Handler(Looper.getMainLooper())
    private val coreSize = Runtime.getRuntime().availableProcessors() + 1
    private val fix: ExecutorService = Executors.newFixedThreadPool(coreSize)
    private val cache: ExecutorService = Executors.newCachedThreadPool()
    private val single: ExecutorService = Executors.newSingleThreadExecutor()
    private val scheduled: ExecutorService = Executors.newScheduledThreadPool(coreSize)


    fun execute(block: () -> Unit){
       fix.execute(block)
    }

    fun getExecute() = fix

    fun executeAutoCleanTask(limit:Int,block: (curSize:Int) -> Unit){
        fix.execute {
        }
    }



    /**
     * 切换到主线程
     */
    fun ktxRunOnUi(block: () -> Unit) {
        handler.post {
            block()
        }
    }

    /**
     * 延迟delayMillis后切换到主线程
     */
    fun <T> T.ktxRunOnUiDelay(delayMillis: Long, block: T.() -> Unit) {
        handler.postDelayed({
            block()
        }, delayMillis)
    }

    /**
     * 子线程执行。SingleThreadPool
     */
    fun <T> T.ktxRunOnBgSingle(block: T.() -> Unit) {
        single.execute {
            block()
        }
    }

    /**
     * 子线程执行。FixedThreadPool
     */
    fun <T> T.ktxRunOnBgFix(block: T.() -> Unit) {
        fix.execute {
            block()
        }
    }

    /**
     * 子线程执行。CachedThreadPool
     */
    fun <T> T.ktxRunOnBgCache(block: T.() -> Unit) {
        cache.execute {
            block()
        }
    }
}


interface Task{
    fun execute()
}