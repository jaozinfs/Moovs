package com.example.paging3.ui.di

import com.example.paging3.data.repository.network.client.RetrofitFactory
import com.example.paging3.data.repository.network.movies.MoviesApi
import com.example.paging3.data.repository.network.movies.MoviesRepository
import com.example.paging3.data.repository.network.movies.MoviesRepositoryImpl
import com.example.paging3.ui.MainActivityViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moviesModules = module {
    single {
        RetrofitFactory.factoryApiClient(MoviesApi::class.java, "https://api.themoviedb.org/3/")
    }
    single {
        MoviesRepositoryImpl(get()) as MoviesRepository
    }
    viewModel {
        MainActivityViewModel(get())
    }
}