package com.jaozinfs.moovs.tvs.domain

import com.jaozinfs.moovs.tvs.domain.model.TvDetailsUI
import com.jaozinfs.moovs.tvs.domain.model.TvUI
import kotlinx.coroutines.flow.Flow

interface TvsLocalRepository {
    suspend fun getTvsFavorited(): List<TvUI>
    suspend fun saveTvDetailsUI(tvDetailsUI: TvDetailsUI): Long
    suspend fun removeTvFavorited(tvId: Int): Int
    fun checkTvFavorite(tvId: Int): Flow<Boolean>
}
