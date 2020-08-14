package com.jaozinfs.moovs

import android.app.Application
import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompat
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()

        if (BuildConfig.DEBUG) {
            startTimber()
        }
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

    private fun startTimber() {
        Timber.plant(Timber.DebugTree())
    }

}