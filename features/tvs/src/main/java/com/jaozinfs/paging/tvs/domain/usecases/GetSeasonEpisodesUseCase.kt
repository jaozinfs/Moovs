package com.jaozinfs.paging.tvs.domain.usecases

import com.jaozinfs.paging.network.BaseUseCase
import com.jaozinfs.paging.tvs.domain.TvsNetworkRepository
import com.jaozinfs.paging.tvs.domain.model.SeasonDetailsUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSeasonEpisodesUseCase(private val tvsNetworkRepository: TvsNetworkRepository) :
    BaseUseCase<GetSeasonEpisodesUseCase.Params, SeasonDetailsUI> {

    class Params(val tvId: Int, val seasonID: Int)

    override fun execute(params: Params): Flow<SeasonDetailsUI> =
        flow {
            emit(tvsNetworkRepository.getTvEpisodesBySeasons(params.tvId, params.seasonID))
        }

}