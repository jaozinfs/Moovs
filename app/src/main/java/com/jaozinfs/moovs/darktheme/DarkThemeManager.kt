package com.jaozinfs.moovs.darktheme

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate.NightMode
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.core.content.edit

interface DarkThemeManager {
    fun changeCurrentTheme(@NightMode themeOptions: Int)
    fun setCurrentTheme()
}

class DarkThemeManagerImpl(private val preferenceManager: SharedPreferences) :
    DarkThemeManager {

    override fun changeCurrentTheme(@NightMode themeOptions: Int) {
        editCurrentThemeSharedPreferences(themeOptions)
        setTheme(themeOptions)
    }

    override fun setCurrentTheme() {
        val theme = getSharedPreferencesCurrentTheme().toInt()
        setTheme(theme)
    }


    private fun getSharedPreferencesCurrentTheme(): String {
        val defaultValue =  "-1"
        return preferenceManager.getString(
            "darkTheme",
           defaultValue
        ) ?: defaultValue
    }

    private fun editCurrentThemeSharedPreferences(currentTheme: Int) {
        preferenceManager.edit {
            putString("darkTheme", currentTheme.toString())
        }
    }

    private fun setTheme(@NightMode darkThemeOptions: Int) {
        setDefaultNightMode(
            darkThemeOptions
        )
    }
}