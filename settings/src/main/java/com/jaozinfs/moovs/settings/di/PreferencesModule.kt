package com.jaozinfs.moovs.settings.di

import com.jaozinfs.moovs.settings.settings.SettingsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val preferenceModule = module {
    viewModel { SettingsViewModel(get()) }
}