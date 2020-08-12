package com.jaozinfs.paging.tvs.domain

import com.jaozinfs.paging.tvs.domain.model.TvDetailsUI
import com.jaozinfs.paging.tvs.domain.model.TvUI
import kotlinx.coroutines.flow.Flow

interface TvsLocalRepository {
    suspend fun getTvsFavorited(): List<TvUI>
    suspend fun saveTvDetailsUI(tvDetailsUI: TvDetailsUI): Long
    suspend fun removeTvFavorited(tvId: Int): Int
    fun checkTvFavorite(tvId: Int): Flow<Boolean>
}
