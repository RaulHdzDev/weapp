package com.example.inicio_3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Iniciosesion : AppCompatActivity(){

    private lateinit var txtUser: EditText
    private lateinit var txtPass: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_iniciosesion)


        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener(View.OnClickListener {
            loginUser()
        })

        val btnRegistro = findViewById<TextView>(R.id.btnRegistro)
        btnRegistro.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@Iniciosesion, Registro::class.java))
        })

        val btnRecuperarContraseña = findViewById<TextView>(R.id.btnRecuperarContraseña)
        btnRecuperarContraseña.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@Iniciosesion, Recuperar::class.java))
        })

        txtUser=findViewById(R.id.txtUser)
        txtPass=findViewById(R.id.txtPass)
        auth= FirebaseAuth.getInstance()
    }

    private fun loginUser(){
        val user:String=txtUser.text.toString()
        val pass:String=txtPass.text.toString()

        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pass)){
            auth?.signInWithEmailAndPassword(user,pass)
                    ?.addOnCompleteListener(this){
                        task ->

                        if (task.isSuccessful){
                            abrirOpciones()
                        }
                        else{
                            Toast.makeText(this,"Error en el inicio de sesión", Toast.LENGTH_LONG).show()
                        }
                    }
        }
    }

    private fun abrirOpciones(){
        startActivity(Intent(this@Iniciosesion,MainActivity::class.java))
        finish()
    }

}
