package com.example.inicio_3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Registro : AppCompatActivity() {

    private lateinit var txtName: EditText
    private lateinit var txtPassword: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtTel: EditText

    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        txtName=findViewById(R.id.txtName)
        txtPassword=findViewById(R.id.txtPassword)
        txtEmail=findViewById(R.id.txtEmail)
        txtTel=findViewById(R.id.txtTel)

        database= FirebaseDatabase.getInstance()
        auth= FirebaseAuth.getInstance()

        dbReference=database.reference.child("Users")

        val btnRegitrar = findViewById<Button>(R.id.btnRegistrar)
        btnRegitrar.setOnClickListener(View.OnClickListener {
            crearCuenta()
        })

    }


    private fun crearCuenta(){
        val name:String=txtName.text.toString()
        val pass:String=txtPassword.text.toString()
        val email:String=txtEmail.text.toString()
        val tel:String=txtTel.text.toString()

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(tel)){
            auth.createUserWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(this){
                        task ->

                        if (task.isSuccessful){
                            val user: FirebaseUser?=auth.currentUser
                            verificarEmail(user)

                            val userBD=dbReference.child(user?.uid.toString())

                            userBD.child("Nombre").setValue(name)
                            userBD.child("Contraseña").setValue(pass)
                            userBD.child("Email").setValue(email)
                            userBD.child("Telefono").setValue(tel)

                            accion()
                        }
                    }
        }
    }

    private fun accion(){
        startActivity(Intent(this@Registro,Weappeate::class.java))
        finish()
    }

    private fun verificarEmail(user: FirebaseUser?){
        user?.sendEmailVerification()
                ?.addOnCompleteListener(this){
                    task ->

                    if (task.isSuccessful){
                        Toast.makeText(this,"Email enviado", Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this,"Error al enviar", Toast.LENGTH_LONG).show()
                    }
                }
    }



}
