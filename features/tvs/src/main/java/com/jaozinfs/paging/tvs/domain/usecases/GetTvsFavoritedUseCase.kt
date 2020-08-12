package com.jaozinfs.paging.tvs.domain.usecases

import com.jaozinfs.paging.network.BaseUseCase
import com.jaozinfs.paging.tvs.domain.TvsLocalRepository
import com.jaozinfs.paging.tvs.domain.model.TvUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTvsFavoritedUseCase(
    private val tvsLocalRepository: TvsLocalRepository
) : BaseUseCase<Unit, List<TvUI>> {

    override fun execute(params: Unit): Flow<List<TvUI>> = flow {
        emit(tvsLocalRepository.getTvsFavorited())
    }
}