package com.jaozinfs.paging.tvs.domain

import com.jaozinfs.paging.tvs.domain.model.TvDetailsUI
import com.jaozinfs.paging.tvs.domain.model.TvUI

interface TvsNetworkRepository {
    suspend fun getTvsPopular(): List<TvUI>
    suspend fun getTvsOnAir(): List<TvUI>
    suspend fun getTvDetails(tvId: Int): TvDetailsUI
}
