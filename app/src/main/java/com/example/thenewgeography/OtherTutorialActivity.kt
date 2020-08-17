package com.example.thenewgeography

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.usage.UsageEvents
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.RelativeLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import java.net.URI

class OtherTutorialActivity : AppCompatActivity() {

    fun backButtonClicked(view: View) {
        val intent = Intent(applicationContext, MainActivity2::class.java)
        intent.putExtra("Option", "2")
        try {
            startActivity(intent)
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }

    private fun onLongTouchListener(v:View) {
        val builder = Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.window?.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        );
        builder.setOnDismissListener(DialogInterface.OnDismissListener() {
        }
        )
        val resId = v.id;
        val viewId = resources.getResourceEntryName(resId)
        val uri: Uri = Uri.parse("android.resource://$packageName/drawable/$viewId");    //path of image
        val fullImageView = ImageView(this);
        fullImageView.setImageURI(uri);
        builder.addContentView(
            fullImageView, RelativeLayout . LayoutParams (
                1200, 1200
            ));
        builder.show();
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_tutorial)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)
        val button = findViewById<FloatingActionButton>(R.id.fab)
        button.setOnClickListener { view ->
            backButtonClicked(view)
        }

        val antarcticaImage = findViewById<ImageView>(R.id.antarctica_blurred);
        val chileImage = findViewById<ImageView>(R.id.private_estate_lowres);
        val tibetImage = findViewById<ImageView>(R.id.tibet_china);
        val seymourImage = findViewById<ImageView>(R.id.seymour_avenue);
        val militaryImage = findViewById<ImageView>(R.id.military_base);
        antarcticaImage.setOnLongClickListener(){ v: View ->
            onLongTouchListener(v)
            false
        };

        chileImage.setOnLongClickListener() { v: View ->
            onLongTouchListener(v)
            false
        };

        tibetImage.setOnLongClickListener() { v: View ->
            onLongTouchListener(v)
            false
        };

        seymourImage.setOnLongClickListener() { v: View ->
            onLongTouchListener(v)
            false
        };

        militaryImage.setOnLongClickListener() { v: View ->
            onLongTouchListener(v)
            false
        };

    }
}