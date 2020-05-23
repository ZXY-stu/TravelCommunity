package com.bignerdranch.travelcommunity.base

/**
 * @author zhongxinyu
 * @date 2020/4/10
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 */
/**
 * 网络改变监控广播
 *
 *
 * 监听网络的改变状态,只有在用户操作网络连接开关(wifi,mobile)的时候接受广播,
 * 然后对相应的界面进行相应的操作，并将 状态 保存在我们的APP里面
 *
 *
 *
 *
 */
interface NetworkChangeListener{
    fun onNetWorkChanged(type:Int)
    fun onDisconnected()
    fun onConnected()
}