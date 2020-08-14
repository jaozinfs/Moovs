package com.jaozinfs.moovs.tvs.domain

import com.jaozinfs.moovs.tvs.domain.model.EpisodeUI
import com.jaozinfs.moovs.tvs.domain.model.SeasonDetailsUI
import com.jaozinfs.moovs.tvs.domain.model.TvDetailsUI
import com.jaozinfs.moovs.tvs.domain.model.TvUI

interface TvsNetworkRepository {
    suspend fun getTvsPopular(): List<TvUI>
    suspend fun getTvsOnAir(): List<TvUI>
    suspend fun getTvDetails(tvId: Int): TvDetailsUI
    suspend fun getTvEpisodesBySeasons(tvId: Int, seasonId: Int): SeasonDetailsUI
    suspend fun getTvSeasonEpisode(tvId: Int, seasonId: Int, episodeID: Int): EpisodeUI
}
