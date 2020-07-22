package com.example.paging3

import android.app.Application
import com.example.paging3.ui.di.moviesModules
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
}