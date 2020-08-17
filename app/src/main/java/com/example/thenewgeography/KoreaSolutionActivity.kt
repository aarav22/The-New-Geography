package com.example.thenewgeography

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

class KoreaSolutionActivity : AppCompatActivity() {

    fun backButtonClicked(view: View) {
        val intent = Intent(applicationContext, RestrictionPromptActivity::class.java)
        try {
            intent.putExtra("Option", "1")
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_korea_solution)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)
        val button = findViewById<FloatingActionButton>(R.id.fab)
        button.setOnClickListener { view ->
            backButtonClicked(view)
        }
    }
}