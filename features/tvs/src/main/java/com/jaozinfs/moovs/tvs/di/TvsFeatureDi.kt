package com.jaozinfs.moovs.tvs.di

import androidx.room.Room
import com.jaozinfs.moovs.database.MoviesDatabase
import com.jaozinfs.moovs.network.client.RetrofitFactory
import com.jaozinfs.moovs.tvs.data.BASE_URL
import com.jaozinfs.moovs.tvs.data.local.TvLocalRepositoryImpl
import com.jaozinfs.moovs.tvs.data.network.TvsApi
import com.jaozinfs.moovs.tvs.data.network.repository.TvsNetworkRepositoryImpl
import com.jaozinfs.moovs.tvs.domain.TvsLocalRepository
import com.jaozinfs.moovs.tvs.domain.TvsNetworkRepository
import com.jaozinfs.moovs.tvs.domain.usecases.*
import com.jaozinfs.moovs.tvs.ui.viewmodels.TvsViewModel
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