package com.example.paging.movies.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.paging.movies.R
import kotlinx.android.synthetic.main.activity_movies.*
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesActivity : AppCompatActivity() {
    private val viewmodel: MoviesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        setSupportActionBar(toolbar)
    }

}