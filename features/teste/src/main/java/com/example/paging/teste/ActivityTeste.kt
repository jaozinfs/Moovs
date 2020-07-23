package com.example.paging.teste

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ActivityTeste : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Toast.makeText(this, "OOPA", Toast.LENGTH_LONG).show()
    }
}