package com.jaozinfs.paging.tvs.data.network.repository

import com.jaozinfs.paging.network.NetworkRepositoryManager
import com.jaozinfs.paging.tvs.data.mappers.toUI
import com.jaozinfs.paging.tvs.data.network.TvsApi
import com.jaozinfs.paging.tvs.domain.TvsNetworkRepository
import com.jaozinfs.paging.tvs.domain.model.TvDetailsUI
import com.jaozinfs.paging.tvs.domain.model.TvUI

class TvsNetworkRepositoryImpl(private val tvsApi: TvsApi) :
    TvsNetworkRepository {

    override suspend fun getTvsPopular(): List<TvUI> = NetworkRepositoryManager.getData {
        tvsApi.getTvsPopular()
    }.results.map {
        it.toUI()
    }

    override suspend fun getTvsOnAir(): List<TvUI> = NetworkRepositoryManager.getData {
        tvsApi.getTvsOnAir()
    }.results.map {
        it.toUI()
    }

    override suspend fun getTvDetails(tvId: Int): TvDetailsUI = NetworkRepositoryManager.getData {
        tvsApi.getTvDetails(tvId)
    }.toUI()
}