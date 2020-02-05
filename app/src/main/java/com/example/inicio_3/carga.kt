package com.example.inicio_3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class carga : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 2630 //3 seconds

    private lateinit var auth: FirebaseAuth

    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            checaSesion()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carga)

        mDelayHandler = Handler()

        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

    }

    fun checaSesion() {
        auth = FirebaseAuth.getInstance()
        val authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser != null) {
                val ref = FirebaseDatabase.getInstance().getReference();
                //Perfil_fragment.usuario = firebaseUser.displayName
                //Perfil_fragment.numero = "0"
                Perfil_fragment.correo = firebaseUser.email

                ref.child("Users").addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for(snap in dataSnapshot.children){
                            if (snap.value == firebaseUser.uid) {
                                Perfil_fragment.numero = snap.child(firebaseUser.uid).child("Telefono").getValue().toString()
                                Perfil_fragment.usuario = snap.child(firebaseUser.uid).child("Nombre").getValue().toString()
                            }
                        }
                    }
                })

                startActivity(Intent(this@carga, MainActivity::class.java))
            } else {
                startActivity(Intent(this@carga, Iniciosesion::class.java))
            }
            finish()
        }
        auth.addAuthStateListener(authListener);
    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
        super.onDestroy()
    }
}
