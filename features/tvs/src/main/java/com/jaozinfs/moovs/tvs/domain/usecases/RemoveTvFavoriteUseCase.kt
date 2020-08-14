package com.jaozinfs.moovs.tvs.domain.usecases

import com.jaozinfs.moovs.network.BaseUseCase
import com.jaozinfs.moovs.tvs.domain.TvsLocalRepository
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