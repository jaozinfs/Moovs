package com.jaozinfs.paging.tvs.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.jaozinfs.paging.tvs.domain.model.TvUI
import com.jaozinfs.paging.tvs.domain.usecases.GetTvDetailsUseCase
import com.jaozinfs.paging.tvs.domain.usecases.GetTvsOnAirUseCase
import com.jaozinfs.paging.tvs.domain.usecases.GetTvsPopularUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TvsViewModel(
    private val getTvsPopularUseCase: GetTvsPopularUseCase,
    private val getTvsOnAirUseCase: GetTvsOnAirUseCase,
    private val getTvDetailsUseCase: GetTvDetailsUseCase
) : ViewModel() {
    companion object {
        const val POPULAR = "popular"
        const val ON_AIR = "onTheAir"
    }

    fun getTvsPopularFirstItem() = getTvsPopularUseCase.execute(null).map { it.take(10) }
    fun getTvsOnAir() = getTvsOnAirUseCase.execute(null).map { it.take(10) }

    fun getTvDetails(tvId: Int) = getTvDetailsUseCase.execute(tvId)

    /**
     * Get all items by category
     */
    fun getTvsByCategory(category: String): Flow<List<TvUI>> {
        return when (category) {
            POPULAR -> {
                getTvsPopularUseCase.execute(null)
            }
            ON_AIR -> {
                getTvsOnAirUseCase.execute(null)
            }
            else -> {
                throw NotImplementedError("Categoria não implementada")
            }
        }
    }
}