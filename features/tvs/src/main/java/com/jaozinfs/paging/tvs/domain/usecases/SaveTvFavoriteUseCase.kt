package com.jaozinfs.paging.tvs.domain.usecases

import android.util.Log
import com.jaozinfs.paging.network.BaseUseCase
import com.jaozinfs.paging.tvs.domain.TvsLocalRepository
import com.jaozinfs.paging.tvs.domain.model.TvDetailsUI
import com.jaozinfs.paging.tvs.domain.model.TvUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveTvFavoriteUseCase(
    private val tvsLocalRepository: TvsLocalRepository
) :
    BaseUseCase<TvDetailsUI, Long> {
    override fun execute(params: TvDetailsUI): Flow<Long> = flow {
        emit(tvsLocalRepository.saveTvDetailsUI(params))
    }
}