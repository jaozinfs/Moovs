package com.jaozinfs.paging.tvs.domain.usecases

import com.jaozinfs.paging.network.BaseUseCase
import com.jaozinfs.paging.tvs.domain.TvsLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoveTvFavoriteUseCase(
    private val tvsLocalRepository: TvsLocalRepository
) :
    BaseUseCase<Int, Int> {
    override fun execute(params: Int): Flow<Int> = flow {
        emit(tvsLocalRepository.removeTvFavorited(params))
    }
}