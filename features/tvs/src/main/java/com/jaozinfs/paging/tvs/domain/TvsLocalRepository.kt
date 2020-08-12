package com.jaozinfs.paging.tvs.domain

import com.jaozinfs.paging.tvs.domain.model.TvDetailsUI
import com.jaozinfs.paging.tvs.domain.model.TvUI

interface TvsLocalRepository {
    suspend fun getTvsFavorited(): List<TvUI>
    suspend fun saveTvDetailsUI(tvDetailsUI: TvDetailsUI): Long
}
