package com.example.thenewgeography

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.TextView

class OtherSolutionActivity : AppCompatActivity() {
    fun backButtonClicked(view: View) {
        val intent = Intent(applicationContext, RestrictionPromptActivity::class.java)
        intent.putExtra("Option", "2")
        try {
            startActivity(intent)
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_solution)
        val writeupText = findViewById<TextView>(R.id.textView)
        writeupText.movementMethod = ScrollingMovementMethod()
    }
}