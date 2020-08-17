package com.example.thenewgeography

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button

class RestrictionPromptActivity : AppCompatActivity() {


    fun backButtonClicked(view: View) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("BeenBefore", true)
        try {
            startActivity(intent)
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }

    fun optionButtonClicked(view: View) {
        val intent = Intent(applicationContext, MainActivity2::class.java)
        Log.i("Tag: ", view.tag.toString())
        intent.putExtra("Option", view.tag.toString())
        try {
            startActivity(intent)
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }

    private fun onTouchListen(v: View, event:MotionEvent, pressedDrawable: Int, activeDrawable: Int) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                v.background = getDrawable(pressedDrawable)
                v.invalidate()
            }
            MotionEvent.ACTION_UP -> {
                v.background = getDrawable(activeDrawable)
                v.invalidate()
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trial_for_buttons)

        val chinaButton = findViewById<Button>(R.id.chinaButton)
        val koreaButton = findViewById<Button>(R.id.koreaButton)
        val othersButton = findViewById<Button>(R.id.othersButton)

        chinaButton.setOnTouchListener { v, event ->
            onTouchListen(v, event, R.drawable.china_pressed, R.drawable.china_active)
            false
        }
        koreaButton.setOnTouchListener { v, event ->
            onTouchListen(v, event, R.drawable.southkorea_pressed, R.drawable.southkorea_active)
            false
        }


        othersButton.setOnTouchListener { v, event ->
            onTouchListen(v, event, R.drawable.other_pressed, R.drawable.other_active)

            false
        }

    }
}