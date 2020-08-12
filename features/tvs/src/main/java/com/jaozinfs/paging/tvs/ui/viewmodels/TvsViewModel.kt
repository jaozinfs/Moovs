package com.jaozinfs.paging.tvs.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jaozinfs.paging.extensions.handleErrors
import com.jaozinfs.paging.tvs.domain.model.TvDetailsUI
import com.jaozinfs.paging.tvs.domain.model.TvUI
import com.jaozinfs.paging.tvs.domain.usecases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip

class TvsViewModel(
    private val getTvsPopularUseCase: GetTvsPopularUseCase,
    private val getTvsOnAirUseCase: GetTvsOnAirUseCase,
    private val getTvDetailsUseCase: GetTvDetailsUseCase,
    private val getTvsFavoritedUseCase: GetTvsFavoritedUseCase,
    private val saveTvFavoriteUseCase: SaveTvFavoriteUseCase
) : ViewModel() {
    companion object {
        const val POPULAR = "popular"
        const val ON_AIR = "onTheAir"
    }

    fun getTvsPopularFirstItem() = getTvsPopularUseCase.execute(null).map { it.take(10) }
    fun getTvsOnAir() = getTvsOnAirUseCase.execute(null).map { it.take(10) }
    fun getTvDetails(tvId: Int) = getTvDetailsUseCase.execute(tvId)

    /**
     * Get combine flow with all categories
     * @return [Flow] Triple<List<TvUI>, List<TvUI>, List<TvUi>> -> with segments of combined
     */
    fun getCategoriesCombined() =
        getTvsPopularFirstItem().zip(getTvsOnAir()) { popular, onAIr ->
            Pair(popular, onAIr)
        }.zip(getTvsFavoritedUseCase.execute(Unit)) { first, second ->
            Triple(first.first, first.second, second)
        }.flowOn(Dispatchers.IO)

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

    fun saveTvFavorite(tvUI: TvDetailsUI) = saveTvFavoriteUseCase
        .execute(tvUI)
        .handleErrors {
            Log.d("Teste", "Error: $it")
        }
        .flowOn(Dispatchers.IO)


    fun getTvsFavorite(tvUI: TvUI) = getTvsFavoritedUseCase.execute(Unit).flowOn(Dispatchers.IO)


}