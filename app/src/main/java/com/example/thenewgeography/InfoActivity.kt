package com.example.thenewgeography

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.TextView

class InfoActivity : AppCompatActivity() {
    fun backButtonClicked(view: View) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("BeenBefore", true)
        try {
            startActivity(intent)
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val writeupText = findViewById<TextView>(R.id.textView)
        writeupText.movementMethod = ScrollingMovementMethod()
    }
}