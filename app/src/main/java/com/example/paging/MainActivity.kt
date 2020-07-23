package com.example.paging

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.main.*

class MainActivity : AppCompatActivity() {
//    private val navHostFragment by lazy {
//        supportFragmentManager
//            .findFragmentById(R.id.main_fragmnet_container) as NavHostFragment?
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        teste.setOnClickListener {
            main_fragmnet_container.findNavController().navigate(R.id.nav_features_movies)
        }

    }


}