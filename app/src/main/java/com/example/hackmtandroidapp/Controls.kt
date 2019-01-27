package com.example.hackmtandroidapp

import org.jetbrains.anko.doAsync
import java.io.IOException
import java.io.PrintWriter
import java.net.Socket
import com.example.hackmtandroidapp.Direction.DOWN
import com.example.hackmtandroidapp.Direction.UP
import com.example.hackmtandroidapp.Direction.RIGHT
import com.example.hackmtandroidapp.Direction.LEFT
class Controls {

    fun control(direction: Int, port: String? = null) = port?.let {
        val portInt = it.toInt()
        when (direction) {
            //we need to make it so that if angle
            in 136..225 -> connect(LEFT.data, portInt)
            in 226..315 -> connect(DOWN.data, portInt)
            else        -> connect(UP.data, portInt)
        }
    }

    private fun connect(message: String, port: Int) = tryAsync {
        Socket(IP, port).also { socket ->
            PrintWriter(socket.getOutputStream()).apply {
                write(message)
                flush()
                close()
            }
            socket.close()
        }
    }

    private fun tryAsync(block: () -> Unit) {
        doAsync {
            try {
                block()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        const val IP = "10.82.35.212"
    }

}
