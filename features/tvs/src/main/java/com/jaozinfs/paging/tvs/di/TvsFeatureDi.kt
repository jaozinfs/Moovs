package com.jaozinfs.paging.tvs.di

import com.jaozinfs.paging.network.client.RetrofitFactory
import com.jaozinfs.paging.tvs.data.BASE_URL
import com.jaozinfs.paging.tvs.data.network.TvsApi
import com.jaozinfs.paging.tvs.data.network.repository.TvsNetworkRepository
import com.jaozinfs.paging.tvs.data.network.repository.TvsNetworkRepositoryImpl
import com.jaozinfs.paging.tvs.domain.usecases.GetTvDetailsUseCase
import com.jaozinfs.paging.tvs.domain.usecases.GetTvsOnAirUseCase
import com.jaozinfs.paging.tvs.domain.usecases.GetTvsPopularUseCase
import com.jaozinfs.paging.tvs.ui.viewmodels.TvsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tvsFeatureDI = module {
    //API
    single {
        RetrofitFactory.factoryApiClient(TvsApi::class.java, BASE_URL)
    }

    single {
        TvsNetworkRepositoryImpl(get()) as TvsNetworkRepository
    }

    factory {
        GetTvsPopularUseCase(get())
    }
    factory {
        GetTvDetailsUseCase(get())
    }
    factory {
        GetTvsOnAirUseCase(get())
    }

    viewModel {
        TvsViewModel(get(), get(), get())
    }
}