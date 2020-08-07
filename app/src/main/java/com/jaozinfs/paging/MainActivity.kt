package com.jaozinfs.paging

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment


class MainActivity : AppCompatActivity() {
    private val navHostFragment by lazy {
        supportFragmentManager
            .findFragmentById(R.id.main_fragmnet_container) as NavHostFragment?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        val navController = navHostFragment!!.navController

    }

    override fun onPause() {
        super.onPause()
        finish()
    }

}