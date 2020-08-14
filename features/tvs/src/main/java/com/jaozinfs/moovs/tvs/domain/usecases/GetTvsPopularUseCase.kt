package com.jaozinfs.moovs.tvs.domain.usecases

import com.jaozinfs.moovs.network.BaseUseCase
import com.jaozinfs.moovs.tvs.domain.TvsNetworkRepository
import com.jaozinfs.moovs.tvs.domain.model.TvUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTvsPopularUseCase(private val tvsNetworkRepository: TvsNetworkRepository) :
    BaseUseCase<Unit?, List<TvUI>> {
    override fun execute(params: Unit?): Flow<List<TvUI>> = flow {
        emit(tvsNetworkRepository.getTvsPopular())
    }
}