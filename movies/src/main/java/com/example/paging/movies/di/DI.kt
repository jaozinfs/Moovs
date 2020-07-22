package com.example.paging.movies.di

import com.example.paging.movies.data.network.client.RetrofitFactory
import com.example.paging.movies.data.network.movies.MoviesApi
import com.example.paging.movies.data.network.movies.MoviesRepository
import com.example.paging.movies.data.network.movies.MoviesRepositoryImpl
import com.example.paging.movies.ui.MoviesViewModel
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
        MoviesViewModel(get())
    }
}