package com.jaozinfs.moovs.tvs.domain.usecases

import com.jaozinfs.moovs.network.BaseUseCase
import com.jaozinfs.moovs.tvs.domain.TvsNetworkRepository
import com.jaozinfs.moovs.tvs.domain.model.TvDetailsUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTvDetailsUseCase(private val tvsNetworkRepository: TvsNetworkRepository) :
    BaseUseCase<Int, TvDetailsUI> {
    override fun execute(params: Int): Flow<TvDetailsUI> = flow {
        emit(tvsNetworkRepository.getTvDetails(params))
    }

}