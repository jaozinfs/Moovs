package com.jaozinfs.moovs.tvs.data.network.repository

import com.jaozinfs.moovs.network.NetworkRepositoryManager
import com.jaozinfs.moovs.tvs.data.mappers.toUI
import com.jaozinfs.moovs.tvs.data.network.TvsApi
import com.jaozinfs.moovs.tvs.domain.TvsNetworkRepository
import com.jaozinfs.moovs.tvs.domain.model.EpisodeUI
import com.jaozinfs.moovs.tvs.domain.model.SeasonDetailsUI
import com.jaozinfs.moovs.tvs.domain.model.TvDetailsUI
import com.jaozinfs.moovs.tvs.domain.model.TvUI

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

    override suspend fun getTvEpisodesBySeasons(tvId: Int, seasonId: Int): SeasonDetailsUI =
        NetworkRepositoryManager.getData {
            tvsApi.getTvSeasonEpisodes(tvId, seasonId)
        }.toUI()

    override suspend fun getTvSeasonEpisode(tvId: Int, seasonId: Int, episodeID: Int): EpisodeUI =
        NetworkRepositoryManager.getData {
            tvsApi.getTvSeasonEpisode(tvId, seasonId, episodeID)
        }.toUI()
}