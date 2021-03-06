package com.jaozinfs.moovs.tvs.domain.usecases

import com.jaozinfs.moovs.network.BaseUseCase
import com.jaozinfs.moovs.tvs.domain.TvsNetworkRepository
import com.jaozinfs.moovs.tvs.domain.model.EpisodeUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSeasonEpisodeUseCase(private val tvsNetworkRepository: TvsNetworkRepository) :
    BaseUseCase<GetSeasonEpisodeUseCase.Params, EpisodeUI> {

    class Params(val tvID: Int, val seasonID: Int, val episodeID: Int)

    override fun execute(params: Params): Flow<EpisodeUI> =
        flow {
            emit(
                tvsNetworkRepository.getTvSeasonEpisode(
                    params.tvID,
                    params.seasonID,
                    params.episodeID
                )
            )
        }

}