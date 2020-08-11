package com.jaozinfs.paging.tvs.domain.usecases

import com.jaozinfs.paging.network.BaseUseCase
import com.jaozinfs.paging.tvs.data.network.repository.TvsNetworkRepository
import com.jaozinfs.paging.tvs.domain.model.TvDetailsUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTvDetailsUseCase(private val tvsNetworkRepository: TvsNetworkRepository) :
    BaseUseCase<Int, TvDetailsUI> {
    override fun execute(params: Int): Flow<TvDetailsUI> = flow {
        emit(tvsNetworkRepository.getTvDetails(params))
    }

}