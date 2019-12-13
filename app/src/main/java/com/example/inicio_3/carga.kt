package com.example.inicio_3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth

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

    fun checaSesion(){
        val auth = FirebaseAuth.getInstance()
        val authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser != null) {
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
