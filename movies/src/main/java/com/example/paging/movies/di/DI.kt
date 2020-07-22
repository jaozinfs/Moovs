package com.example.paging.movies.di

import com.example.paging.movies.data.network.BASE_URL
import com.example.paging.movies.data.network.movies.MoviesApi
import com.example.paging.movies.data.network.movies.MoviesRepository
import com.example.paging.movies.data.network.movies.MoviesRepositoryImpl
import com.example.paging.movies.ui.MoviesViewModel
import com.example.paging.network.client.RetrofitFactory
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moviesModules = module {

    single {
        RetrofitFactory.factoryApiClient(MoviesApi::class.java, BASE_URL)
    }

    single {
        MoviesRepositoryImpl(get()) as MoviesRepository
    }

    viewModel {
        MoviesViewModel(get())
    }
}