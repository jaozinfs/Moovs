package com.jaozinfs.paging

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.play.core.splitcompat.SplitCompat
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    /**
     * Initilize Dynimic SplitCompat
     */
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }

    /**
     * Initialize Koin with Android Application Context Singleton
     *
     */
    private fun startKoin() = startKoin {
        androidContext(this@MainApp)
    }


}