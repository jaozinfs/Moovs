package com.jaozinfs.paging.movies.di

import androidx.room.Room
import com.jaozinfs.paging.database.MoviesDatabase
import com.jaozinfs.paging.movies.data.cache.GenreCacheRepository
import com.jaozinfs.paging.movies.data.cache.GenreCacheRepositoryImpl
import com.jaozinfs.paging.movies.data.local.MoviesLocalRepositoryImpl
import com.jaozinfs.paging.movies.data.network.BASE_URL
import com.jaozinfs.paging.movies.data.network.MoviesApi
import com.jaozinfs.paging.movies.data.network.MoviesRepositoryImpl
import com.jaozinfs.paging.movies.domain.movies.MoviesLocalRepository
import com.jaozinfs.paging.movies.domain.movies.MoviesRepository
import com.jaozinfs.paging.movies.domain.usecase.*
import com.jaozinfs.paging.movies.ui.MoviesFavoritesViewModel
import com.jaozinfs.paging.movies.ui.MoviesViewModel
import com.jaozinfs.paging.network.client.RetrofitFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moviesModules = module {

    //API
    single {
        RetrofitFactory.factoryApiClient(MoviesApi::class.java, BASE_URL)
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            MoviesDatabase::class.java,
            "MoviesDB"
        ).build()
    }

    //repository's
    single {
        MoviesRepositoryImpl(get()) as MoviesRepository
    }
    single {
        GenreCacheRepositoryImpl() as GenreCacheRepository
    }
    single {
        MoviesLocalRepositoryImpl(get()) as MoviesLocalRepository
    }

    //Use cases
    factory {
        GetGenresUseCase(get(), get())
    }
    factory {
        GetGenresUseResultCase(get(), get())
    }
    factory {
        GetMovieDetailsUseCase(get())
    }
    factory {
        GetMovieImagesUseCase(get())
    }
    factory {
        SaveMovieFavoriteUseCase(get())
    }
    factory {
        CheckIsMovieFavoritedUseCase(get())
    }
    factory {
        GetMoviesFavoritesUseCase(get())
    }
    factory {
        RemoveMovieFavoriteUseCase(get())
    }

    //ViewModels
    viewModel {
        MoviesViewModel(get(), get(), get(), get(), get(), get(), get(), get())
    }
    viewModel {
        MoviesFavoritesViewModel(get())
    }
}