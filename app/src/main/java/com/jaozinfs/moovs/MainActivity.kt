package com.jaozinfs.moovs

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.InstallStatus
import com.jaozinfs.moovs.extensions.setupWithNavController
import kotlinx.android.synthetic.main.main.*


class MainActivity : AppCompatActivity() {

    private val navHostFragment by lazy {
        supportFragmentManager
            .findFragmentById(R.id.main_fragmnet_container) as NavHostFragment?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        setupNavigationBar()
    }


    private fun setupNavigationBar() {
        navHostFragment?.let {
            bottom_navigation.setupWithNavController(it.navController)
        }
    }


}