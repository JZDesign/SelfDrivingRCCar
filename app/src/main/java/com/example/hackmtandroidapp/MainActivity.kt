package com.example.hackmtandroidapp

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import io.github.controlwear.virtual.joystick.android.JoystickView

class MainActivity : AppCompatActivity() {
    private var port: Int = 0

    //10.82.35.212;5000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val controls = Controls()

        val TVangle = findViewById<TextView>(R.id.angleVal)
        val TVstrength = findViewById<TextView>(R.id.strengthVal)

        val joystick = findViewById<JoystickView>(R.id.joystickView_left)

        findViewById<Button>(R.id.connect_button).apply {
        }

        findViewById<SearchView>(R.id.port).apply {
            listenForKeystrokes()
        }

        joystick.setOnMoveListener { angle, strength ->
            TVangle.text = "ANGLE:" + angle.toString()
            TVstrength.text = "STRENGTH:" + strength.toString()
            // below we will need to pass the text value of the text field
            controls.control(angle, null)
        }
    }

    private fun startTimer() {

    }

    private fun SearchView.listenForKeystrokes() {
        setOnSearchClickListener {}

        setOnCloseListener { false }

        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(s: String): Boolean {
                port = s.toInt()
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
