package com.jaozinfs.paging.tvs.di

import androidx.room.Room
import com.jaozinfs.paging.database.MoviesDatabase
import com.jaozinfs.paging.network.client.RetrofitFactory
import com.jaozinfs.paging.tvs.data.BASE_URL
import com.jaozinfs.paging.tvs.data.local.TvLocalRepositoryImpl
import com.jaozinfs.paging.tvs.data.network.TvsApi
import com.jaozinfs.paging.tvs.data.network.repository.TvsNetworkRepositoryImpl
import com.jaozinfs.paging.tvs.domain.TvsLocalRepository
import com.jaozinfs.paging.tvs.domain.TvsNetworkRepository
import com.jaozinfs.paging.tvs.domain.usecases.*
import com.jaozinfs.paging.tvs.ui.viewmodels.TvsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tvsFeatureDI = module {
    //API
    single {
        RetrofitFactory.factoryApiClient(TvsApi::class.java, BASE_URL)
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            MoviesDatabase::class.java,
            "MoviesDB"
        ).build()
    }
    single {
        TvsNetworkRepositoryImpl(get()) as TvsNetworkRepository
    }
    single {
        TvLocalRepositoryImpl(get()) as TvsLocalRepository
    }

    //network
    factory {
        GetTvsPopularUseCase(get())
    }
    factory {
        GetTvDetailsUseCase(get())
    }
    factory {
        GetTvsOnAirUseCase(get())
    }
    factory {
        CheckTvIsFavoriteUseCase(get())
    }

    //local
    factory {
        SaveTvFavoriteUseCase(get())
    }
    factory {
        RemoveTvFavoriteUseCase(get())
    }
    factory {
        GetTvsFavoritedUseCase(get())
    }
    factory {
        GetSeasonEpisodesUseCase(get())
    }
    factory {
        GetSeasonEpisodeUseCase(get())
    }

    viewModel {
        TvsViewModel(get(), get(), get(), get(), get(), get(),get(), get(), get())
    }
}