package com.jaozinfs.paging.tvs.domain.usecases

import com.jaozinfs.paging.network.BaseUseCase
import com.jaozinfs.paging.tvs.domain.TvsLocalRepository
import com.jaozinfs.paging.tvs.domain.TvsNetworkRepository
import com.jaozinfs.paging.tvs.domain.model.TvDetailsUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CheckTvIsFavoriteUseCase(private val tvsLocalRepository: TvsLocalRepository) :
    BaseUseCase<Int, Boolean> {
    override fun execute(params: Int): Flow<Boolean> = tvsLocalRepository.checkTvFavorite(params)

}