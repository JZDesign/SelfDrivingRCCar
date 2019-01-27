package com.example.hackmtandroidapp

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import io.github.controlwear.virtual.joystick.android.JoystickView
import org.jetbrains.anko.doAsync
import java.util.*
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {
    private val button get() = findViewById<Button>(R.id.connect_button)
    private var port = " "
    var direction = 0
    private var isConnected = false
    private val controls = Controls()
    private var count = 0
    val TVangle get() = findViewById<TextView>(R.id.angleVal)
    val TVstrength get() = findViewById<TextView>(R.id.strengthVal)
    //var timer: Timer? = null


    //10.82.35.212;5000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val joystick = findViewById<JoystickView>(R.id.joystickView_left)

        button.apply {
            setOnClickListener {
//                toggleButtonText()
                if (isConnected) {
                    button.text = getString(R.string.connect)
                    isConnected = false
                }else {
                    button.text = getString(R.string.disconnect)
                    isConnected = true
                }
            }
        }

        findViewById<SearchView>(R.id.port).apply {
            listenForKeystrokes()
        }

        joystick.setOnMoveListener()

    }

    private inline fun JoystickView.setOnMoveListener() = setOnMoveListener { angle, strength ->
            TVangle.text = "ANGLE:" + angle.toString()
            TVstrength.text = "STRENGTH:" + strength.toString()
            controls.control(angle, port)
            Log.d("Count: ", count.toString())
            count++
    }

    private fun toggleButtonText() = when (isConnected) {
        true -> button.text = getString(R.string.disconnect)
        else -> button.text = getString(R.string.connect)
    }

    private fun SearchView.listenForKeystrokes() {
        setOnSearchClickListener {}
        setOnCloseListener { false }
        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(s: String): Boolean {
                port = s
                return true
            }

            override fun onQueryTextSubmit(s: String): Boolean = false // closes keyboard if s != ""
        })
    }

    companion object {
        var handler: Handler? = null
        var CONNECTION_ERROR: Int = 0
        var CONNECTION_LOST: Int = 0
    }
}
