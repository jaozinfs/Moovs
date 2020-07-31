package com.jaozinfs.paging.movies.di

import com.jaozinfs.paging.movies.data.cache.GenreCacheRepository
import com.jaozinfs.paging.movies.data.cache.GenreCacheRepositoryImpl
import com.jaozinfs.paging.movies.data.network.BASE_URL
import com.jaozinfs.paging.movies.data.network.MoviesApi
import com.jaozinfs.paging.movies.data.network.MoviesRepositoryImpl
import com.jaozinfs.paging.movies.domain.movies.MoviesRepository
import com.jaozinfs.paging.movies.domain.usecase.GetGenresUseCase
import com.jaozinfs.paging.movies.domain.usecase.GetMovieDetailsUseCase
import com.jaozinfs.paging.movies.domain.usecase.GetMovieImagesUseCase
import com.jaozinfs.paging.movies.ui.MoviesViewModel
import com.jaozinfs.paging.network.client.RetrofitFactory
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moviesModules = module {

    //API
    single {
        RetrofitFactory.factoryApiClient(MoviesApi::class.java, BASE_URL)
    }

    //repository's
    single {
        MoviesRepositoryImpl(get()) as MoviesRepository
    }
    single {

        GenreCacheRepositoryImpl() as GenreCacheRepository
    }

    //Use cases
    factory {
        GetGenresUseCase(get(), get())
    }
    factory {
        GetMovieDetailsUseCase(get())
    }
    factory {
        GetMovieImagesUseCase(get())
    }

    //ViewModels
    viewModel {
        MoviesViewModel(get(), get(), get(), get())
    }
}