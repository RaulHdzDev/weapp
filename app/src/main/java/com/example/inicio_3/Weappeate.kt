package com.example.inicio_3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button


    class Weappeate : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_weappeate)

            val btnContinuarWeappeate = findViewById<Button>(R.id.btnContinuarWeappeate)
            btnContinuarWeappeate.setOnClickListener(View.OnClickListener {
                startActivity(Intent(this@Weappeate, MainActivity::class.java))
                finish()
            })
        }
    }