package com.bignerdranch.travelcommunity.videocache

import java.io.OutputStream
import java.net.InetAddress
import java.net.Socket

/**
 * @author zhongxinyu
 * @date 2020/4/30
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/

const val SERVER_IP = "192.168.0.106"
const val LOCAL_PORT = 40977
class ServerConnector {

    private lateinit var socket:Socket
    private lateinit var outputStream:OutputStream

      init {
          socket = Socket(InetAddress.getByName(SERVER_IP),0)
          outputStream = socket.getOutputStream()
      }

    companion object {
        private var connector: ServerConnector? = null

        fun build(): ServerConnector {
            return connector ?: synchronized(this) {
                connector ?: ServerConnector()
            }
        }
    }



   fun sendMsg(msg:String){
      outputStream.write(msg.toByteArray())
   }
}