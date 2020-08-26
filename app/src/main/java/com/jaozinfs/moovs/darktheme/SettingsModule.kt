package com.jaozinfs.moovs.darktheme

import androidx.preference.PreferenceManager
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val settingsModule = module {
    single {
        DarkThemeManagerImpl(
            PreferenceManager.getDefaultSharedPreferences(androidApplication())
        ) as DarkThemeManager
    }
}