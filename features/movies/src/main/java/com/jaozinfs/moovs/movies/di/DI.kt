package com.jaozinfs.moovs.movies.di

import androidx.room.Room
import com.jaozinfs.moovs.database.MoviesDatabase
import com.jaozinfs.moovs.movies.data.cache.GenreCacheRepository
import com.jaozinfs.moovs.movies.data.cache.GenreCacheRepositoryImpl
import com.jaozinfs.moovs.movies.data.local.MoviesLocalRepositoryImpl
import com.jaozinfs.moovs.movies.data.network.BASE_URL
import com.jaozinfs.moovs.movies.data.network.MoviesApi
import com.jaozinfs.moovs.movies.data.network.MoviesRepositoryImpl
import com.jaozinfs.moovs.movies.domain.movies.MoviesLocalRepository
import com.jaozinfs.moovs.movies.domain.movies.MoviesRepository
import com.jaozinfs.moovs.movies.domain.usecase.cinema.GetMoviesCinemaUseCase
import com.jaozinfs.moovs.movies.domain.usecase.favorites.CheckIsMovieFavoritedUseCase
import com.jaozinfs.moovs.movies.domain.usecase.favorites.GetMoviesFavoritesUseCase
import com.jaozinfs.moovs.movies.domain.usecase.favorites.RemoveMovieFavoriteUseCase
import com.jaozinfs.moovs.movies.domain.usecase.favorites.SaveMovieFavoriteUseCase
import com.jaozinfs.moovs.movies.domain.usecase.movies.GetGenresUseCase
import com.jaozinfs.moovs.movies.domain.usecase.movies.GetGenresUseResultCase
import com.jaozinfs.moovs.movies.domain.usecase.movies.GetMovieDetailsUseCase
import com.jaozinfs.moovs.movies.domain.usecase.movies.GetMovieImagesUseCase
import com.jaozinfs.moovs.movies.ui.MoviesCinemaViewModel
import com.jaozinfs.moovs.movies.ui.MoviesFavoritesViewModel
import com.jaozinfs.moovs.movies.ui.MoviesViewModel
import com.jaozinfs.moovs.network.client.RetrofitFactory
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
        GetGenresUseCase(
            get(),
            get()
        )
    }
    factory {
        GetGenresUseResultCase(
            get(),
            get()
        )
    }
    factory {
        GetMovieDetailsUseCase(
            get()
        )
    }
    factory {
        GetMovieImagesUseCase(
            get()
        )
    }
    factory {
        SaveMovieFavoriteUseCase(
            get()
        )
    }
    factory {
        CheckIsMovieFavoritedUseCase(
            get()
        )
    }
    factory {
        GetMoviesFavoritesUseCase(
            get()
        )
    }
    factory {
        RemoveMovieFavoriteUseCase(
            get()
        )
    }
    factory {
        GetMoviesCinemaUseCase(
            get()
        )
    }

    //ViewModels
    viewModel {
        MoviesViewModel(get(), get(), get(), get(), get(), get(), get(), get())
    }
    viewModel {
        MoviesFavoritesViewModel(get())
    }
    viewModel {
        MoviesCinemaViewModel(get(), get())
    }
}