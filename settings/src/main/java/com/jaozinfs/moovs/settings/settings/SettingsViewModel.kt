package com.jaozinfs.moovs.settings.settings

import androidx.lifecycle.ViewModel
import com.jaozinfs.moovs.darktheme.DarkThemeManager


class SettingsViewModel(
    private val darkThemeManager: DarkThemeManager
) : ViewModel() {

    fun setCurrentTheme(theme: String) {
        darkThemeManager.changeCurrentTheme(theme.toInt())
    }
}