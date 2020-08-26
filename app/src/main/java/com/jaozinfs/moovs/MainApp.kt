package com.jaozinfs.moovs

import android.app.Application
import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompat
import com.jaozinfs.moovs.darktheme.DarkThemeManager
import com.jaozinfs.moovs.darktheme.settingsModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApp : Application() {
    private val darkThemeManager: DarkThemeManager by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin()

        if (BuildConfig.DEBUG) {
            startTimber()
        }

        verifyTheme()
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
        modules(settingsModule)
    }

    private fun startTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun verifyTheme() {
        darkThemeManager.setCurrentTheme()
    }

}