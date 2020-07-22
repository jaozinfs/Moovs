package com.example.paging

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.paging.movies.di.moviesModules
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            modules(moviesModules)
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }

    fun install() {
        val splitInstallManager = SplitInstallManagerFactory.create(applicationContext)

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
            i.setClassName(BuildConfig.APPLICATION_ID, "com.example.paging3.ui.ui.MoviesActivity")
            startActivity(i)
        } else {
            Log.e("Teste", "Registration feature is not installed")
        }
    }
}