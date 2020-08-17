package com.example.thenewgeography

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat

class MainActivity2 : AppCompatActivity() {
    private lateinit var optionClicked: String
    fun backButtonClicked(view: View) {
        val intent = Intent(applicationContext, RestrictionPromptActivity::class.java)
        try {
            startActivity(intent)
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }

    fun mainOptionClicked(view: View) {
        var newIntent = Intent(applicationContext, SolutionMapsActivity::class.java)
        Log.i("Option: ", optionClicked)
        Log.i("Tag: ", view.tag.toString())
        if (view.tag.toString() == "0") {
            when (optionClicked) {
                "0" -> {
                    newIntent = Intent(applicationContext, TutorialMapsActivity::class.java)
                }
                "1" -> {
                    newIntent = Intent(applicationContext, KoreaTutorialActivity::class.java)
                }
                "2" -> {
                    newIntent = Intent(applicationContext, OtherTutorialActivity::class.java)
                }
                "3" -> {
                    newIntent = Intent(applicationContext, TutorialMapsActivity::class.java)
                }
            }
        } else if (view.tag.toString() == "1") {
            when (optionClicked) {
                "0" -> {
                    newIntent = Intent(applicationContext, SolutionMapsActivity::class.java)
                }
                "1" -> {
                    newIntent = Intent(applicationContext, KoreaSolutionActivity::class.java)
                }
                "2" -> {
                    newIntent = Intent(applicationContext, OtherSolutionActivity::class.java)
                }
                "3" -> {
                    newIntent = Intent(applicationContext, TutorialMapsActivity::class.java)
                }
            }
        }

        try {
            startActivity(newIntent)
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val oldIntent: Intent = intent
        val solutionPressed = findViewById<TextView>(R.id.solutionButton)
        optionClicked = oldIntent.getStringExtra("Option") ?: "null-value-fallback"

        val tutorialButton = findViewById<Button>(R.id.tutorialButton)
        tutorialButton.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.background = ContextCompat.getDrawable(this, R.drawable.pressed_tutorial_button)
                  //  v.setText("What is the solution?")
                    v.invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    v.background = ContextCompat.getDrawable(this, R.drawable.tutorial_button)
                    v.invalidate()
                }
            }
            false
        }

        val solutionButton = findViewById<TextView>(R.id.solutionButton)

        solutionButton.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.background = ContextCompat.getDrawable(this, R.drawable.solution_buton_pressed)
                    v.invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    v.background = ContextCompat.getDrawable(this, R.drawable.solution_buton_active)
                    v.invalidate()
                }
            }
            false
        }

    }
}

