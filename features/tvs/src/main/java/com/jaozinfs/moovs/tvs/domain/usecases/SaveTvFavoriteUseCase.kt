package com.jaozinfs.moovs.tvs.domain.usecases

import android.util.Log
import com.jaozinfs.moovs.network.BaseUseCase
import com.jaozinfs.moovs.tvs.domain.TvsLocalRepository
import com.jaozinfs.moovs.tvs.domain.model.TvDetailsUI
import com.jaozinfs.moovs.tvs.domain.model.TvUI
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