package com.example.paging3

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.paging3.ui.MainActivityViewModel
import com.example.paging3.ui.MoviesAdapter
import com.example.paging3.ui.setClickListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewmodel: MainActivityViewModel by viewModel()

    private val adapter = MoviesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        rv.adapter = adapter

        fab.setClickListener(::refresh)

        lifecycleScope.launch {
            viewmodel.getMovies().collectLatest {
                Log.d("Teste", "Collected")
                adapter.submitData(it)
            }
        }

    }

    fun refresh() {
        adapter.refresh()
    }
}