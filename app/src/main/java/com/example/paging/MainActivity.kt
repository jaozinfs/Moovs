package com.example.paging

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        install()
    }
    fun install() {
        val splitInstallManager = SplitInstallManagerFactory.create(this)

        val request = SplitInstallRequest.newBuilder()
            .addModule("movies")
            .build()

        splitInstallManager.startInstall(request)
            .addOnSuccessListener {
                Log.d("Teste", it.toString())
            }
            .addOnFailureListener {
                Log.e("Teste", it.toString())
            }
        if (splitInstallManager.installedModules.contains("movies")) {
            val i = Intent()
            i.setClassName(BuildConfig.APPLICATION_ID, "com.example.paging.movies.ui.MoviesActivity")
            startActivity(i)
        } else {
            Log.e("Teste", "Registration feature is not installed")
        }
    }
}