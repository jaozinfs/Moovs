package com.jaozinfs.moovs.tvs.domain.usecases

import com.jaozinfs.moovs.network.BaseUseCase
import com.jaozinfs.moovs.tvs.domain.TvsLocalRepository
import com.jaozinfs.moovs.tvs.domain.TvsNetworkRepository
import com.jaozinfs.moovs.tvs.domain.model.TvDetailsUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CheckTvIsFavoriteUseCase(private val tvsLocalRepository: TvsLocalRepository) :
    BaseUseCase<Int, Boolean> {
    override fun execute(params: Int): Flow<Boolean> = tvsLocalRepository.checkTvFavorite(params)

}