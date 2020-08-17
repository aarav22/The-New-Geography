package com.example.thenewgeography

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.targets.ActionViewTarget
import com.github.amlcurran.showcaseview.targets.ViewTarget


class MainActivity : AppCompatActivity() {

    fun startButtonClicked(button: View) {
        button.animate().startDelay = 10000

        val intent = Intent(applicationContext, RestrictionPromptActivity::class.java)

        try {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }

    fun titleButtonClicked(button: View) {
        val intent = Intent(applicationContext, InfoActivity::class.java)
        try {
            startActivity(intent)
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val titleButton = findViewById<Button>(R.id.titleButton)
        val thisIntent = intent
        val beenBefore = intent.getBooleanExtra("BeenBefore", false)

        titleButton.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.background = ContextCompat.getDrawable(this, R.drawable.pressed_title_button)
                    v.invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    v.background = ContextCompat.getDrawable(this, R.drawable.title_button)
                    v.invalidate()
                }
            }
            false
        }

        val showcaseTitle =
        if (!beenBefore) {ShowcaseView.Builder(this)
            .setTarget(ViewTarget(titleButton))
            .setStyle(R.style.ShowcaseView)
            .setContentTitle("Welcome")
            .setContentText("Press this to know about the project")
            .hideOnTouchOutside()
            .build()

        } else {
            null
        }

        if (showcaseTitle != null) {
            showcaseTitle.setButtonText("")
            showcaseTitle.show()
        }

        val startButton = findViewById<Button>(R.id.startButton)

        startButton.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.background = ContextCompat.getDrawable(this, R.drawable.pressed_go_button)
                    v.invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    v.background = ContextCompat.getDrawable(this, R.drawable.go_button)
                    v.invalidate()
                }
            }
            false
        }




    }
}


