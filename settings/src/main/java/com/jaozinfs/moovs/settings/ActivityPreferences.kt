package com.jaozinfs.moovs.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jaozinfs.moovs.settings.di.preferenceModule
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.koinApplication

class ActivityPreferences : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        injectModules()
    }

    override fun onDestroy() {
        super.onDestroy()
        koinApplication {
            unloadKoinModules(preferenceModule)
        }
    }

    private fun injectModules() = koinApplication {
        loadKoinModules(preferenceModule)
    }
}