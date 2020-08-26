package com.jaozinfs.moovs.settings.settings

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.jaozinfs.moovs.darktheme.DarkThemeManager
import com.jaozinfs.moovs.settings.R
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class SettingsFragment : PreferenceFragmentCompat() {
    private val settingsViewModel: SettingsViewModel by viewModel()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_screen, rootKey)
        (findPreference("darkTheme") as? ListPreference)?.let {
            it.setOnPreferenceChangeListener { _, newValue ->
                settingsViewModel.setCurrentTheme(newValue as String)
                true
            }
        }
    }
}