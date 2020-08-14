package com.jaozinfs.moovs

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startMainApp()
    }

    private fun startMainApp() {
        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
        finish()
    }


}