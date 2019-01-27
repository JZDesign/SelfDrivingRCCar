package com.example.hackmtandroidapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import io.github.controlwear.virtual.joystick.android.JoystickView

class MainActivity : AppCompatActivity() {
    private var port = " "
    private var isConnected = false
    private val controls = Controls()
    val TVangle get() = findViewById<TextView>(R.id.angleVal)
    val TVstrength get() = findViewById<TextView>(R.id.strengthVal)
    val joystick get() = findViewById<JoystickView>(R.id.joystickView_left)
    private val button get() = findViewById<Button>(R.id.connect_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.apply {
            setOnClickListener {
                if (isConnected) {
                    button.text = getString(R.string.connect)
                    isConnected = false
                }else {
                    button.text = getString(R.string.disconnect)
                    isConnected = true
                }
            }
        }

        findViewById<SearchView>(R.id.port).apply { listenForKeystrokes() }
        joystick.setOnMoveListener()

    }

    private inline fun JoystickView.setOnMoveListener() = setOnMoveListener { angle, strength ->
            TVangle.text = "ANGLE:" + angle.toString()
            TVstrength.text = "STRENGTH:" + strength.toString()
            controls.driveIn(angle, port)
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
}
