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

    fun driveIn(direction: Int, atPort: String? = null) = atPort?.let {
        val portInt = if(atPort != " ")it.toInt() else 0
        when (direction) {
            in 135..75 -> transmit(UP.data, portInt)
            in 136..225 -> transmit(LEFT.data, portInt)
            0,
            in 226..315 -> transmit(DOWN.data, portInt)
            else        -> transmit(RIGHT.data, portInt)
        }
    }

    private fun transmit(message: String, port: Int) = tryAsync {
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
