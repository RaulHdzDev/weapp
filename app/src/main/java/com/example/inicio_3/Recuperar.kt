package com.example.inicio_3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
    class Recuperar : AppCompatActivity() {
        private lateinit var txtEmail: EditText
        private lateinit var auth: FirebaseAuth

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_recuperar)
            txtEmail=findViewById(R.id.recEmail)
            auth= FirebaseAuth.getInstance()
        }

        fun send(view: View){
            val email=txtEmail.text.toString()

            if (!TextUtils.isEmpty(email)){
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(this){
                            task ->
                            if (task.isSuccessful){
                                startActivity(Intent(this,Iniciosesion::class.java))
                            }
                            else{
                                Toast.makeText(this,"Error en al enviar el email", Toast.LENGTH_LONG).show()
                            }
                        }
            }
        }
    }
